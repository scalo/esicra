package it.saga.egov.esicra.attachments;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

abstract class AbstractAttachments {
    /*
     *  Constants
     */

    // [
    public static final int FTP_LOGIN_INCORRECT = 530;
    public static final int FTP_NO_SUCH_FILE_OR_DIRECTORY = 550;
    private Logger _logger = null;

    // ]

    /*
     *  Internal Attributes
     */

    // [
    private ArrayList _pendingList = new ArrayList();
    private String _pendingListFilePath = null;
    private String _subsystemName = null;
    private FTPClient _ftp = null;
    private String _ftpHostName = null;
    private int _ftpPortNumber = 0;
    private String _ftpUserName = null;
    private String _ftpPassword = null;
    private String _ftpConnectMode = null;
    private String _ftpTransferType = null;
    private String _ftpLocalDir = null;

    public AbstractAttachments() throws ClassNotFoundException {

		//_logger = Logger.getLogger(Class.forName("it.saga.egov.esicra.attachments.Attachments"));
        _logger = Logger.getLogger(this.getClass());    
        _pendingListFilePath = Attachments.PROPERTY_HOMEDIR + File.separator + Attachments.PROPERTY_PENDINGLISTFILEPATH + File.separator + "attachments.backup";
        _subsystemName = Attachments.PROPERTY_SUBSYSTEMNAME;
        _ftpHostName = Attachments.PROPERTY_HOSTNAME;
        _ftpPortNumber = Integer.parseInt(Attachments.PROPERTY_PORTNUMBER);
        _ftpUserName = Attachments.PROPERTY_USERNAME;
        _ftpPassword = Attachments.PROPERTY_PASSWORD;
        _ftpConnectMode = Attachments.PROPERTY_CONNECTMODE;
        _ftpTransferType = Attachments.PROPERTY_TRANSFERTYPE;
        _ftpLocalDir = Attachments.PROPERTY_HOMEDIR + File.separator + Attachments.PROPERTY_LOCALDIR;
    }

    private Logger logger() {
        return _logger;
    }

    protected ArrayList pendingList() {
        return _pendingList;
    }

    private String pendingListFilePath() {
        return _pendingListFilePath;
    }

    protected String subsystemName() {
        return _subsystemName;
    }

    protected FTPClient ftp() {
        return _ftp;
    }

    private String ftpHostName() {
        return _ftpHostName;
    }

    private int ftpPortNumber() {
        return _ftpPortNumber;
    }

    private String ftpUserName() {
        return _ftpUserName;
    }

    private String ftpPassword() {
        return _ftpPassword;
    }

    private String ftpConnectMode() {
        return _ftpConnectMode;
    }

    private String ftpTransferType() {
        return _ftpTransferType;
    }

    protected String ftpLocalDir() {
        return _ftpLocalDir;
    }
    // ]

    /*
     * Private Interface
     */

    // [
    private void restorePendingList() throws IOException {
        BufferedReader reader = null;
        try {
            File backup = new File(pendingListFilePath());
            if (backup.exists() && backup.canRead()) {
                reader = new BufferedReader(new FileReader(backup));
                String file = null;
                while ((file = reader.readLine()) != null) {
                    pendingList().add(file);
                    logger().info("File [" + file + "] restored!!");
                }
            }
        } catch (IOException ioEx) {
            logger().info("Unable to restore pending list form file [" +
                pendingListFilePath() + "]");
            logger().error(ioEx);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private void backupPendingList() throws IOException {
        BufferedWriter writer = null;
        try {
            File backup = new File(pendingListFilePath());
            writer = new BufferedWriter(new FileWriter(pendingListFilePath()));
            Iterator it = pendingList().iterator();
            while (it.hasNext()) {
                String file = (String) it.next();
                writer.write(file, 0, file.length());
                writer.newLine();
                logger().info("File [" + file + "] backedup!!");
            }
        } catch (IOException ioEx) {
            logger().info("Unable to backup pending list file [" +
                pendingListFilePath() + "]");
            logger().error(ioEx);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private boolean openFtpConnection() throws AttachmentsException {
        boolean retVal = true;
        try {
            logger().info("OPENING FTP ...");
            _ftp = new FTPClient(ftpHostName(), ftpPortNumber());
            logger().info("... FTP OPENED!!");

            logger().info("FTP LOGGING IN ...");
            ftp().login(ftpUserName(), ftpPassword());
            logger().info("... FTP LOGGED IN!!");

            logger().info("FTP SETTING CONNECT MODE ...");
            if (ftpConnectMode().equals("ACTIVE")) {
                logger().info("ACTIVE");
                ftp().setConnectMode(FTPConnectMode.ACTIVE);
            } else if (ftpConnectMode().equals("PASSIVE")) {
                logger().info("PASSIVE");
                ftp().setConnectMode(FTPConnectMode.PASV);
            } else {
                throw new AttachmentsException("FTP Connect Mode [" +
                    ftpConnectMode() + " UNKNOWN!!");
            }
            logger().info("... CONNECT MODE SET!!");

            logger().info("FTP SETTING TRANSFER TYPE ...");
            if (ftpTransferType().equals("BINARY")) {
                logger().info("BINARY");
                ftp().setType(FTPTransferType.BINARY);
            } else if (ftpTransferType().equals("ASCII")) {
                logger().info("ASCII");
                ftp().setType(FTPTransferType.ASCII);
            } else {
                throw new AttachmentsException("FTP Transfer Type [" +
                    ftpTransferType() + " UNKNOWN!!");
            }
            logger().info("... TRANSFER TYPE SET!!");

            //logger().info("CHANGE TO REMOTE DIR ["+ftpRemoteDir()+"]");
            //ftp().chdir(ftpRemoteDir());
            //logger().info("... REMOTE DIR CHANGED!!");
        } catch (FTPException ftpEx) {
            retVal = false;
            switch (ftpEx.getReplyCode()) {
            case FTP_LOGIN_INCORRECT: {
                logger().error("FTP: Unable to open connection - check login credentials!!");
                logger().error("UserName: [" + ftpUserName() + "]");
                logger().error("Password: [" + ftpPassword() + "]");
                break;
            }
            /*
                                            case FTP_NO_SUCH_FILE_OR_DIRECTORY:
                                            {
                                                    logger().error("FTP: Unable to change remote dir to ["+ftpRemoteDir()+"]");
                                                    break;
                                            }
            */
            default: {
                logger().error("FTPException caught - ReplyCode [" +
                    ftpEx.getReplyCode() + "]");
                logger().error(ftpEx);
                break;
            }
            }
        } catch (UnknownHostException uhex) {
            retVal = false;

            logger().error("FTP: Unknown Host Exception!!");
            logger().error("Message: [" + uhex.getMessage() + "]");
        } catch (NoRouteToHostException nrthex) {
            retVal = false;

            logger().error("FTP: No Route To Host Exception!!");
            logger().error("Message: [" + nrthex.getMessage() + "]");
        } catch (ConnectException cex) {
            retVal = false;

            logger().error("FTP: Connect Exception!!");
            logger().error("Message: [" + cex.getMessage() +
                "] - check ftp port number!!");
        } catch (IOException ioEx) {
            retVal = false;

            logger().error("FTP Exception: " + ioEx.getClass().getName());
            logger().error("Message: [" + ioEx.getMessage() + "]");
        }

        return retVal;
    }

    // ]

    /*
     * Abstract Interface
     */

    // [
    abstract protected void retrieveFiles();

    abstract protected void postRetrieve();

    // ]

    /*
     * Public Interface
     */

    // [
    public void getAttachments() throws Exception {
        boolean _openFtpConnection = false;
        try {
            logger().info("[ >>> Opening Ftp Connection ...");
            if (openFtpConnection()) {
                logger().info("] <<< ... DONE!!");

                logger().info("[ >>> Try to restore pending files from backup ...");
                restorePendingList();
                logger().info("] <<< ... DONE!!");

                logger().info("[ >>> Try to execute postretrieve action on pending files ...");
                postRetrieve();
                logger().info("] <<< ... DONE!!");

                logger().info("[ >>> Retrieving files ...");
                retrieveFiles();
                logger().info("] <<< ... DONE!!");

                logger().info("[ >>> Execute postretreive action ...");
                postRetrieve();
                logger().info("] <<< ... DONE!!");

                logger().info("[ >>> Try to bakcup pending files ...");
                backupPendingList();
                logger().info("] <<< ... DONE!!");
            }
        } finally {
            if (ftp() != null) {
                ftp().quit();
            }
        }
    }
    // ]
}


class PATAttachments extends AbstractAttachments {

    private Logger _logger = null;

	PATAttachments() throws ClassNotFoundException
	{
		_logger = Logger.getLogger(Class.forName("it.saga.egov.esicra.attachments.Attachments"));
	}


    private Logger logger() {
        return _logger;
    }

    /*
     * Public Interface
     */
    protected void retrieveFiles() {
        try {
            /*
             * Retrieving Ftp Home Directory Absolute Path
             */
            String absoluteRemoteHomePath = ftp().pwd();
            logger().debug("Remote Absolute Path: ["+absoluteRemoteHomePath+"]");

            /*
             * Listing PATs directories Ftp Home Directory
             */
            logger().info("Listing remote dir ...");
            String[] remotePatDirs = ftp().dir();
            logger().info("... DONE!!");
            if (remotePatDirs == null) {
                logger().info("Obtained NULL remotePatDirs array: no nodes to retrieve?? Assuming so!!");
                return;
            }
            logger().debug("Remote dirs cardinality: ["+remotePatDirs.length+"]");

            /*
             * Checking PAT directory presence
             */
            logger().debug("Configured subsystem Name: ["+subsystemName()+"]");
            boolean found = false;
            for (int i = 0; i < remotePatDirs.length; i++) {
            logger().debug("Checking configured subsystem name against: ["+remotePatDirs[i]+"]");
                if (remotePatDirs[i].equals(subsystemName())) {
                    found = true;
                    break;
                }
            }

            String remotePatDir = null;
            if (!found) {
                logger().info("No files to retreive!!");
                return;
            } else {
                logger().info("Possible files to retrieve!!");
                remotePatDir = absoluteRemoteHomePath + File.separator + subsystemName();
            }

            /*
             * Changing to Remote PAT directory
             */
            logger().info("Change to Remote PAT dir [" + remotePatDir + "]");
            ftp().chdir(remotePatDir);
            logger().info("... Remote PAT dir changed!!");

            /*
             * Listing NODEs directories in PAT directory
             */
            logger().info("Listing Remote PAT dir ...");
            String[] remoteNodeDirs = ftp().dir();
            logger().info("... DONE!!");

            if (remoteNodeDirs == null) {
                logger().info("Obtained NULL nodeDirs array: no Node Dirs to retrieve?? Assuming so!!");
                return;
            }

            logger().info("Nodes to get: ["+remoteNodeDirs.length+"]");
            for (int i = 0; i < remoteNodeDirs.length;
                    logger().info("     [" + (String) remoteNodeDirs[i] + "]"), i++)
                ;

            /*
             * Looping through Remote Node Dirs to handle files retrievals
             */
            for (int i = 0; i < remoteNodeDirs.length; i++) {
                /*
                 * Check for local Node Dir presence and create it if does not exists
                 */
                File localNodeDir = new File(ftpLocalDir() + File.separator +
                        remoteNodeDirs[i]);
                File localNodeRepository = new File(localNodeDir, "repository");
                File localNodeBackup = new File(localNodeDir, "backup");
                if (!localNodeDir.exists()) {
                    logger().info("Local dir [" +
                        localNodeDir.getAbsolutePath() + "] doesn't exists!!");

                    if (!localNodeRepository.mkdirs()) {
                        logger().error("Unable to create local Node Repository [" +
                            localNodeRepository.getAbsolutePath() + "]");
                        continue;
                    } else {
                        logger().info("Local Node Repository [" +
                            localNodeRepository.getAbsolutePath() +
                            "] succesfully created");
                    }

                    if (!localNodeBackup.mkdirs()) {
                        logger().error("Unable to create local Node Backup [" +
                            localNodeBackup.getAbsolutePath() + "]");
                        continue;
                    } else {
                        logger().info("Local Node Backup [" +
                            localNodeBackup.getAbsolutePath() +
                            "] succesfully created");
                    }
                } else {
                    logger().info("Local dir [" +
                        localNodeDir.getAbsolutePath() + "] exists!!");
                }

                /*
                 * Changing to Remote Node directory
                 */
                String remoteNodeDir = remotePatDir + File.separator + remoteNodeDirs[i];
                logger().info("Change to Remote NODE dir [" + remoteNodeDir +
                    "]");
                ftp().chdir(remoteNodeDir);
                logger().info("... Remote NODE dir changed!!");

                /*
                 * Listing files in Remote Node directory
                 */
                logger().info("Listing remote NODE dir ...");
                String[] files = ftp().dir();
                logger().info("... DONE!!");

                if (files == null) {
                    logger().info("Obtained NULL files array: no files to retrieve?? Assuming so!!");
                    continue;
                }

                logger().info("Files to get: ["+files.length+"]");
                for (int k = 0; k < files.length;
                        logger().info("     [" + (String) files[k] + "]"), k++)
                    ;

                /*
                 * Retrieving files
                 */
                for (int j = 0; j < files.length; j++) {
                    // salta i files incompleti
                    if (files[j].toLowerCase().endsWith(".part")) continue;
                    logger().info("Retrieving file [" + files[j] + "]");
                    try {
                        ftp().get(localNodeRepository.getAbsolutePath() + File.separator +
                            files[j]+".part", files[j]);
                        File file = new File(localNodeRepository.getAbsolutePath() + File.separator +
                            files[j]+".part");
                        file.renameTo(new File(localNodeRepository.getAbsolutePath() + File.separator + 
                            files[j]));
                        pendingList().add(remoteNodeDir + File.separator + files[j]);
                    } catch (FTPException ftpEx) {
                        logger().error("FTPException caught retrieving files - ReplyCode: [" +
                            ftpEx.getReplyCode() + "]");
                    } catch (Exception ex) {
                        logger().error("IOException caught retrieving files!!");
                        logger().error(ex);
                    }
                }
            }
        } catch (FTPException ftpEx) {
            logger().error("FTPException caught in PATAttachments.retrieveFiles" +
                " - ReplyCode: [" + ftpEx.getReplyCode() + "]");
        } catch (IOException ioEx) {
            logger().error("IOException caught in PATAttachments.retrieveFiles");
            logger().error(ioEx);
        }
    }

    public void postRetrieve() {
        logger().info("Files to delete: [" + pendingList().size() + "]");
        Iterator it = pendingList().iterator();
        while (it.hasNext()) {
            String file = (String) it.next();
            try {
                logger().info("Deleting [" + file + "]");
                ftp().delete(file);
                it.remove();
            } catch (FTPException ftpEx) {
                if (ftpEx.getReplyCode() == AbstractAttachments.FTP_NO_SUCH_FILE_OR_DIRECTORY) {
                    logger().error("Remote file [" + file +
                        "] doesnt exists - remove from pending!!");
                    it.remove();
                } else {
                    logger().error("FTPException caught renaming file [" +
                        file + "] - ReplyCode [" + ftpEx.getReplyCode() + "]");
                    logger().error(ftpEx);
                }
            } catch (Exception ex) {
                logger().error(ex);
            }
        }
    }
}


class NODEAttachments extends AbstractAttachments {

    private Logger _logger = null;

	NODEAttachments() throws ClassNotFoundException
	{
		_logger = Logger.getLogger(Class.forName("it.saga.egov.esicra.attachments.Attachments"));
	}

    private Logger logger() {
        return _logger;
    }

    /*
     * Public Interface
     */
    protected void retrieveFiles() {
        try {
            /*
             * Retrieving Ftp Home Directory Absolute Path
             */
            String absoluteRemoteHomePath = ftp().pwd();
            logger().debug("Remote Absolute Path: ["+absoluteRemoteHomePath+"]");

            /*
             * Listing Nodes directories
             */
            logger().info("Listing remote dir ...");
            String[] remoteNodeDirs = ftp().dir();
            logger().info("... DONE!!");
            if (remoteNodeDirs == null) {
                logger().info("Obtained NULL remoteNodeDirs array: no nodes to retrieve?? Assuming so!!");
                return;
            }
            logger().debug("Remote dirs cardinality: ["+remoteNodeDirs.length+"]");

            /*
             * Checking NODE directory presence
             */
            logger().debug("Configured subsystem Name: ["+subsystemName()+"]");
            boolean found = false;
            for (int i = 0; i < remoteNodeDirs.length; i++) {
                logger().debug("Checking configured subsystem name against: ["+remoteNodeDirs[i]+"]");
                if (remoteNodeDirs[i].equals(subsystemName())) {
                    found = true;
                    break;
                }
            }

            String remoteNodeRepository = null;
            String remoteNodeBackup = null;
            if (!found) {
                logger().info("No files to retreive!!");
                return;
            } else {
                logger().info("Possible files to retrieve!!");
                remoteNodeRepository = absoluteRemoteHomePath + File.separator +
                    subsystemName() + File.separator + "repository";
                remoteNodeBackup = absoluteRemoteHomePath + File.separator +
                    subsystemName() + File.separator + "backup";
            }

            /*
             * Changing to Remote Node Repository directory
             */
            logger().info("Change to Remote Repository dir [" +
                remoteNodeRepository + "]");
            ftp().chdir(remoteNodeRepository);
            logger().info("... Remote Node Repository dir changed!!");

            /*
             * Listing files in Remote Node Repository directory
             */
            logger().info("Listing Remote Node Repository dir ...");
            String[] files = ftp().dir();
            logger().info("... DONE!!");

            if (files == null) {
                logger().info("Obtained NULL files array: no files to retrieve?? Assuming so!!");
                return;
            }

            logger().info("Files to get:"+files.length+"]");
            for (int k = 0; k < files.length;
                    logger().info("     [" + (String) files[k] + "]"), k++);

            /*
             * Retrieving files
             */
            for (int j = 0; j < files.length; j++) {
                if (files[j].toLowerCase().endsWith(".part")) continue;
                logger().info("Retrieving file [" + files[j] + "]");
                try {
                    ftp().get(ftpLocalDir() + File.separator + files[j]+".part", files[j]);
                    File file = new File(ftpLocalDir() + File.separator + files[j]+".part");
                    file.renameTo(new File(ftpLocalDir() + File.separator + files[j]));
                    pendingList().add(files[j]);
                } catch (FTPException ftpEx) {
                    logger().error("FTPException caught retrieving files - ReplyCode: [" +
                        ftpEx.getReplyCode() + "]");
                } catch (Exception ex) {
                    logger().error("IOException caught retrieving files!!");
                    logger().error(ex);
                }
            }
        } catch (FTPException ftpEx) {
            logger().error("FTPException caught in PATAttachments.retrieveFiles" +
                " - ReplyCode: [" + ftpEx.getReplyCode() + "]");
        } catch (IOException ioEx) {
            logger().error("IOException caught in PATAttachments.retrieveFiles");
            logger().error(ioEx);
        }
    }

    public void postRetrieve() {
        logger().info("Files to move: [" + pendingList().size() + "]");
        Iterator it = pendingList().iterator();
        while (it.hasNext()) {
            String file = (String) it.next();
            String newFile = "../backup/" + file;
            try {
                logger().info("Moving [" + file + "] to [" + newFile + "]");
                ftp().rename(file, newFile);
                it.remove();
            } catch (FTPException ftpEx) {
                if (ftpEx.getReplyCode() == AbstractAttachments.FTP_NO_SUCH_FILE_OR_DIRECTORY) {
                    logger().error("Remote file [" + file +
                        "] doesnt exists - remove from pending!!");
                    it.remove();
                } else {
                    logger().error("FTPException caught moving file [" + file +
                        "] - ReplyCode [" + ftpEx.getReplyCode() + "]");
                    logger().error(ftpEx);
                }
            } catch (Exception ex) {
                logger().error(ex);
            }
        }
    }
}


public class Attachments {
    /*
     * CONSTANTS
     */

    // [
    public static final String _PROPERTY_HOMEDIR             = "esicra.home";
    public static final String _PROPERTY_SUBSYSTEMTYPE       = "esicra.tipo_installazione";
    public static final String _PROPERTY_SUBSYSTEMNAME       = "esicra.id_ente";
    public static final String _PROPERTY_PENDINGLISTFILEPATH = "esicra.persistence.dir";
    public static final String _PROPERTY_HOSTNAME            = "esicra.attachments.ftp.hostname";
    public static final String _PROPERTY_PORTNUMBER          = "esicra.attachments.ftp.portnumber";
    public static final String _PROPERTY_USERNAME            = "esicra.attachments.ftp.username";
    public static final String _PROPERTY_PASSWORD            = "esicra.attachments.ftp.password";
    public static final String _PROPERTY_CONNECTMODE         = "esicra.attachments.ftp.connectMode";
    public static final String _PROPERTY_TRANSFERTYPE        = "esicra.attachments.ftp.transferType";
    public static final String _PROPERTY_LOCALDIR            = "esicra.attachments.ftp.localDir";

    // ]

    /*
     * Private Attributes
     */
    // [
    private AbstractAttachments attachments = null;
    private Logger _logger = null;
    // ]

    /*
     * Public Attributes
     */
    // [
	public static String PROPERTY_HOMEDIR             = null;
	public static String PROPERTY_SUBSYSTEMTYPE       = null;
	public static String PROPERTY_SUBSYSTEMNAME       = null;
	public static String PROPERTY_PENDINGLISTFILEPATH = null;
	public static String PROPERTY_HOSTNAME            = null;
	public static String PROPERTY_PORTNUMBER          = null;
	public static String PROPERTY_USERNAME            = null;
	public static String PROPERTY_PASSWORD            = null;
	public static String PROPERTY_CONNECTMODE         = null;
	public static String PROPERTY_TRANSFERTYPE        = null;
	public static String PROPERTY_LOCALDIR            = null;
    // ]

    /*
     * Private Interface
     */
    // [
    private Logger logger() {
        return _logger;
    }


	private void loadProperties() throws AttachmentsException
	{
		PROPERTY_HOMEDIR = System.getProperty(_PROPERTY_HOMEDIR).trim(); 
		if (PROPERTY_HOMEDIR == null)
		{
			throw new AttachmentsException("Property "+_PROPERTY_HOMEDIR+" not set!!");
		}
		PROPERTY_SUBSYSTEMTYPE = System.getProperty(_PROPERTY_SUBSYSTEMTYPE).trim(); 
		if (PROPERTY_SUBSYSTEMTYPE == null)
		{
			throw new AttachmentsException("Property "+_PROPERTY_SUBSYSTEMTYPE+" not set!!");
		}
		PROPERTY_SUBSYSTEMNAME = System.getProperty(_PROPERTY_SUBSYSTEMNAME).trim(); 
		if (PROPERTY_SUBSYSTEMNAME == null)
		{
			throw new AttachmentsException("Property "+_PROPERTY_SUBSYSTEMNAME+" not set!!");
		}
		PROPERTY_PENDINGLISTFILEPATH = System.getProperty(_PROPERTY_PENDINGLISTFILEPATH).trim(); 
		if (PROPERTY_PENDINGLISTFILEPATH == null)
		{
			throw new AttachmentsException("Property "+_PROPERTY_PENDINGLISTFILEPATH+" not set!!");
		}
		PROPERTY_HOSTNAME = System.getProperty(_PROPERTY_HOSTNAME).trim(); 
		if (PROPERTY_HOSTNAME == null)
		{
			throw new AttachmentsException("Property "+_PROPERTY_HOSTNAME+" not set!!");
		}
		PROPERTY_PORTNUMBER = System.getProperty(_PROPERTY_PORTNUMBER).trim(); 
		if (PROPERTY_PORTNUMBER == null)
		{
			throw new AttachmentsException("Property "+_PROPERTY_PORTNUMBER+" not set!!");
		}
		PROPERTY_USERNAME = System.getProperty(_PROPERTY_USERNAME).trim(); 
		if (PROPERTY_USERNAME == null)
		{
			throw new AttachmentsException("Property "+_PROPERTY_USERNAME+" not set!!");
		}
		PROPERTY_PASSWORD = System.getProperty(_PROPERTY_PASSWORD).trim(); 
		if (PROPERTY_PASSWORD == null)
		{
			throw new AttachmentsException("Property "+_PROPERTY_PASSWORD+" not set!!");
		}
		PROPERTY_CONNECTMODE = System.getProperty(_PROPERTY_CONNECTMODE).trim(); 
		if (PROPERTY_CONNECTMODE == null)
		{
			throw new AttachmentsException("Property "+_PROPERTY_CONNECTMODE+" not set!!");
		}
		PROPERTY_TRANSFERTYPE = System.getProperty(_PROPERTY_TRANSFERTYPE).trim(); 
		if (PROPERTY_TRANSFERTYPE == null)
		{
			throw new AttachmentsException("Property "+_PROPERTY_TRANSFERTYPE+" not set!!");
		}
		PROPERTY_LOCALDIR = System.getProperty(_PROPERTY_LOCALDIR).trim(); 
		if (PROPERTY_LOCALDIR == null)
		{
			throw new AttachmentsException("Property "+_PROPERTY_LOCALDIR+" not set!!");
		}

		logger().debug("Properties Values:");
		logger().debug(_PROPERTY_HOMEDIR+"=["+PROPERTY_HOMEDIR+"]");
		logger().debug(_PROPERTY_HOMEDIR+"=["+PROPERTY_HOMEDIR+"]");
		logger().debug(_PROPERTY_SUBSYSTEMTYPE+"=["+PROPERTY_SUBSYSTEMTYPE+"]");
		logger().debug(_PROPERTY_SUBSYSTEMNAME+"=["+PROPERTY_SUBSYSTEMNAME+"]");
		logger().debug(_PROPERTY_PENDINGLISTFILEPATH+"=["+PROPERTY_PENDINGLISTFILEPATH+"]");
		logger().debug(_PROPERTY_HOSTNAME+"=["+PROPERTY_HOSTNAME+"]");
		logger().debug(_PROPERTY_PORTNUMBER+"=["+PROPERTY_PORTNUMBER+"]");
		logger().debug(_PROPERTY_USERNAME+"=["+PROPERTY_USERNAME+"]");
		logger().debug(_PROPERTY_PASSWORD+"=["+PROPERTY_PASSWORD+"]");
		logger().debug(_PROPERTY_CONNECTMODE+"=["+PROPERTY_CONNECTMODE+"]");
		logger().debug(_PROPERTY_TRANSFERTYPE+"=["+PROPERTY_TRANSFERTYPE+"]");
		logger().debug(_PROPERTY_LOCALDIR+"=["+PROPERTY_LOCALDIR+"]");
	}
    // ]

    /*
     * Public Interface
     */
    // [
    public void execute() {
        try {
            attachments.getAttachments();
        } catch (Exception ex) {
            logger().error(ex);
        }
    }
    // ]

    /*
     * Constructors
     */
    // [
    public Attachments()
	{

        try {
            _logger = Logger.getLogger(Class.forName("it.saga.egov.esicra.attachments.Attachments"));

				loadProperties();

            if (PROPERTY_SUBSYSTEMTYPE.equalsIgnoreCase("PAT"))
				{
                attachments = new PATAttachments();
            }
				else if (PROPERTY_SUBSYSTEMTYPE.equalsIgnoreCase("NODO"))
				{
                attachments = new NODEAttachments();
            }
				else
				{
                throw new AttachmentsException("Unexpected Attachments Type [" +
                    PROPERTY_SUBSYSTEMTYPE + "]");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
				if ( logger() != null )
				{
            	logger().error(ex);
				}
        }
    }
    // ]

    /*
     *  TEST
     */
	public static void main(String[] args) throws Exception
	{
		if ( args.length == 0 )
		{
			System.out.println("Usage: it.saga.egov.esicra.attachments.Attachments <PRJ_HOME>");
			return;
		}

		String home = args[0];
		File homeDir = new File(home);
		if ( !homeDir.exists() || !homeDir.isDirectory() || !homeDir.canRead() )
		{
			System.out.println("Cannot access home directory ["+home+"]");
			return;
		}

		Properties props = new Properties();
		FileInputStream stream = new FileInputStream(homeDir+File.separator+"conf"+File.separator+"esicra.properties");
		props.load(stream);
		stream.close();
		Properties sysProps = System.getProperties();
		sysProps.putAll(props);
		System.setProperties(sysProps);

		Attachments attach = new Attachments();
		attach.execute();
	}
}
