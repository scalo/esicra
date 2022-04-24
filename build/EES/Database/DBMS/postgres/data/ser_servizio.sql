SET search_path = :dbschema;

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



UPDATE ser_servizio SET dt_mod = '2004-01-01 00:00:00';

