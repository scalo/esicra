SET search_path = :dbschema;

-- utente amministratore 

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

