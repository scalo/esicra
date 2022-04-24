-- Siscotel CR Postgres
-- da 1.7.2 a 1.7.3 

SET search_path = :dbschema;

CREATE TABLE statofornitore
(
  pkstatofornitore numeric(22) NOT NULL,
  cod_stato varchar(10),
  des_stato varchar(40),
  id_ente numeric(22) NOT NULL,
  CONSTRAINT pk_statofornitore PRIMARY KEY (pkstatofornitore)
);

CREATE TABLE statoiter
(
  pkstatoiter numeric(22) NOT NULL,
  cod_stato varchar(10),
  des_stato varchar(40),
  id_ente numeric(22) NOT NULL,
  CONSTRAINT pk_statoiter PRIMARY KEY (pkstatoiter)
) ;

CREATE TABLE albo
(
  pkalbo numeric(22) NOT NULL,
  des_albo varchar(80),
  flg_chiuso integer,
  note varchar(1000),
  id_ente numeric(22) NOT NULL,
  dt_mod timestamp,
  CONSTRAINT pk_albo PRIMARY KEY (pkalbo)
);

CREATE TABLE alboiter
(
  pkalboiter numeric(22) NOT NULL,
  fkalbo numeric(22) NOT NULL,
  datainiziovalidita timestamp NOT NULL,
  datafinevalidita timestamp NOT NULL,
  dt_mod timestamp,
  CONSTRAINT pk_pkalboiter PRIMARY KEY (pkalboiter),
  CONSTRAINT refalbo03 FOREIGN KEY (fkalbo) REFERENCES albo (pkalbo) ON DELETE CASCADE
);

CREATE TABLE fornitori
(
  pkfornitore numeric(22) NOT NULL,
  codfornitore varchar(100),
  denominazione varchar(100),
  personarif varchar(200),
  titolaritarif varchar(100),
  codfiscale varchar(100),
  partiva varchar(100),
  via varchar(100),
  civico varchar(100),
  cap varchar(100),
  citta varchar(100),
  provincia varchar(100),
  telefono varchar(100),
  fax varchar(100),
  email varchar(100),
  id_ente numeric(22) NOT NULL,
  dt_mod timestamp,
  nome varchar(100),
  cognome varchar(100),
  CONSTRAINT pk_fornitore PRIMARY KEY (pkfornitore)
) ;

CREATE TABLE categorie
(
  pkcategoria numeric(22) NOT NULL,
  descategoria varchar(200),
  fkalbo numeric(22),
  flg_eco integer NOT NULL,
  id_ente numeric(22) NOT NULL,
  CONSTRAINT pk_categorie PRIMARY KEY (pkcategoria),
  CONSTRAINT refalbo04 FOREIGN KEY (fkalbo) REFERENCES albo (pkalbo) ON DELETE CASCADE
) ;

CREATE TABLE richiesteaccreditamento
(
  pkrichiesteaccreditamento numeric(22) NOT NULL,
  fkalbo numeric(22),
  fkfornitore numeric(22),
  datapresentazione date,
  codfiscalerichiedente varchar(20),
  denominazione varchar(100),
  partiva varchar(20),
  note varchar(1000),
  id_ente_richiedente numeric(22),
  id_ente_destinatario numeric(22) NOT NULL,
  dt_mod timestamp,
  CONSTRAINT pk_richieste PRIMARY KEY (pkrichiesteaccreditamento),
  CONSTRAINT refalbo01 FOREIGN KEY (fkalbo) REFERENCES albo (pkalbo) ON DELETE CASCADE,
  CONSTRAINT reffornitore01 FOREIGN KEY (fkfornitore) REFERENCES fornitori (pkfornitore) ON DELETE CASCADE
);

/* tab centrale */
CREATE TABLE albo_for_cat_stato
(
  pkalbofornitori numeric(22) NOT NULL,
  fkalbo numeric(22),
  fkfornitore numeric(22),
  fkcategorie numeric(22),
  fkstato numeric(22),
  dataultimostato timestamp,
  notestato varchar(1000),
  dt_mod timestamp,
  CONSTRAINT pk_albof PRIMARY KEY (pkalbofornitori),
  CONSTRAINT refalbo02 FOREIGN KEY (fkalbo) REFERENCES albo (pkalbo) ON DELETE CASCADE,
  CONSTRAINT refcategorie03 FOREIGN KEY (fkcategorie) REFERENCES categorie (pkcategoria) ON DELETE CASCADE,
  CONSTRAINT reffornitore02 FOREIGN KEY (fkfornitore) REFERENCES fornitori (pkfornitore) ON DELETE CASCADE,
  CONSTRAINT refstatof01 FOREIGN KEY (fkstato) REFERENCES statofornitore (pkstatofornitore) ON DELETE CASCADE
) ;

CREATE TABLE forniture
(
  pkforniture numeric(22) NOT NULL,
  fkcategoria numeric(22) NOT NULL,
  fkrichiesta numeric(22) NOT NULL,
  desfornitura varchar(200),
  importo numeric(10,2),
  anno integer,
  committente varchar(200),
  CONSTRAINT pk_forniture PRIMARY KEY (pkforniture),
  CONSTRAINT refcategoria01 FOREIGN KEY (fkcategoria) REFERENCES categorie (pkcategoria) ON DELETE CASCADE,
  CONSTRAINT refrichiesta02 FOREIGN KEY (fkrichiesta) REFERENCES richiesteaccreditamento (pkrichiesteaccreditamento) ON DELETE CASCADE
);

CREATE TABLE richiesteaccreditamentoiter
(
  pkiter numeric(22) NOT NULL,
  fkrichiesta numeric(22) NOT NULL,
  fkstatoiter numeric(22),
  responsabile varchar(100),
  notestato varchar(1000),
  dataultimostato timestamp NOT NULL,
  id_ente numeric(22) NOT NULL,
  dt_mod timestamp,
  CONSTRAINT pk_iter PRIMARY KEY (pkiter),
  CONSTRAINT refrichiesta01 FOREIGN KEY (fkrichiesta) REFERENCES richiesteaccreditamento (pkrichiesteaccreditamento) ON DELETE CASCADE,
  CONSTRAINT refstatoiter01 FOREIGN KEY (fkstatoiter) REFERENCES statoiter (pkstatoiter) ON DELETE CASCADE
) ;

CREATE TABLE categorieric
(
  pkcategorieric numeric(22) NOT NULL,
  fkrichiesta numeric(22) NOT NULL,
  fkcategorie numeric(22) NOT NULL,
  CONSTRAINT pk_categorieric PRIMARY KEY (pkcategorieric),
  CONSTRAINT refcategorie05 FOREIGN KEY (fkcategorie) REFERENCES categorie (pkcategoria) ON DELETE CASCADE,
  CONSTRAINT refrichiesta05 FOREIGN KEY (fkrichiesta) REFERENCES richiesteaccreditamento (pkrichiesteaccreditamento) ON DELETE CASCADE
);

CREATE TABLE entealbo
(
  id_ente numeric(7) NOT NULL,
  des_ente varchar(120),
  cod_istat numeric(7),
  CONSTRAINT pk_ente PRIMARY KEY (id_ente)
);

CREATE TABLE pro_tipo
(
  pkid numeric(22) NOT NULL,
  descrizione varchar(50),
  CONSTRAINT pro_tipo_pkey PRIMARY KEY (pkid)
);


CREATE TABLE pro_valore
(
  pkid numeric(22) NOT NULL,
  tipo numeric(22),
  descrizione varchar(50),
  ordinale integer,
  id_ente numeric(7),
  dt_mod timestamp,
  CONSTRAINT pro_valore_pkey PRIMARY KEY (pkid),
  CONSTRAINT pro_tipo_fkid FOREIGN KEY (tipo) REFERENCES pro_tipo (pkid) ON DELETE CASCADE
);


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

insert into ser_coordinator (nome_tabella,fl_allinea,fl_cancellabile,fl_bidir,ordine,pkid) values 
('pro_valore',1,1,0,920,920);

delete from entealbo;
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (8240,'COMUNE TEST', 8240);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19, 'cremona', 19);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19022, 'casalmorano', 19022);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19041, 'dovera', 19041);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19077, 'pozzaglio ed uniti', 19077);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19098, 'soresina', 19098);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19110, 'trigolo', 19110);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19003, 'annicco', 19003);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19004, 'azzanello', 19004);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19006, 'bonemerse', 19006);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19007, 'bordolano', 19007);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19008, 'ca'' d''andrea', 19008);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19009, 'calvatone', 19009);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19010, 'camisano', 19010);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19012, 'capergnanica', 19012);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19013, 'cappella cantone', 19013);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19014, 'cappella de'' picenardi', 19014);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19015, 'capralba', 19015);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19016, 'casalbuttano ed uniti', 19016);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19017, 'casale cremasco-vidolasco', 19017);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19018, 'casaletto ceredano', 19018);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19019, 'casaletto di sopra', 19019);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19020, 'casaletto vaprio', 19020);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19024, 'castel gabbiano', 19024);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19025, 'castelleone', 19025);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19026, 'castelverde', 19026);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19027, 'castelvisconti', 19027);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19030, 'cicognolo', 19030);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19031, 'cingia de'' botti', 19031);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19032, 'corte de'' cortesi con cignone', 19032);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19033, 'corte de'' frati', 19033);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19034, 'credera rubbiano', 19034);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19038, 'crotta d''adda', 19038);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19039, 'cumignano sul naviglio', 19039);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19040, 'derovere', 19040);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19042, 'drizzona', 19042);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19044, 'formigara', 19044);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19045, 'gabbioneta binanuova', 19045);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19046, 'gadesco pieve delmona', 19046);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19049, 'gombito', 19049);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19050, 'grontardo', 19050);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19109, 'trescore cremasco', 19109);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19111, 'vaiano cremasco', 19111);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19112, 'vailate', 19112);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19051, 'grumello cremonese ed uniti', 19051);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19052, 'gussola', 19052);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19056, 'malagnino', 19056);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19059, 'montodine', 19059);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19060, 'moscazzano', 19060);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19062, 'offanengo', 19062);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19063, 'olmeneta', 19063);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19064, 'ostiano', 19064);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19065, 'paderno ponchielli', 19065);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19067, 'pandino', 19067);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19068, 'persico dosimo', 19068);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19069, 'pescarolo ed uniti', 19069);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19072, 'pianengo', 19072);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19074, 'pieve d''olmi', 19074);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19076, 'pizzighettone', 19076);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19078, 'quintano', 19078);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19079, 'ricengo', 19079);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19080, 'ripalta arpina', 19080);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19082, 'ripalta guerina', 19082);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19083, 'rivarolo del re ed uniti', 19083);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19084, 'rivolta d''adda', 19084);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19086, 'romanengo', 19086);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19087, 'salvirola', 19087);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19088, 'san bassano', 19088);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19090, 'san giovanni in croce', 19090);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19091, 'san martino del lago', 19091);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19093, 'scandolara ripa d''oglio', 19093);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19094, 'sergnano', 19094);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19095, 'sesto ed uniti', 19095);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19096, 'solarolo rainerio', 19096);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19097, 'soncino', 19097);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19099, 'sospiro', 19099);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19100, 'spinadesco', 19100);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19102, 'spino d''adda', 19102);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19104, 'ticengo', 19104);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19105, 'torlino vimercati', 19105);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19106, 'tornata', 19106);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19107, 'torre de'' picenardi', 19107);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19108, 'torricella del pizzo', 19108);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19054, 'izano', 19054);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19035, 'crema', 19035);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19002, 'agnadello', 19002);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19011, 'campagnola cremasca', 19011);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19023, 'casteldidone', 19023);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19037, 'cremosano', 19037);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19047, 'genivolta', 19047);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19061, 'motta baluffi', 19061);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19075, 'pieve san giacomo', 19075);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19113, 'vescovato', 19113);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19036, 'cremona', 19036);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19066, 'palazzo pignano', 19066);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19089, 'san daniele po', 19089);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19057, 'martignana di po', 19057);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19053, 'isola dovarese', 19053);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19021, 'casalmaggiore', 19021);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19043, 'fiesco', 19043);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19029, 'chieve', 19029);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19101, 'spineda', 19101);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19073, 'pieranica', 19073);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19028, 'cella dati', 19028);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19070, 'pessina cremonese', 19070);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19085, 'robecco d''oglio', 19085);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19092, 'scandolara ravara', 19092);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19103, 'stagno lombardo', 19103);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19001, 'acquanegra cremonese', 19001);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19071, 'piadena', 19071);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19081, 'ripalta cremasca', 19081);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19055, 'madignano', 19055);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19114, 'volongo', 19114);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19115, 'voltido', 19115);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19048, 'gerre de'' caprioli', 19048);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19005, 'bagnolo cremasco', 19005);
INSERT INTO entealbo (id_ente, des_ente, cod_istat) VALUES (19058, 'monte cremasco', 19058);

delete from statoiter;
INSERT INTO statoiter VALUES (1,'01','INOLTRATA',0);
INSERT INTO statoiter VALUES (2,'02','IN VALUTAZIONE',0);
INSERT INTO statoiter VALUES (3,'03','ACCETTATA',0);

delete from statofornitore;
INSERT INTO statofornitore VALUES (1,'01','ATTIVO',0);
INSERT INTO statofornitore VALUES (2,'02','SOSPESO',0);

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
