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

_DBMS_BIN=$DBMS_HOME/bin
_ORA_HOME=$DBMS_HOME/oracle
_PG_HOME=$DBMS_HOME/postgres
if [ ! -d $_DBMS_BIN -o \
     ! -d $_ORA_HOME -o \
     ! -d $_PG_HOME ]
then
	echo "Error checking ORACLE installation directory structure!!"
	echo "Following directory structure expected:"
	echo
	echo $_DBMS_BIN
	echo $_ORA_HOME
	echo $_PG_HOME
	echo
	exit
fi

. $_DBMS_BIN/functions.sh


question="Insert installation type [ora|pg] [CTRL+C abort]:> "
inst_type=""
echo
__read_rsp "$question" inst_type
while [ ! "$inst_type" = "ora" -a ! "$inst_type" = "pg" ]
do
	echo
	echo
	echo "Check answer correctness!!"
	echo
	__read_rsp "$question" inst_type
done

if [ "$inst_type" = "ora" ]
then
	$_ORA_HOME/install.sh
else
	$_PG_HOME/install.sh
fi
