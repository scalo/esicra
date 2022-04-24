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
_ORA_LOG=$DBMS_HOME/oracle/log
_PG_HOME=$DBMS_HOME/postgres
_PG_LOG=$DBMS_HOME/postgres/log
if [ ! -d $_ORA_HOME -o \
     ! -d $_PG_HOME ]
then
	echo "Error checking ORACLE installation directory structure!!"
	echo "Following directory structure expected:"
	echo
	echo $_ORA_HOME
	echo $_PG_HOME
	echo
	exit
fi

if [ -d $_ORA_LOG ]; then
	/bin/rm -rf $_ORA_LOG/*
fi

if [ -d $_PG_LOG ]; then
	/bin/rm -rf $_PG_LOG/*
fi
