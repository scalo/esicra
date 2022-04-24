SET search_path = :dbschema;

delete from pro_tipo;

INSERT INTO pro_tipo (pkid, descrizione) VALUES (10, 'Scuole');
INSERT INTO pro_tipo (pkid, descrizione) VALUES (20, 'Mense');
INSERT INTO pro_tipo (pkid, descrizione) VALUES (30, 'Centri');
INSERT INTO pro_tipo (pkid, descrizione) VALUES (40, 'Percorsi');
INSERT INTO pro_tipo (pkid, descrizione) VALUES (60, 'Esenzioni-Trasporto');
INSERT INTO pro_tipo (pkid, descrizione) VALUES (50, 'Esenzioni-Mensa');
INSERT INTO pro_tipo (pkid, descrizione) VALUES (70, 'Esenzioni-Centri');
INSERT INTO pro_tipo (pkid, descrizione) VALUES (80, 'Agevolazioni-Mensa');
INSERT INTO pro_tipo (pkid, descrizione) VALUES (90, 'Agevolazioni-Trasporto');
INSERT INTO pro_tipo (pkid, descrizione) VALUES (100, 'Agevolazioni-Centri');