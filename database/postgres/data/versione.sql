SET search_path = :dbschema;

delete from ser_esicra_info;

insert into ser_esicra_info (nomedb, versione, dt_creazione, db_engine,id_ente,cod_istat)
values ('esicra', '1.7.3', '2005-02-01', 'postgres',8240,999999)

