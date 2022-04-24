#/bin/bash

if [ $# != 3 ]; then
	echo
	echo "Usage: load_data.sh <username> <dbname> <dbschema>"
	echo
	exit
fi

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

_PG_HOME=$DBMS_HOME/postgres
_PG_DATA=$_PG_HOME/data
_PG_LOG=$_PG_HOME/log

if [ ! -d $_PG_HOME -o ! -d $_PG_DATA ]
then
	echo "Error checking POSTGRES installation directory structure!!"
	echo "Following directory structure expected:"
	echo
	echo $_PG_HOME
	echo $_PG_DATA
	echo
	exit
fi


/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/ter_stato.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/ter_regione.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/ter_provincia.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/ter_comune.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/ter_tribunale.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/ter_cittadinanza.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/ter_consolato.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/ana_relazione.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/esinextid.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/pol_tipo_veicolo.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/pol_tipo_permesso.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/ser_servizio.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/ser_coordinator.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/ser_provenienza.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/versione.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/j2ee_users.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/j2ee_user_roles.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/pra_stato.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/inserimenti_amministratore.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/pro_tipo.sql
/usr/bin/psql -U $1 -d $2 -v dbschema=$3 -f $_PG_DATA/albo.sql