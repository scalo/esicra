package it.saga.egov.esicra.importazione.test;

import it.saga.egov.esicra.importazione.DbUtilita;
import it.saga.siscotel.db.AnaSoggetto;
import it.saga.siscotel.db.hibernate.HibernateUtil;
import junit.framework.*;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.*;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.*;
import org.hibernate.util.SerializationHelper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DbUtilitaTest extends TestCase {
    DbUtilita utilita = null;

    public DbUtilitaTest(String name) {
        super(name);
    }

    public static Test suite() {
        System.out.println("* DbUtilitaTest *");
        TestSuite suite = new TestSuite();
        suite.addTest(new DbUtilitaTest("testCopyBeanBad"));
        //suite.addTest(new DbUtilitaTest("testCopyBean"));
        //suite.addTest(new DbUtilitaTest("testCopyBean2"));
        //suite.addTest(new DbUtilitaTest("testCercaRigaCorrente"));
        //suite.addTest(new DbUtilitaTest("testAnnullaRecordStorico"));
        return suite;
    }

    protected void setUp() throws Exception {
        System.setProperty("id_ente", "8240");

        utilita = new DbUtilita();
    }



    public void testCercaRigaCorrente() throws Exception {
        AnaSoggetto sg = (AnaSoggetto) utilita.cercaRigaCorrente("it.saga.siscotel.db.AnaSoggetto",
                "cod_soggetto", "PNLGNN77H8763HJZ");
        Map map = BeanUtils.describe(sg);
        System.out.println(map);
    }

    public void testAnnullaRecordStorico() throws Exception {
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
