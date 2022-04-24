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
_PG_HOME=$DBMS_HOME/postgres
_PG_BIN=$_PG_HOME/bin
_PG_DATA=$_PG_HOME/data
_PG_DB=$_PG_HOME/db
_PG_SCHEMA=$_PG_HOME/schema
_PG_LOG=$_PG_HOME/log

if [ ! -d $_DBMS_BIN -o \
     ! -d $_PG_HOME -o \
     ! -d $_PG_BIN -o \
     ! -d $_PG_DATA -o \
     ! -d $_PG_DB -o \
     ! -d $_PG_SCHEMA ]
then
	echo "Error checking POSTGRES installation directory structure!!"
	echo "Following directory structure expected:"
	echo
	echo $_DBMS_BIN
	echo $_PG_BIN
	echo $_PG_DATA
	echo $_PG_DB
	echo $_PG_SCHEMA
	echo
	exit
fi


#
# INCLUDE UTILITY FUNCTIONS
#
. $_DBMS_BIN/functions.sh
. $_PG_BIN/functions.sh


#
# CHECKING LOG DIR PRESENCE
#
if [ ! -d $_PG_LOG ]; then
	/bin/mkdir $_PG_LOG
fi



#
# NEW USER CREATION
#
# [
yn=""
username=""
password=""
echo
__read_yn_rsp "Do you want to create a new DB user [y|n] [CTRL+C abort]? > " yn

if [ "$yn" = "y" ]; then
	__get_create_user_input username password

	echo
	echo "CREATING USER ..."
	echo

#DROP USER $username;
	/usr/bin/psql -U postgres -d template1 << EOF
CREATE USER $username PASSWORD '$password';
EOF

	echo
	echo "... DONE!!"
#	echo "Check [$_PG_LOG/db.log] log file for details."
	echo

else
	echo
	echo "... DB user creation skipped!!" #| tee $_PG_LOG/schema.log
	echo
fi
# ]

#
# NEW DATABASE CREATION
#
# [
yn=""
dbname=""
dbowner=""
echo
__read_yn_rsp "Do you want to create a new DataBase [y|n] [CTRL+C abort]? > " yn

if [ "$yn" = "y" ]; then
	__get_create_db_input dbname dbowner

	echo
	echo "CREATING DB ..."
	echo

#DROP DATABASE $dbname;
	/usr/bin/psql -U postgres -d template1 << EOF
CREATE DATABASE $dbname OWNER=$dbowner ENCODING='UNICODE';
EOF

	echo
	echo "... DONE!!"
#	echo "Check [$_PG_LOG/db.log] log file for details."
	echo

else
	echo
	echo "... DataBase creation skipped!!" #| tee $_PG_LOG/schema.log
	echo
fi
# ]


#
# NEW DATABASE SCHEMA CREATION
#
# [
yn=""
username=""
dbname=""
dbschema=""
echo
__read_yn_rsp "Do you want to create a new DataBase Schema [y|n] [CTRL+C abort]? > " yn

if [ "$yn" = "y" ]; then

	__get_create_dbschema_input username dbname dbschema

	echo
	echo "CREATING DB SCHEMA ..."
	echo

	echo $username
	echo $dbname
	echo $dbschema

/usr/bin/psql -U $username -d $dbname << EOF
CREATE SCHEMA $dbschema AUTHORIZATION $username;
EOF

	echo
	echo "... DONE!!"
#	echo "Check [$_PG_LOG/db.log] log file for details."
	echo

else
	echo
	echo "... DB Schema creation skipped!!" #| tee $_PG_LOG/schema.log
	echo
fi
# ]


#
# SCHEMA LOADING
#
# [
yn=""
load_username=""
load_dbname=""
load_dbschema=""
echo
__read_yn_rsp "Do you want to proceed with DB schema loading [y|n] [CTRL+C abort]? > " yn

if [ "$yn" = "y" ]; then

	__get_loading_input load_username load_dbname load_dbschema

	echo
	echo -e "LOADING SCHEMA ...\c"
	/usr/bin/psql -U $load_username -d $load_dbname -v dbschema=$load_dbschema -f $_PG_SCHEMA/esicra_schema.sql >& $_PG_LOG/schema.log
	echo "... DONE!!"
	echo "Check [$_PG_LOG/schema.log] log file for details."
	echo

else
	echo
	echo "... schema loading skipped!!" | tee $_PG_LOG/schema.log
	echo
fi
# ]


#
# DATA LOADING
#
# [
yn=""
__read_yn_rsp "Do you want to proceed with data loading [y|n] [CTRL+C abort]? > " yn

if [ "$yn" = "y" ]; then


	if [ "$load_username" = "" -o "$load_dbname" = "" ]; then
		__get_loading_input load_username load_dbname load_dbschema
	fi

	echo
	echo -e "LOADING DATA ...\c"
	$_PG_BIN/load_data.sh $load_username $load_dbname $load_dbschema >& $_PG_LOG/data.log
	echo "... DONE!!"
	echo "Check [$_PG_LOG/data.log] log file for details."
	echo

else
	echo
	echo "... data loading skipped!!" | tee $_PG_LOG/data.log
	echo
fi
# ]

echo "Installation terminated ... BYE BYE!!"
echo


