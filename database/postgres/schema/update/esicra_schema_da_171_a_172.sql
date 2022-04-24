-- siscotel CR Postgres
-- 1.7.1 to 1.7.2
SET search_path = :dbschema;

DROP VIEW v_indice_oggetto_ind;

CREATE VIEW v_indice_oggetto_ind AS 
	SELECT aot.pkid AS aot_pkid,
	       ind.pkid AS ind_pkid,
	       aot.cod_ecografico AS codice_ecografico,
	       aot.cod_aot,
		   aot.cod_catasto,
		   aot.dt_mod AS aot_dtmod,
		   aot.cod_istat_comune AS aot_cod_istat,
		   area_indirizzo,
		   numero_civico,
		   lettera_civico,
		   colore_civico,
		   corte_civico,
		   scala_civico,
		   interno_civico,
		   piano_civico,
		   edificio_civico,
		   dt_ini,
		   dt_fin,
		   id_ente
   FROM ot_indice aot
   LEFT JOIN ( SELECT ind.fkid_ot, ind.pkid, ind.des_area AS area_indirizzo, ind.num_civ AS numero_civico, ind.let_civ AS lettera_civico, ind.colore AS colore_civico, ind.corte AS corte_civico, ind.scala AS scala_civico, ind.interno AS interno_civico, ind.piano AS piano_civico, ind.edificio AS edificio_civico, ind.dt_ini, ind.dt_fin
                FROM ot_indirizzo ind
               WHERE ind.dt_mod IS NOT NULL AND ind.dt_fin IS NULL) ind ON aot.pkid = ind.fkid_ot
  WHERE aot.dt_mod IS NOT NULL;

delete from ser_esicra_info;

insert into ser_esicra_info (nomedb, versione, dt_creazione, db_engine,id_ente,cod_istat)
values ('esicra', '1.7.2', '2005-02-01', 'postgres', 8240, 999999);

CREATE TABLE ser_gruppo
(
	nome       VARCHAR(30) NOT NULL,
	des_gruppo VARCHAR(200),
	dt_mod     TIMESTAMP,
	CONSTRAINT ser_gruppo_pkey PRIMARY KEY (nome)
);

CREATE TABLE ser_gruppo_utente
(
  gruppo VARCHAR(30) NOT NULL,
  utente VARCHAR(30) NOT NULL,
  CONSTRAINT ser_gruppo_utente_pkey PRIMARY KEY (gruppo, utente),
  CONSTRAINT gruppo_utente_fk1 FOREIGN KEY (gruppo)
  REFERENCES ser_gruppo(nome) ON DELETE CASCADE,
  CONSTRAINT gruppo_utente_fk2 FOREIGN KEY (utente)
  REFERENCES j2ee_users(user_name) ON DELETE CASCADE

);

CREATE TABLE ser_gruppo_servizio
(
  gruppo      VARCHAR(30) NOT NULL,
  id_servizio NUMERIC(22) NOT NULL,
  CONSTRAINT ser_gruppo_servizio_pkey PRIMARY KEY (gruppo, id_servizio),
  CONSTRAINT gruppo_servizio_fk1 FOREIGN KEY (gruppo)
  REFERENCES ser_gruppo(nome) ON DELETE CASCADE
);

DELETE FROM ser_servizio;
INSERT INTO ser_servizio (pkid, id_servizio, descrizione, raggruppamento, des_breve, tipo_export, sigla_export, id_ente, id_ente_destinatario, dt_mod)
VALUES (1, 100141, 'DOMANDA SERVIZIO MENSE SCOLASTICHE', 'SCU', 'MENSE SCUOLA', 1, 'PRASER', NULL, NULL, to_date('2005-01-01', 'YYYY-MM-DD'));
INSERT INTO ser_servizio (pkid, id_servizio, descrizione, raggruppamento, des_breve, tipo_export, sigla_export, id_ente, id_ente_destinatario, dt_mod)
VALUES (2, 100141, 'DOMANDA SERVIZIO MENSE SCOLASTICHE', 'SCU', 'MENSE SCUOLA', 2, 'PRASCU', NULL, NULL, to_date('2005-01-01', 'YYYY-MM-DD'));
INSERT INTO ser_servizio (pkid, id_servizio, descrizione, raggruppamento, des_breve, tipo_export, sigla_export, id_ente, id_ente_destinatario, dt_mod)
VALUES (3, 100142, 'DOMANDA SERVIZIO TRASPORTO SCOLASTICO', 'SCU', 'TRASPORTO SCUOLA', 1, 'PRASER', NULL, NULL, to_date('2005-01-01', 'YYYY-MM-DD'));
INSERT INTO ser_servizio (pkid, id_servizio, descrizione, raggruppamento, des_breve, tipo_export, sigla_export, id_ente, id_ente_destinatario, dt_mod)
VALUES (4, 100142, 'DOMANDA SERVIZIO TRASPORTO SCOLASTICO', 'SCU', 'TRASPORTO SCUOLA', 2, 'PRASCU', NULL, NULL, to_date('2005-01-01', 'YYYY-MM-DD'));
INSERT INTO ser_servizio (pkid, id_servizio, descrizione, raggruppamento, des_breve, tipo_export, sigla_export, id_ente, id_ente_destinatario, dt_mod)
VALUES (5, 100168, 'DOMANDA ISCRIZIONE CENTRO SPORTIVO', 'SCU', 'CENTRI SPORTIVI', 1, 'PRASER', NULL, NULL, to_date('2005-01-01', 'YYYY-MM-DD'));
INSERT INTO ser_servizio (pkid, id_servizio, descrizione, raggruppamento, des_breve, tipo_export, sigla_export, id_ente, id_ente_destinatario, dt_mod)
VALUES (6, 100168, 'DOMANDA ISCRIZIONE CENTRO SPORTIVO', 'SCU', 'CENTRI SPORTIVI', 2, 'PRASCU', NULL, NULL, to_date('2005-01-01', 'YYYY-MM-DD'));

INSERT INTO ser_servizio (pkid, id_servizio, descrizione, raggruppamento, des_breve, tipo_export, sigla_export, id_ente, id_ente_destinatario, dt_mod)
VALUES (164, 210046, 'DOMANDA RECESSO MENSA SCOLASTICA', 'SCU', 'MENSE SCUOLA', 1, 'PRASER', NULL, NULL, to_date('2005-01-01', 'YYYY-MM-DD'));
INSERT INTO ser_servizio (pkid, id_servizio, descrizione, raggruppamento, des_breve, tipo_export, sigla_export, id_ente, id_ente_destinatario, dt_mod)
VALUES (165, 210046, 'DOMANDA RECESSO MENSA SCOLASTICA', 'SCU', 'MENSE SCUOLA', 2, 'PRASCU', NULL, NULL, to_date('2005-01-01', 'YYYY-MM-DD'));
INSERT INTO ser_servizio (pkid, id_servizio, descrizione, raggruppamento, des_breve, tipo_export, sigla_export, id_ente, id_ente_destinatario, dt_mod)
VALUES (166, 210047, 'DOMANDA RECESSO TRASPORTO SCOLASTICO', 'SCU', 'TRASPORTO SCUOLA', 1, 'PRASER', NULL, NULL, to_date('2005-01-01', 'YYYY-MM-DD'));
INSERT INTO ser_servizio (pkid, id_servizio, descrizione, raggruppamento, des_breve, tipo_export, sigla_export, id_ente, id_ente_destinatario, dt_mod)
VALUES (167, 210047, 'DOMANDA RECESSO TRASPORTO SCOLASTICO', 'SCU', 'TRASPORTO SCUOLA', 2, 'PRASCU', NULL, NULL, to_date('2005-01-01', 'YYYY-MM-DD'));
INSERT INTO ser_servizio (pkid, id_servizio, descrizione, raggruppamento, des_breve, tipo_export, sigla_export, id_ente, id_ente_destinatario, dt_mod)
VALUES (168, 210048, 'DOMANDA RECESSO PARTECIPAZIONE CENTRO SPORTIVO', 'SCU', 'CENTRI SPORTIVI', 1, 'PRASER', NULL, NULL, to_date('2005-01-01', 'YYYY-MM-DD'));
INSERT INTO ser_servizio (pkid, id_servizio, descrizione, raggruppamento, des_breve, tipo_export, sigla_export, id_ente, id_ente_destinatario, dt_mod)
VALUES (169, 210048, 'DOMANDA RECESSO PARTECIPAZIONE CENTRO SPORTIVO', 'SCU', 'CENTRI SPORTIVI', 2, 'PRASCU', NULL, NULL, to_date('2005-01-01', 'YYYY-MM-DD'));

DELETE FROM ser_gruppo;
INSERT INTO ser_gruppo (nome, des_gruppo, dt_mod) VALUES ('tutti', 'tutti servizi', NULL);

DELETE FROM ser_gruppo_utente;
INSERT INTO ser_gruppo_utente (gruppo, utente) VALUES ('tutti', 'amministratore');

DELETE FROM ser_gruppo_servizio;
--Iscrizioni
INSERT INTO ser_gruppo_servizio (gruppo, id_servizio) VALUES ('tutti', 100141);
INSERT INTO ser_gruppo_servizio (gruppo, id_servizio) VALUES ('tutti', 100142);
INSERT INTO ser_gruppo_servizio (gruppo, id_servizio) VALUES ('tutti', 100168);
--Recessi
INSERT INTO ser_gruppo_servizio (gruppo, id_servizio) VALUES ('tutti', 210046);
INSERT INTO ser_gruppo_servizio (gruppo, id_servizio) VALUES ('tutti', 210047);
INSERT INTO ser_gruppo_servizio (gruppo, id_servizio) VALUES ('tutti', 210048);

