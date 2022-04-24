SET search_path = :dbschema;

insert into esinextid(  PKID, NOME_TABELLA, INCREMENTO, NEXT_ID, DT_ULTIMO_POPOLAMENTO,
DT_INS, FKID_UTENTE_INS, FKID_PROC_INS, DT_MOD, FKID_UTENTE_MOD, FKID_PROC_MOD)
values(1, 'ESINEXTID' , 1, 2, null, '2003-01-01', 0, 0, null, null, null);
