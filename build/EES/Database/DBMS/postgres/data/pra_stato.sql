SET search_path = :dbschema;

delete from pra_stato;


INSERT INTO pra_stato (stato, des_stato) VALUES (1, 'DA PRENDERE IN CARICO');
INSERT INTO pra_stato (stato, des_stato) VALUES (2, 'IN ATTESA DI ALLEGATI');
INSERT INTO pra_stato (stato, des_stato) VALUES (3, 'IN ATTESA DI PAGAMENTO');
INSERT INTO pra_stato (stato, des_stato) VALUES (4, 'PAGATA');
INSERT INTO pra_stato (stato, des_stato) VALUES (5, 'RICHIESTO PAGAMENTO ON-LINE');
INSERT INTO pra_stato (stato, des_stato) VALUES (10, 'IN CARICO ALL''ENTE');
INSERT INTO pra_stato (stato, des_stato) VALUES (20, 'ISTRUTTORIA');
INSERT INTO pra_stato (stato, des_stato) VALUES (30, 'IN ELABORAZIONE');
INSERT INTO pra_stato (stato, des_stato) VALUES (40, 'RESPINTA');
INSERT INTO pra_stato (stato, des_stato) VALUES (50, 'ACCETTATA');
INSERT INTO pra_stato (stato, des_stato) VALUES (60, 'PRONTA PER CONSEGNA');
INSERT INTO pra_stato (stato, des_stato) VALUES (70, 'EVASA');
INSERT INTO pra_stato (stato, des_stato) VALUES (80, 'SPEDITA');
INSERT INTO pra_stato (stato, des_stato) VALUES (0, 'INCOMPLETA');
