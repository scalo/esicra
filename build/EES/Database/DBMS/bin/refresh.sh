#!/bin/sh

if [ -z "$DBMS_HOME"  ]; then
	echo
	echo "Environment variable [DBMS_HOME] not set!!"
	echo
   exit
fi

if [ ! -d $DBMS_HOME ]; then
	echo
   echo "[$DBMS_HOME] not a valid path!!"
	echo
   exit
fi

_ORA_HOME=$DBMS_HOME/oracle
_ORA_BIN=$_ORA_HOME/bin
_PG_HOME=$DBMS_HOME/postgres
_PG_BIN=$_PG_HOME/bin
if [ ! -d $_ORA_HOME -o \
     ! -d $_ORA_BIN -o \
     ! -d $_PG_HOME -o \
     ! -d $_PG_BIN ]
then
	echo "Error checking ORACLE installation directory structure!!"
	echo "Following directory structure expected:"
	echo
	echo $_ORA_HOME
	echo $_ORA_BIN
	echo $_PG_HOME
	echo $_PG_BIN
	echo
	exit
fi

/usr/bin/dos2unix $DBMS_HOME/install.sh #>& /dev/null
/bin/chmod ug+x $DBMS_HOME/install.sh

/usr/bin/dos2unix $DBMS_HOME/bin/*.sh #>& /dev/null
/bin/chmod ug+x $DBMS_HOME/bin/*.sh

/usr/bin/dos2unix $_ORA_HOME/install.sh #>& /dev/null
/bin/chmod ug+x $_ORA_HOME/install.sh

/usr/bin/dos2unix $_ORA_BIN/*.sh #>& /dev/null
/bin/chmod ug+x $_ORA_BIN/*.sh

/usr/bin/dos2unix $_PG_HOME/install.sh #>& /dev/null
/bin/chmod ug+x $_PG_HOME/install.sh

/usr/bin/dos2unix $_PG_BIN/*.sh #>& /dev/null
/bin/chmod ug+x $_PG_BIN/*.sh
