#!/bin/sh

if [ -z "$DBMS_HOME"  ]; then
	echo "Environment variable [DBMS_HOME] not set!!"
	echo
   exit
fi

if [ ! -d $DBMS_HOME ]; then
   echo "[$DBMS_HOME] not a valid path!!"
	echo
   exit
fi

_DBMS_BIN=$DBMS_HOME/bin
_PG_HOME=$DBMS_HOME/postgres
_PG_DB=$_PG_HOME/db
_PG_LOG=$_PG_HOME/log

if [ ! -d $_DBMS_BIN -o \
     ! -d $_PG_HOME -o \
     ! -d $_PG_DB ]
then
	echo "Error checking POSTGRES installation directory structure!!"
	echo "Following directory structure expected:"
	echo
	echo $_DBMS_BIN
	echo $_PG_HOME
	echo $_PG_DB
	echo
	exit
fi

#
# INCLUDE UTILITY FUNCTIONS
#
. $_DBMS_BIN/functions.sh

#
# usage: _get_input <out:username>
#                   <out:dbname>
#
# return values:
#  0 on succesful completion
#  1 otherwise
#
__get_input()
{
	if [ $# != 2 ]
	then
		return 1;
	fi

	_username=""
	_dbname=""

	_yn_rsp=""
	while [ "$_yn_rsp" != "y" ]
	do

		echo
		echo
		_question="Insert Username [CTRL+C abort]>: "
		__read_rsp "$_question" _username

		echo
		echo
		_question="Insert Database Name[CTRL+C abort]>: "
		__read_rsp "$_question" _dbname


		echo
		echo
		echo "Following data will be used:"
		echo
		echo
		echo "    DB Username : $_username"
		echo "    DB Name     : $_dbname"
		echo
		echo
		_question="Do you agree [y/n] [CTRL+C abort]> ? "
		__read_yn_rsp "$_question" _yn_rsp
	done

	eval "$1=$_username"
	eval "$2=$_dbname"

	return 0
}


#
# COLLECTING INPUT PARAMETERS
#
username=""
dbname=""

__get_input username dbname

/usr/bin/psql -U $username -d $dbname -f $_PG_DB/db_cleanup.sql

echo
echo "Database cleanup terminated ... BYE BYE!!"
echo
