-- SISCOTEL 1.7.3

SET search_path = :dbschema;

CREATE TABLE ter_regione(
    pkid                 NUMERIC(22, 0)    NOT NULL,
    cod_regione          varchar(20),
    des_regione          varchar(80),
    cod_istat_regione    INTEGER,
    CONSTRAINT ter_regione_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE ter_provincia(
    pkid                   NUMERIC(22, 0)    NOT NULL,
    cod_provincia          varchar(20),
    des_provincia          varchar(80),
    sigla                  varchar(2),
    cod_istat_provincia    INTEGER,
    fkid_regione           NUMERIC(22, 0)    NOT NULL,
    id_ente                NUMERIC(7, 0),
    CONSTRAINT ter_provincia_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ter_regione01 FOREIGN KEY (fkid_regione)
    REFERENCES ter_regione(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ter_comune(
    pkid              NUMERIC(22, 0)    NOT NULL,
    cod_comune        varchar(20),
    cod_istat         INTEGER,
    des_comune        varchar(80),
    des_comune2       varchar(80),
    fkid_provincia    NUMERIC(22, 0)    NOT NULL,
    des_provincia     varchar(80),
    cap_comune        varchar(10),
    cod_belfiore      varchar(4),
    cod_tribunale     INTEGER,
    cod_leva          INTEGER,
    dt_ini            DATE,
    dt_fin            DATE,
    fl_sto            INTEGER,
    CONSTRAINT ter_comune_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ter_provincia01 FOREIGN KEY (fkid_provincia)
    REFERENCES ter_provincia(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ter_consolato(
    pkid             NUMERIC(22, 0)    NOT NULL,
    cod_consolato    varchar(20),
    des_consolato    varchar(80),
    cod_istat        INTEGER,
    tipo             INTEGER,
    CONSTRAINT ter_consolato_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE ter_stato(
    pkid                      NUMERIC(22, 0)    NOT NULL,
    cod_stato                 varchar(20),
    cod_istat                 INTEGER,
    des_stato                 varchar(80),
    cod_catasto               varchar(4),
    sigla                     varchar(3),
    cod_leva                  INTEGER,
    fl_cee                    INTEGER,
    cod_istat_cittadinanza    INTEGER,
    des_cittadinanza_gen      varchar(80),
    des_cittadinanza_mas      varchar(80),
    des_cittadinanza_fem      varchar(80),
    dt_ini                    DATE,
    dt_fin                    DATE,
    CONSTRAINT ter_stato_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE ter_localita(
    pkid              NUMERIC(22, 0)    NOT NULL,
    cod_localita      varchar(20),
    des_localita      varchar(80),
    cap_localita      varchar(10),
    fkid_stato        NUMERIC(22, 0),
    des_stato         varchar(80),
    fkid_consolato    NUMERIC(22, 0),
    des_consolato     varchar(80),
    id_ente           NUMERIC(7, 0),
    CONSTRAINT ter_localita_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ter_consolato02 FOREIGN KEY (fkid_consolato)
    REFERENCES ter_consolato(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_stato03 FOREIGN KEY (fkid_stato)
    REFERENCES ter_stato(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ana_soggetto(
    pkid                     NUMERIC(22, 0)     NOT NULL,
    provenienza              INTEGER,
    cod_soggetto             varchar(20),
    codice_fiscale           varchar(16),
    flg_cf_calcolato         INTEGER,
    natura                   INTEGER,
    tipo_natura              INTEGER,
    partita_iva              varchar(11),
    cognome                  varchar(36),
    nome                     varchar(36),
    altri_nomi               varchar(36),
    denominazione            varchar(100),
    dt_nascita               DATE,
    precisione_dt_nascita    INTEGER,
    sesso                    INTEGER,
    note                     varchar(1000),
    fkid_comune_nascita      NUMERIC(22, 0),
    des_comune_nascita       varchar(80),
    des_provincia_nascita    varchar(80),
    fkid_stato_nascita       NUMERIC(22, 0),
    des_stato_nascita        varchar(80),
    fkid_localita_nascita    NUMERIC(22, 0),
    des_localita_nascita     varchar(80),
    flg_stato                INTEGER,
    fkid_rappresentante      INTEGER,
    id_ente                  NUMERIC(7, 0),
    dt_ini                   DATE,
    dt_fin                   DATE,
    dt_mod                   TIMESTAMP,
    CONSTRAINT ana_soggetto_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ter_comune06 FOREIGN KEY (fkid_comune_nascita)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_localita05 FOREIGN KEY (fkid_localita_nascita)
    REFERENCES ter_localita(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_stato06 FOREIGN KEY (fkid_stato_nascita)
    REFERENCES ter_stato(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ter_centro_nascita(
    pkid           NUMERIC(22, 0)    NOT NULL,
    cod_centro     varchar(20),
    des_centro     varchar(80),
    via_den        varchar(80),
    civ_num        INTEGER,
    civ_let        varchar(5),
    fkid_comune    NUMERIC(22, 0),
    id_ente        NUMERIC(7, 0),
    dt_mod         TIMESTAMP,
    CONSTRAINT ter_centro_nascita_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ter_comune14 FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ter_tribunale(
    pkid             NUMERIC(22, 0)    NOT NULL,
    cod_tribunale    varchar(20),
    des_tribunale    varchar(80),
    CONSTRAINT ter_tribunale_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE ana_atto(
    pkid                   NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto          NUMERIC(22, 0),
    tipo_atto              INTEGER,
    dt_riferimento         DATE,
    fkid_com_reg           NUMERIC(22, 0),
    des_com_reg            varchar(80),
    des_prov_reg           varchar(80),
    fkid_centro_nascita    NUMERIC(22, 0),
    des_centro_nascita     varchar(80),
    anno_iscrizione        INTEGER,
    num_iscrizione         INTEGER,
    parte_iscrizione       varchar(2),
    serie_iscrizione       varchar(6),
    vol_iscrizione         varchar(4),
    uff_iscrizione         varchar(4),
    fkid_com_tras          NUMERIC(22, 0),
    des_com_tras           varchar(80),
    des_prov_tras          varchar(80),
    anno_trascrizione      INTEGER,
    num_trascrizione       INTEGER,
    parte_trascrizione     varchar(2),
    serie_trascrizione     varchar(6),
    vol_trascrizione       varchar(4),
    uff_trascrizione       varchar(4),
    n_sen_divorzio         INTEGER,
    fkid_tribunale         NUMERIC(22, 0),
    des_tribunale          varchar(80),
    dt_sen_divorzio        DATE,
    id_ente                NUMERIC(7, 0),
    dt_mod                 TIMESTAMP,
    CONSTRAINT ana_atto_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto35 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT centro_nascita01 FOREIGN KEY (fkid_centro_nascita)
    REFERENCES ter_centro_nascita(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_comune12 FOREIGN KEY (fkid_com_reg)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_comune13 FOREIGN KEY (fkid_com_tras)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_tribunale01 FOREIGN KEY (fkid_tribunale)
    REFERENCES ter_tribunale(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ana_atto_annota(
    pkid          NUMERIC(22, 0)    NOT NULL,
    fkid_atto     NUMERIC(22, 0),
    dt_nota       DATE,
    codifica      varchar(20),
    nota          varchar(255),
    flg_stampa    INTEGER,
    id_ente       NUMERIC(7, 0),
    dt_mod        TIMESTAMP,
    CONSTRAINT ana_atto_annota_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_atto01 FOREIGN KEY (fkid_atto)
    REFERENCES ana_atto(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ana_cancellazione(
    pkid                 NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto        NUMERIC(22, 0),
    cod_cancellazione    varchar(20),
    des_cancellazione    varchar(80),
    dt_can               DATE,
    des_motivo           varchar(80),
    dt_dec               DATE,
    fkid_comune          NUMERIC(22, 0),
    des_comune           varchar(80),
    des_provincia        varchar(80),
    num_pratica          INTEGER,
    dt_def_pratica       DATE,
    fkid_stato           NUMERIC(22, 0),
    des_stato            varchar(80),
    fkid_localita        NUMERIC(22, 0),
    des_localita         varchar(80),
    id_ente              NUMERIC(7, 0),
    dt_ini               DATE,
    dt_fin               DATE,
    dt_mod               TIMESTAMP,
    CONSTRAINT ana_cancellazione_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto09 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_comune05 FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_localita04 FOREIGN KEY (fkid_localita)
    REFERENCES ter_localita(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_stato05 FOREIGN KEY (fkid_stato)
    REFERENCES ter_stato(pkid) ON DELETE CASCADE
) 
;

CREATE TABLE ana_carta_identita(
    pkid                 NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto        NUMERIC(22, 0),
    dt_emissione         DATE,
    numcarta             varchar(20),
    flg_residente        INTEGER,
    flg_espatrio         INTEGER,
    flg_elettronica      INTEGER,
    tipo                 INTEGER,
    stato                INTEGER,
    prog_denuncia        INTEGER,
    dt_denuncia          DATE,
    dt_scadenza          DATE,
    fkid_comune          NUMERIC(22, 0),
    des_comune           varchar(80),
    des_provincia        varchar(80),
    des_statocivile      varchar(80),
    professione          varchar(80),
    statura              varchar(20),
    capelli              varchar(80),
    occhi                varchar(80),
    segniparticolari1    varchar(80),
    segniparticolari2    varchar(80),
    segniparticolari3    varchar(80),
    dt_rinnovo           DATE,
    id_ente              NUMERIC(7, 0),
    dt_ini               DATE,
    dt_fin               DATE,
    dt_mod               TIMESTAMP,
    CONSTRAINT ana_carta_identita_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto25 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_comune09 FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE
);


CREATE TABLE ter_cittadinanza(
    pkid                NUMERIC(22, 0)    NOT NULL,
    cod_cittadinanza    varchar(20),
    cod_istat           INTEGER,
    des_cittadinanza    varchar(80),
    CONSTRAINT ter_cittadinanza_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE ana_cittadinanza(
    pkid                 NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto        NUMERIC(22, 0),
    fkid_cittadinanza    NUMERIC(22, 0),
    des_cittadinanza     varchar(80),
    id_ente              NUMERIC(7, 0),
    dt_ini               DATE,
    dt_fin               DATE,
    dt_mod               TIMESTAMP,
    CONSTRAINT ana_cittadinanza_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto05 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_cittadinanza01 FOREIGN KEY (fkid_cittadinanza)
    REFERENCES ter_cittadinanza(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ana_economica(
    pkid              NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto     NUMERIC(22, 0),
    cod_banca         varchar(20),
    des_banca         varchar(80),
    abi               varchar(20),
    cab               varchar(20),
    conto_corrente    varchar(20),
    cond_pagamento    varchar(80),
    telex             varchar(20),
    icin              varchar(20),
    ncin              varchar(20),
    reddito           NUMERIC(15, 2),
    dt_ini            DATE,
    dt_fin            DATE,
    id_ente           NUMERIC(7, 0),
    dt_mod            TIMESTAMP,
    CONSTRAINT ana_economica_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto33 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ter_area(
    pkid              NUMERIC(22, 0)    NOT NULL,
    cod_area          varchar(20),
    cod_istat_area    INTEGER,
    specie            varchar(20),
    des_area          varchar(80),
    ordinamento       varchar(100),
    id_ente           NUMERIC(7, 0),
    dt_ini            DATE,
    dt_fin            DATE,
    dt_mod            TIMESTAMP,
    CONSTRAINT ter_area_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE ter_ente(
    pkid           NUMERIC(22, 0)    NOT NULL,
    cod_ente_bo    varchar(20),
    des_ente       varchar(80),
    via_deno       varchar(80),
    num_civ        INTEGER,
    let_civ        varchar(5),
    cap            varchar(10),
    fkid_comune    NUMERIC(22, 0),
    e_mail         varchar(50),
    id_ente        NUMERIC(7, 0),
    dt_mod         TIMESTAMP,
    CONSTRAINT ter_ente_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ter_comune11 FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ter_accesso(
    pkid              NUMERIC(22, 0)    NOT NULL,
    fkid_area         NUMERIC(22, 0),
    civico_num        INTEGER,
    civico_let        varchar(5),
    fl_km             INTEGER,
    zona_rsu          varchar(4),
    zona_osap         varchar(4),
    sez_censimento    INTEGER,
    sez_elettorale    INTEGER,
    codice_acq        INTEGER,
    codice_gas        INTEGER,
    cap               varchar(10),
    fkid_asl          NUMERIC(22, 0),
    fkid_scuola       NUMERIC(22, 0),
    fkid_cc           NUMERIC(22, 0),
    id_ente           NUMERIC(7, 0),
    dt_mod            TIMESTAMP,
    CONSTRAINT ter_accesso_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ter_area05 FOREIGN KEY (fkid_area)
    REFERENCES ter_area(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_ente01 FOREIGN KEY (fkid_cc)
    REFERENCES ter_ente(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_ente02 FOREIGN KEY (fkid_scuola)
    REFERENCES ter_ente(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_ente03 FOREIGN KEY (fkid_asl)
    REFERENCES ter_ente(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ter_sez_ele(
    pkid                          NUMERIC(22, 0)    NOT NULL,
    num_sezione                   INTEGER,
    denominazione                 varchar(38),
    tipo_sezione                  INTEGER,
    des_via                       varchar(80),
    num_civ                       INTEGER,
    let_civ                       varchar(4),
    circ_parlamentoeuropeo        varchar(80),
    collegio_parlamentoeuropeo    varchar(80),
    circ_senato                   varchar(80),
    collegio_senato               varchar(80),
    circ_camera                   varchar(80),
    collegio_camera               varchar(80),
    circ_regionali                varchar(80),
    collegio_regionali            varchar(80),
    circ_provinciali              varchar(80),
    collegio_provinciali          varchar(80),
    circ_circoscrizione           varchar(80),
    collegio_circoscrizionali     varchar(80),
    id_ente                       NUMERIC(7, 0),
    dt_mod                        TIMESTAMP,
    CONSTRAINT ter_sez_ele_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE ana_fam(
    pkid            NUMERIC(22, 0)    NOT NULL,
    cod_famiglia    varchar(20),
    tipo_ana        INTEGER,
    tipo_scheda     INTEGER,
    num_scheda      INTEGER,
    intestatario    varchar(80),
    specie_conv     varchar(80),
    des_conv        varchar(80),
    id_ente         NUMERIC(7, 0),
    dt_ini          DATE,
    dt_fin          DATE,
    dt_mod          TIMESTAMP,
    CONSTRAINT ana_fam_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE ana_relazione(
    pkid             NUMERIC(22, 0)    NOT NULL,
    cod_relazione    varchar(20),
    sesso            INTEGER,
    des_relazione    varchar(80),
    grado            varchar(15),
    CONSTRAINT ana_relazione_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE ana_fam_componente(
    pkid                  NUMERIC(22, 0)    NOT NULL,
    fkid_fam              NUMERIC(22, 0),
    fkid_soggetto         NUMERIC(22, 0),
    fkid_relazione        NUMERIC(22, 0),
    des_relazione         varchar(80),
    motivo_ingresso       varchar(30),
    num_ordine            INTEGER,
    dt_def_pratica_ing    DATE,
    dt_dec_ingresso       DATE,
    motivo_uscita         varchar(30),
    dt_def_pratica_usc    DATE,
    dt_dec_uscita         DATE,
    id_ente               NUMERIC(7, 0),
    dt_ini                DATE,
    dt_fin                DATE,
    dt_mod                TIMESTAMP,
    CONSTRAINT ana_fam_componente_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_fam1 FOREIGN KEY (fkid_fam)
    REFERENCES ana_fam(pkid) ON DELETE CASCADE,
    CONSTRAINT ana_relazione01 FOREIGN KEY (fkid_relazione)
    REFERENCES ana_relazione(pkid) ON DELETE CASCADE,
    CONSTRAINT ana_soggetto04 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ana_fornitore(
    pkid                      NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto             NUMERIC(22, 0),
    cod_fornitore             varchar(20),
    cod_banca                 varchar(20),
    des_banca                 varchar(80),
    cod_tributo_irpef         varchar(20),
    cod_tributo_irpef_reg     varchar(20),
    cod_tributo_irpef_prov    varchar(20),
    cod_tributo_irpef_com     varchar(20),
    cod_tributo_irap          varchar(20),
    abi                       varchar(6),
    cab                       varchar(6),
    conto_corrente            varchar(10),
    cod_attivita              varchar(20),
    condizioni_pagamento      varchar(80),
    banca                     varchar(80),
    telex                     varchar(20),
    icin                      varchar(38),
    ncin                      varchar(10),
    dt_ini                    DATE,
    dt_fin                    DATE,
    id_ente                   NUMERIC(7, 0),
    dt_mod                    TIMESTAMP,
    CONSTRAINT ana_fornitori_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto37 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ana_indirizzo(
    pkid              NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto     NUMERIC(22, 0),
    cod_indirizzo     varchar(20),
    tipo              varchar(3)      NOT NULL,
    fkid_area         NUMERIC(22, 0),
    des_area          varchar(80),
    num_civ           INTEGER,
    let_civ           varchar(5),
    colore            varchar(10),
    corte             varchar(9),
    scala             varchar(9),
    interno           varchar(9),
    piano             varchar(9),
    edificio          varchar(9),
    presso            varchar(50),
    telefono          varchar(30),
    fax               varchar(30),
    email             varchar(50),
    fkid_stato        NUMERIC(22, 0),
    des_stato         varchar(80),
    contea            varchar(50),
    fkid_localita     NUMERIC(22, 0),
    des_localita      varchar(80),
    cap               varchar(10),
    fkid_comune       NUMERIC(22, 0),
    des_comune        varchar(80),
    des_provincia     varchar(80),
    fkid_consolato    NUMERIC(22, 0),
    des_consolato     varchar(80),
    id_ente           NUMERIC(7, 0),
    dt_ini            DATE,
    dt_fin            DATE,
    dt_mod            TIMESTAMP,
    CONSTRAINT ana_indirizzo_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto07 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_area02 FOREIGN KEY (fkid_area)
    REFERENCES ter_area(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_comune03 FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_consolato03 FOREIGN KEY (fkid_consolato)
    REFERENCES ter_consolato(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_localita02 FOREIGN KEY (fkid_localita)
    REFERENCES ter_localita(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_stato02 FOREIGN KEY (fkid_stato)
    REFERENCES ter_stato(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ana_iscrizione(
    pkid              NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto     NUMERIC(22, 0),
    cod_iscrizione    varchar(20),
    des_iscrizione    varchar(80),
    dt_isc            DATE,
    des_motivo        varchar(80),
    dt_dec            DATE,
    fkid_comune       NUMERIC(22, 0),
    des_comune        varchar(80),
    des_provincia     varchar(80),
    num_pratica       INTEGER,
    dt_def_pratica    DATE,
    fkid_stato        NUMERIC(22, 0),
    des_stato         varchar(80),
    fkid_localita     NUMERIC(22, 0),
    des_localita      varchar(80),
    des_com_aire      varchar(80),
    id_ente           NUMERIC(7, 0),
    dt_ini            DATE,
    dt_fin            DATE,
    dt_mod            TIMESTAMP,
    CONSTRAINT ana_iscrizione_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto06 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_comune16 FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_localita01 FOREIGN KEY (fkid_localita)
    REFERENCES ter_localita(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_stato01 FOREIGN KEY (fkid_stato)
    REFERENCES ter_stato(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ana_patente(
    pkid                 NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto        NUMERIC(22, 0),
    num_patente          varchar(20),
    categoria            varchar(2),
    dt_rilascio          DATE,
    dt_scadenza          DATE,
    des_ente_rilascio    varchar(80),
    punti                INTEGER,
    id_ente              NUMERIC(7, 0),
    dt_ini               DATE,
    dt_fin               DATE,
    dt_mod               TIMESTAMP,
    CONSTRAINT ana_patente_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto14 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ana_pensione(
    pkid             NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto    NUMERIC(22, 0),
    des_pensione     varchar(80),
    cod_ente         varchar(20),
    des_ente         varchar(80),
    dt_ass           DATE,
    num_pensione     varchar(80),
    id_ente          NUMERIC(7, 0),
    dt_ini           DATE,
    dt_fin           DATE,
    dt_mod           TIMESTAMP,
    CONSTRAINT ana_pensione_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto13 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ana_permesso_soggiorno(
    pkid             NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto    NUMERIC(22, 0),
    fkid_comune      NUMERIC(22, 0),
    des_comune       varchar(80),
    des_provincia    varchar(80),
    tipo             INTEGER,
    numero           varchar(20),
    dt_rilascio      DATE,
    dt_rinnovo       DATE,
    dt_scadenza      DATE,
    motivo           varchar(100),
    id_ente          NUMERIC(7, 0),
    dt_ini           DATE,
    dt_fin           DATE,
    dt_mod           TIMESTAMP,
    CONSTRAINT ana_permesso_soggiorno_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto26 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_comune10 FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE
) 
;

CREATE TABLE ana_professione
(
  pkid          NUMERIC(22, 0) NOT NULL,
  fkid_soggetto NUMERIC(22, 0),
  cod_professione     varchar(20),
  des_professione     varchar(80),
  cod_set_professione varchar(20),
  set_professione     varchar(80),
  cond_professione    varchar(80),
  id_ente             NUMERIC(7, 0),
  dt_ini              DATE, 
  dt_fin              DATE,
  dt_mod              TIMESTAMP,
  CONSTRAINT ana_professione_pkey PRIMARY KEY (pkid), 
  CONSTRAINT ana_soggetto15 FOREIGN KEY (fkid_soggetto)
  REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
);

CREATE TABLE ana_rappresentante(
    pkid                   NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto          NUMERIC(22, 0),
    fkid_rappresentante    NUMERIC(22, 0),
    flg_rappresentante     INTEGER,
    des_rappresentante     varchar(100),
    des_titolo_rapp        varchar(100),
    cognome                varchar(36),
    nome                   varchar(36),
    codice_fiscale         varchar(16),
    dt_nascita             DATE,
    loc_nascita            varchar(200),
    recapito               varchar(200),
    id_ente                NUMERIC(7, 0),
    dt_ini                 DATE,
    dt_fin                 DATE,
    dt_mod                 TIMESTAMP,
    CONSTRAINT ana_rappresentante_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto01 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ana_soggetto03 FOREIGN KEY (fkid_rappresentante)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ana_scheda(
    pkid                 NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto        NUMERIC(22, 0),
    cod_scheda           varchar(20),
    tipo_ana             INTEGER,
    motivo_iscrizione    varchar(80),
    fkid_padre           NUMERIC(22, 0),
    cognome_padre        varchar(36),
    nome_padre           varchar(36),
    fkid_madre           NUMERIC(22, 0),
    cognome_madre        varchar(36),
    nome_madre           varchar(36),
    flg_certificato      INTEGER,
    flg_cittadinanza     INTEGER,
    id_ente              NUMERIC(7, 0),
    dt_ini               DATE,
    dt_fin               DATE,
    dt_mod               TIMESTAMP,
    CONSTRAINT ana_scheda_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto10 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ana_soggetto11 FOREIGN KEY (fkid_padre)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ana_soggetto12 FOREIGN KEY (fkid_madre)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ana_soggetto_ele(
    pkid                     NUMERIC(22, 0)     NOT NULL,
    fkid_soggetto            NUMERIC(22, 0),
    cod_soggetto             varchar(20),
    codice_fiscale           varchar(16),
    cognome                  varchar(36),
    nome                     varchar(36),
    altri_nomi               varchar(36),
    dt_nascita               DATE,
    precisione_dt_nascita    INTEGER,
    sesso                    INTEGER,
    note                     varchar(1000),
    fkid_comune_nascita      NUMERIC(22, 0),
    des_comune_nascita       varchar(80),
    des_provincia_nascita    varchar(80),
    fkid_stato_nascita       NUMERIC(22, 0),
    des_stato_nascita        varchar(80),
    fkid_localita_nascita    NUMERIC(22, 0),
    des_localita_nascita     varchar(80),
    des_stato_civ            varchar(80),
    coniuge                  varchar(80),
    id_ente                  NUMERIC(7, 0),
    dt_ini                   DATE,
    dt_fin                   DATE,
    dt_mod                   TIMESTAMP,
    CONSTRAINT ana_soggetto_ele_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto27 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_comune01 FOREIGN KEY (fkid_comune_nascita)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_stato08 FOREIGN KEY (fkid_stato_nascita)
    REFERENCES ter_stato(pkid) ON DELETE CASCADE
) 
;

CREATE TABLE ana_ele(
    pkid              NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto     NUMERIC(22, 0),
    fkid_sez_ele      NUMERIC(22, 0),
    sesso             INTEGER,
    num_lista_gen     INTEGER,
    num_lista_sez     INTEGER,
    num_sez           INTEGER,
    num_fascicolo     INTEGER,
    num_tessera       INTEGER,
    flg_scrutatore    INTEGER,
    flg_presidente    INTEGER,
    anno_iscr         INTEGER,
    fkid_accesso      NUMERIC(22, 0),
    ind_sede_ele      varchar(200),
    id_ente           NUMERIC(7, 0),
    dt_mod            TIMESTAMP,
    CONSTRAINT ana_ele_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto22 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_accesso01 FOREIGN KEY (fkid_accesso)
    REFERENCES ter_accesso(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_sez_ele01 FOREIGN KEY (fkid_sez_ele)
    REFERENCES ter_sez_ele(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ana_soggetto_giur(
    pkid              NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto     NUMERIC(22, 0),
    num_ccia          varchar(20),
    sede_ccia         varchar(200),
    dt_ccia           DATE,
    cod_settore       varchar(20),
    des_settore       varchar(80),
    cod_commercio     varchar(20),
    des_commercio     varchar(80),
    num_isc_albo      varchar(20),
    cod_albo          varchar(20),
    des_albo          varchar(80),
    provincia_albo    varchar(100),
    id_ente           NUMERIC(7, 0),
    dt_mod            TIMESTAMP,
    CONSTRAINT ana_soggetto_giur_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto02 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
) 
;

CREATE TABLE ser_provenienza(
	pkid            NUMERIC(22,0) NOT NULL,            
	cod_provenienza varchar(20),
	des_provenienza varchar(80),
	CONSTRAINT ser_provenienza_pkey PRIMARY KEY (pkid)
)
;


CREATE TABLE ana_soggetto_prov(
    pkid               NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto      NUMERIC(22, 0),
    fkid_provenienza   NUMERIC(22, 0),
    id_servizio        NUMERIC(22, 0),
    id_ente            NUMERIC(7, 0),
    dt_mod             TIMESTAMP,
    CONSTRAINT ana_prov_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto38 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ana_sogg_prov_ser_prov FOREIGN KEY (fkid_provenienza)
    REFERENCES ser_provenienza(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ana_stciv(
    pkid              NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto     NUMERIC(22, 0),
    cod_stciv         varchar(20),
    des_stciv         varchar(80),
    dt_stciv          DATE,
    cf_coniuge        varchar(16),
    fkid_coniuge      NUMERIC(22, 0),
    cognome_cg        varchar(36),
    nome_cg           varchar(36),
    fkid_comune       NUMERIC(22, 0),
    des_comune        varchar(80),
    des_provincia     varchar(80),
    fkid_stato        NUMERIC(22, 0),
    des_stato         varchar(80),
    fkid_localita     NUMERIC(22, 0),
    des_localita      varchar(80),
    fkid_consolato    NUMERIC(22, 0),
    des_consolato     varchar(80),
    id_ente           NUMERIC(7, 0),
    dt_ini            DATE,
    dt_fin            DATE,
    dt_mod            TIMESTAMP,
    CONSTRAINT ana_stciv_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto23 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ana_soggetto24 FOREIGN KEY (fkid_coniuge)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_comune08 FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_consolato01 FOREIGN KEY (fkid_consolato)
    REFERENCES ter_consolato(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_localita06 FOREIGN KEY (fkid_localita)
    REFERENCES ter_localita(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_stato07 FOREIGN KEY (fkid_stato)
    REFERENCES ter_stato(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ana_studio(
    pkid             NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto    NUMERIC(22, 0),
    anno             INTEGER,
    des_studio       varchar(80),
    des_istituto     varchar(80),
    fkid_comune      NUMERIC(22, 0),
    des_comune       varchar(80),
    des_provincia    varchar(80),
    fkid_stato       NUMERIC(22, 0),
    des_stato        varchar(80),
    fkid_localita    NUMERIC(22, 0),
    des_localita     varchar(80),
    id_ente          NUMERIC(7, 0),
    dt_ini           DATE,
    dt_fin           DATE,
    dt_mod           TIMESTAMP,
    CONSTRAINT ana_studio_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto08 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_comune04 FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_localita03 FOREIGN KEY (fkid_localita)
    REFERENCES ter_localita(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_stato04 FOREIGN KEY (fkid_stato)
    REFERENCES ter_stato(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE cat_aot(
    pkid                NUMERIC(22, 0)     NOT NULL,
    tipo                varchar(10),
    cod_ecografico      varchar(70),
    cod_catasto         varchar(50),
    cod_comune          varchar(20),
    aot_aggregazione    NUMERIC(22, 0)     NOT NULL,
    cod_istat           varchar(10),
    abitato             varchar(10),
    rione               varchar(10),
    isolato             varchar(10),
    edificio            varchar(10),
    uiu                 varchar(10),
    note                varchar(1000),
    dt_ini              DATE,
    dt_fin              DATE,
    id_ente             NUMERIC(7, 0),
    dt_mod              TIMESTAMP,
    CONSTRAINT cat_aot_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE cat_prg(
    pkid       NUMERIC(22, 0)     NOT NULL,
    cod_prg    varchar(80),
    des_prg    varchar(80),
    anno       INTEGER,
    note       varchar(1000),
    dt_ini     DATE,
    dt_fin     DATE,
    id_ente    NUMERIC(7, 0),
    dt_mod     TIMESTAMP,
    CONSTRAINT cat_prg_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE cat_aot_prg(
    pkid        NUMERIC(22, 0)    NOT NULL,
    fkid_aot    NUMERIC(22, 0),
    fkid_prg    NUMERIC(22, 0),
    id_ente     NUMERIC(7, 0),
    dt_mod      TIMESTAMP,
    CONSTRAINT cat_aot_prg_pkid PRIMARY KEY (pkid), 
    CONSTRAINT cat_aot04 FOREIGN KEY (fkid_aot)
    REFERENCES cat_aot(pkid) ON DELETE CASCADE,
    CONSTRAINT cat_prg01 FOREIGN KEY (fkid_prg)
    REFERENCES cat_prg(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE cat_dati(
    pkid               NUMERIC(22, 0)       NOT NULL,
    fkid_aot           NUMERIC(22, 0),
    zona               varchar(10),
    categoria          varchar(10),
    classe             varchar(10),
    prot_notifica      varchar(20),
    prot_anno          INTEGER,
    cod_consistenza    varchar(20),
    des_consistenza    varchar(80),
    consistenza        FLOAT,
    ettari             FLOAT,
    are_valore         FLOAT,
    centiare_valore    FLOAT,
    qualita            varchar(10),
    atto_ini           varchar(1000),
    atto_fin           varchar(1000),
    mutazione_ini      varchar(20),
    mutazione_fin      varchar(20),
    note               varchar(1000),
    dt_ini             DATE,
    dt_fin             DATE,
    id_ente            NUMERIC(7, 0),
    dt_mod             TIMESTAMP,
    CONSTRAINT cat_dati_pkey PRIMARY KEY (pkid), 
    CONSTRAINT cat_aot01 FOREIGN KEY (fkid_aot)
    REFERENCES cat_aot(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE cat_identificativo(
    pkid         NUMERIC(22, 0)    NOT NULL,
    fkid_aot     NUMERIC(22, 0),
    tipo         varchar(10),
    id_comune    varchar(10),
    sezione      varchar(10),
    foglio       varchar(10),
    mappale      varchar(11),
    sub          varchar(10),
    anno_prot    INTEGER,
    num_prot     varchar(11),
    dt_ini       DATE,
    dt_fin       DATE,
    id_ente      NUMERIC(7, 0),
    dt_mod       TIMESTAMP,
    CONSTRAINT cat_identificativo_pkid PRIMARY KEY (pkid), 
    CONSTRAINT cat_aot03 FOREIGN KEY (fkid_aot)
    REFERENCES cat_aot(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE cat_indirizzo(
    pkid             NUMERIC(22, 0)    NOT NULL,
    fkid_aot         NUMERIC(22, 0),
    cod_indirizzo    varchar(20),
    fkid_area        NUMERIC(22, 0),
    des_area         varchar(80),
    num_civ          INTEGER,
    let_civ          varchar(5),
    colore           varchar(10),
    corte            varchar(9),
    scala            varchar(9),
    interno          varchar(9),
    piano            varchar(9),
    edificio         varchar(9),
    dt_ini           DATE,
    dt_fin           DATE,
    id_ente          NUMERIC(7, 0),
    dt_mod           TIMESTAMP,
    CONSTRAINT cat_indirizzo_pkey PRIMARY KEY (pkid), 
    CONSTRAINT cat_aot05 FOREIGN KEY (fkid_aot)
    REFERENCES cat_aot(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_area10 FOREIGN KEY (fkid_area)
    REFERENCES ter_area(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE cat_proprietario(
    pkid             NUMERIC(22, 0)       NOT NULL,
    fkid_aot         NUMERIC(22, 0),
    fkid_soggetto    NUMERIC(22, 0),
    cod_titolo       varchar(20),
    des_titolo       varchar(80),
    proprieta        FLOAT,
    tipo             varchar(80),
    tipo_partita     varchar(1),
    partita          varchar(10),
    atto_ini         varchar(1000),
    atto_fin         varchar(1000),
    id_titolarita    varchar(20),
    mutazione_ini    varchar(20),
    mutazione_fin    varchar(20),
    note             varchar(1000),
    dt_ini           DATE,
    dt_fin           DATE,
    id_ente          NUMERIC(7, 0),
    dt_mod           TIMESTAMP,
    CONSTRAINT cat_proprietario_pkid PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto39 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT cat_aot06 FOREIGN KEY (fkid_aot)
    REFERENCES cat_aot(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE cat_rendita(
    pkid               NUMERIC(22, 0)    NOT NULL,
    fkid_aot           NUMERIC(22, 0),
    rendita            NUMERIC(15, 2),
    reddito_agrario    NUMERIC(15, 2),
    dt_ini             DATE,
    dt_fin             DATE,
    id_ente            NUMERIC(7, 0),
    dt_mod             TIMESTAMP,
    CONSTRAINT cat_rendita_pkid PRIMARY KEY (pkid), 
    CONSTRAINT cat_aot02 FOREIGN KEY (fkid_aot)
    REFERENCES cat_aot(pkid) ON DELETE CASCADE
) 
;

CREATE TABLE com_denuncia(
    pkid                  NUMERIC(22, 0)     NOT NULL,
    fkid_soggetto         NUMERIC(22, 0),
    id_pratica            NUMERIC(22, 0)     NOT NULL,
    cod_denuncia_bo       varchar(40),
    cod_denuncia          varchar(40),
    des_denuncia          varchar(80),
    cod_tipo_denuncia     varchar(20),
    des_tipo_denuncia     varchar(80),
    dt_denuncia           DATE,
    dt_cessazione         DATE,
    id_servizio           NUMERIC(22, 0)     NOT NULL,
    num_protocollo        varchar(20),
    des_attivita          varchar(100),
    cod_struttura         varchar(20),
    des_struttura         varchar(80),
    den_subentro          varchar(100),
    num_aut_subentro      varchar(20),
    dt_aut_subentro       DATE,
    cfisc_subentro        varchar(20),
    cod_causa_subentro    varchar(20),
    des_causa_subentro    varchar(80),
    flg_riduzione         NUMERIC(38, 0),
    flg_altre_attivita    NUMERIC(38, 0),
    flg_trasferimento     NUMERIC(38, 0),
    dt_ini_sospensione    DATE,
    dt_fin_sospensione    DATE,
    sup_ampliata          varchar(10),
    sup_ridotta           varchar(10),
    sup_complessiva       varchar(10),
    note                  varchar(1000),
    note_ente             varchar(1000),
    id_stato              NUMERIC(38, 0),
    cod_stato             varchar(20),
    des_stato             varchar(80),
    dt_stato              DATE,
    id_ente               NUMERIC(7, 0),
    dt_mod                TIMESTAMP,
    CONSTRAINT com_denuncia_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_sog_com_den FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE com_allegato(
    pkid             NUMERIC(22, 0)     NOT NULL,
    fkid_denuncia    NUMERIC(22, 0),
    tipo_allegato    varchar(20),
    dt_allegato      DATE,
    dt_prot          DATE,
    num_prot         varchar(20),
    cod_allegato     varchar(20),
    des_allegato     varchar(80),
    note             varchar(1000),
    id_ente          NUMERIC(7, 0),
    dt_mod           TIMESTAMP,
    CONSTRAINT com_allegato_pkey PRIMARY KEY (pkid), 
    CONSTRAINT com_all_com_den FOREIGN KEY (fkid_denuncia)
    REFERENCES com_denuncia(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE com_esercizio(
    pkid                    NUMERIC(22, 0)    NOT NULL,
    fkid_denuncia           NUMERIC(22, 0),
    fkid_soggetto           NUMERIC(22, 0),
    fkid_comune             NUMERIC(22, 0),
    cod_fiscale_titolare    varchar(16),
    nome_titolare           varchar(100),
    cognome_titolare        varchar(100),
    flg_accorpato           INTEGER,
    flg_centrocom           INTEGER,
    flg_stagionale          INTEGER,
    dt_ini_stagione         DATE,
    dt_fin_stagione         DATE,
    num_autorizzazione      varchar(20),
    dt_autorizzazione       DATE,
    superficie              varchar(10),
    volume                  varchar(10),
    des_centrocom           varchar(200),
    num_prov_centrocom      varchar(20),
    dt_prov_centrocom       DATE,
    des_cert_centrocom      varchar(100),
    fkid_area               NUMERIC(22, 0),
    des_area                varchar(80),
    num_civ                 INTEGER,
    let_civ                 varchar(5),
    cap                     varchar(20),
    des_comune              varchar(80),
    des_provincia           varchar(80),
    id_ente                 NUMERIC(7, 0),
    dt_mod                  TIMESTAMP,
    CONSTRAINT com_esercizo_pkey PRIMARY KEY (pkid), 
    CONSTRAINT com_es_com_den FOREIGN KEY (fkid_denuncia)
    REFERENCES com_denuncia(pkid) ON DELETE CASCADE,
    CONSTRAINT ana_sog_com_ese FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT com_ese_ter_com FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE com_merceologico(
    pkid              NUMERIC(22, 0)    NOT NULL,
    fkid_esercizio    NUMERIC(22, 0),
    cod_genere        varchar(20),
    des_genere        varchar(80),
    volume            varchar(10),
    flg_speciale      varchar(10),
    superficie        varchar(10),
    id_ente           NUMERIC(7, 0),
    dt_mod            TIMESTAMP,
    CONSTRAINT com_merceologico_pkey PRIMARY KEY (pkid), 
    CONSTRAINT com_mer_com_ese FOREIGN KEY (fkid_esercizio)
    REFERENCES com_esercizio(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE com_requisiti(
    pkid             NUMERIC(22, 0)    NOT NULL,
    cod_requisto     varchar(20),
    des_requisito    varchar(200),
    id_ente          NUMERIC(7, 0),
    dt_mod           TIMESTAMP,
    CONSTRAINT com_requisito PRIMARY KEY (pkid)
) 
;


CREATE TABLE com_soggetto(
    pkid                 NUMERIC(22, 0)     NOT NULL,
    fkid_soggetto        NUMERIC(22, 0),
    fkid_denuncia        NUMERIC(22, 0),
    cod_tipo_soggetto    varchar(20),
    des_tipo_soggetto    varchar(80),
    des_soggetto         varchar(80),
    cod_tipo_ruolo       varchar(20),
    des_tipo_ruolo       varchar(80),
    note                 varchar(1000),
    id_ente              NUMERIC(7, 0),
    dt_mod               TIMESTAMP,
    CONSTRAINT com_soggetto_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_sog_com_sog FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT com_sog_com_den FOREIGN KEY (fkid_denuncia)
    REFERENCES com_denuncia(pkid) ON DELETE CASCADE
) 
;

CREATE TABLE edi_pratica(
    pkid               NUMERIC(22, 0)    NOT NULL,
    id_servizio        NUMERIC(22, 0)    NOT NULL,
    cod_pratica        varchar(20),
    des_pratica        varchar(80),
    cod_tipo_pratica   varchar(20),
    des_tipo_pratica   varchar(80),
    cod_stato          varchar(20),
    des_stato          varchar(80),
    num_pratica        varchar(20),
    dt_pratica         DATE,
    num_protocollo     varchar(20),
    dt_protocollo      DATE,
    num_provvedimento  varchar(20),
    dt_provvedimento   DATE,
    dt_ini_lavori      DATE,
    dt_fin_lavori      DATE,
    num_registro       varchar(20),
    oggetto            varchar(1000),
    dt_decadimento     DATE,
    des_decadimento    varchar(80),
    dt_agibilita       DATE,
    num_agibilita      varchar(20),
    volume             varchar(10),
    superficie         varchar(10),
    id_pratica         NUMERIC(22, 0),
    id_ente            NUMERIC(7, 0),
    dt_mod             TIMESTAMP,
    CONSTRAINT PK128 PRIMARY KEY (pkid)
) 
;

CREATE TABLE edi_allegato(
    pkid             NUMERIC(22, 0)     NOT NULL,
    fkid_pratica     NUMERIC(22, 0),
    tipo_allegato    varchar(20),
    dt_allegato      DATE,
    dt_prot          DATE,
    num_prot         varchar(20),
    cod_allegato     varchar(20),
    des_allegato     varchar(80),
    note             varchar(1000),
    id_ente          NUMERIC(7, 0),
    dt_mod           TIMESTAMP,
    CONSTRAINT PK132 PRIMARY KEY (pkid), 
    CONSTRAINT edi_pratica05 FOREIGN KEY (fkid_pratica)
    REFERENCES edi_pratica(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE edi_collegata(
    pkid                 NUMERIC(22, 0)    NOT NULL,
    fkid_pratica         NUMERIC(22, 0)    NOT NULL,
    fkid_pr_collegata    NUMERIC(22, 0)    NOT NULL,
    id_ente              NUMERIC(7, 0),
    dt_mod               TIMESTAMP,
    CONSTRAINT PK134 PRIMARY KEY (pkid), 
    CONSTRAINT edi_pratica07 FOREIGN KEY (fkid_pr_collegata)
    REFERENCES edi_pratica(pkid) ON DELETE CASCADE,
    CONSTRAINT edi_pratica08 FOREIGN KEY (fkid_pratica)
    REFERENCES edi_pratica(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE edi_identificativo(
    pkid              NUMERIC(22, 0)    NOT NULL,
    fkid_pratica      NUMERIC(22, 0),
    tipo              varchar(10),
    cod_ecografico    varchar(70),
    id_comune         varchar(10),
    sezione           varchar(10),
    foglio            varchar(10),
    mappale           varchar(11),
    sub               varchar(10),
    anno_prot         INTEGER,
    num_prot          varchar(11),
    des_prg           varchar(100),
    dt_ini            DATE,
    dt_fin            DATE,
    id_ente           NUMERIC(7, 0),
    dt_mod            TIMESTAMP,
    CONSTRAINT PK136 PRIMARY KEY (pkid), 
    CONSTRAINT edi_id_edi_pra FOREIGN KEY (fkid_pratica)
    REFERENCES edi_pratica(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE edi_intervento(
    pkid              NUMERIC(22, 0)    NOT NULL,
    fkid_pratica      NUMERIC(22, 0)    NOT NULL,
    cod_intervento    varchar(20),
    des_intervento    varchar(80),
    id_ente           NUMERIC(7, 0),
    dt_mod            TIMESTAMP,
    CONSTRAINT PK129 PRIMARY KEY (pkid), 
    CONSTRAINT edi_pratica01 FOREIGN KEY (fkid_pratica)
    REFERENCES edi_pratica(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE edi_pagamento(
    pkid             NUMERIC(22, 0)     NOT NULL,
    fkid_pratica     NUMERIC(22, 0),
    cod_pagamento    varchar(100),
    oggetto          varchar(200),
    dt_pagamento     DATE,
    num_rata         INTEGER,
    tot_pagato       NUMERIC(15, 2),
    note             varchar(1000),
    id_ente          NUMERIC(7, 0),
    dt_mod           TIMESTAMP,
    CONSTRAINT pag_bolletta_pkey_1 PRIMARY KEY (pkid), 
    CONSTRAINT edi_pag_edi_pra FOREIGN KEY (fkid_pratica)
    REFERENCES edi_pratica(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE edi_pratica_dett(
    pkid             NUMERIC(22, 0)    NOT NULL,
    fkid_pratica     NUMERIC(22, 0),
    cod_tipo_voce    varchar(20),
    des_voce         varchar(200),
    valore           varchar(200),
    importo          NUMERIC(15, 2),
    id_ente          NUMERIC(7, 0),
    dt_mod           TIMESTAMP,
    CONSTRAINT pag_bolletta_dett_pkey_1 PRIMARY KEY (pkid), 
    CONSTRAINT edi_pdett_edi_pra FOREIGN KEY (fkid_pratica)
    REFERENCES edi_pratica(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE edi_prg_ident(
    pkid          NUMERIC(22, 0)    NOT NULL,
    fkid_prg      NUMERIC(22, 0),
    fkid_ident    NUMERIC(22, 0),
    dt_ini        DATE,
    dt_fin        DATE,
    id_ente       NUMERIC(7, 0),
    dt_mod        TIMESTAMP,
    CONSTRAINT PK150 PRIMARY KEY (pkid), 
    CONSTRAINT edi_pid_cat_prg FOREIGN KEY (fkid_prg)
    REFERENCES cat_prg(pkid) ON DELETE CASCADE,
    CONSTRAINT edi_pid_edi_id FOREIGN KEY (fkid_ident)
    REFERENCES edi_identificativo(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE edi_soggetto(
    pkid                 NUMERIC(22, 0)     NOT NULL,
    fkid_pratica         NUMERIC(22, 0),
    fkid_soggetto        NUMERIC(22, 0),
    cod_tipo_soggetto    varchar(20),
    des_tipo_soggetto    varchar(80),
    des_soggetto         varchar(80),
    cod_tipo_ruolo       varchar(10),
    des_tipo_ruolo       varchar(80),
    note                 varchar(1000),
    id_ente              NUMERIC(7, 0),
    dt_mod               TIMESTAMP,
    CONSTRAINT PK131 PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto36 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT edi_pratica03 FOREIGN KEY (fkid_pratica)
    REFERENCES edi_pratica(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE edi_ubicazione(
    pkid            NUMERIC(22, 0)    NOT NULL,
    fkid_pratica    NUMERIC(22, 0),
    fkid_area       NUMERIC(22, 0),
    des_area        varchar(80),
    num_civ         INTEGER,
    let_civ         varchar(5),
    id_ente         NUMERIC(7, 0),
    dt_mod          TIMESTAMP,
    CONSTRAINT PK130 PRIMARY KEY (pkid), 
    CONSTRAINT edi_pratica02 FOREIGN KEY (fkid_pratica)
    REFERENCES edi_pratica(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_area06 FOREIGN KEY (fkid_area)
    REFERENCES ter_area(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE esinextid(
    pkid                     NUMERIC(22, 0)    NOT NULL,
    nome_tabella             varchar(255)    NOT NULL,
    incremento               NUMERIC(7, 0)      DEFAULT 1 NOT NULL,
    next_id                  NUMERIC(22, 0)    NOT NULL,
    dt_ultimo_popolamento    DATE,
    dt_ins                   DATE             NOT NULL,
    fkid_utente_ins          NUMERIC(22, 0)    NOT NULL,
    fkid_proc_ins            NUMERIC(22, 0)    NOT NULL,
    dt_mod                   TIMESTAMP,
    fkid_utente_mod          NUMERIC(22, 0),
    fkid_proc_mod            NUMERIC(22, 0),
    CONSTRAINT esinextidpk1 PRIMARY KEY (pkid)
) 
;





CREATE TABLE j2ee_user_roles(
    user_name    varchar(30)    NOT NULL,
    role_name    varchar(30)    NOT NULL,
    CONSTRAINT j2ee_user_roles_pkey PRIMARY KEY (user_name, role_name)
) 
;


CREATE TABLE j2ee_user_serv(
    fkid_servizio    NUMERIC(22, 0)    NOT NULL,
    user_name        varchar(30)     NOT NULL,
    cod_accesso      varchar(20)     NOT NULL,
    CONSTRAINT j2ee_user_serv_pkey PRIMARY KEY (fkid_servizio, user_name, cod_accesso)
) 
;


CREATE TABLE j2ee_users(
    user_name         varchar(30)    NOT NULL,
    user_pass         varchar(30),
    cognome           varchar(36),
    nome              varchar(36),
    codice_fiscale    varchar(16),
    matricola         varchar(20),
    data_nascita      DATE,
    dt_ins            DATE,
    dt_mod            TIMESTAMP,
    CONSTRAINT j2ee_user_pkey PRIMARY KEY (user_name)
) 
;


CREATE TABLE ot_indice(
    pkid                NUMERIC(22, 0)    NOT NULL,
    cod_aot             varchar(20),
    cod_ecografico      varchar(70),
    cod_catasto         varchar(20),
    cod_istat_comune    varchar(20),
    sezione             varchar(5),
    foglio              varchar(5),
    mappale             varchar(20),
    sub                 varchar(5),
    prot_anno           INTEGER,
    prot_num            varchar(20),
    id_ente             NUMERIC(7, 0),
    dt_mod              TIMESTAMP,
    CONSTRAINT ot_indice_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE ot_identificativo(
    pkid               NUMERIC(22, 0)    NOT NULL,
    fkid_ot            NUMERIC(22, 0),
    tipo_catasto       varchar(20),
    des_identificativo varchar(20),
    id_comune          varchar(20),
    sezione            varchar(5),
    foglio             varchar(5),
    mappale            varchar(20),
    sub                varchar(5),
    prot_anno          INTEGER,
    prot_num           varchar(20),
    id_ente            NUMERIC(7, 0),
    dt_mod             TIMESTAMP,
    CONSTRAINT ot_identificativo_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ot_indice02 FOREIGN KEY (fkid_ot)
    REFERENCES ot_indice(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ot_indirizzo(
    pkid         NUMERIC(22, 0)    NOT NULL,
    fkid_ot      NUMERIC(22, 0),
    fkid_area    NUMERIC(22, 0),
    des_area     varchar(80),
    num_civ      INTEGER,
    let_civ      varchar(5),
    colore       varchar(10),
    corte        varchar(9),
    scala        varchar(9),
    interno      varchar(9),
    piano        varchar(9),
    edificio     varchar(9),
    dt_ini       DATE,
    dt_fin       DATE,
    id_ente      NUMERIC(7, 0),
    dt_mod       TIMESTAMP,
    CONSTRAINT ot_indirizzo_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ot_indice03 FOREIGN KEY (fkid_ot)
    REFERENCES ot_indice(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE ot_provenienza(
    pkid               NUMERIC(22, 0)    NOT NULL,
    fkid_ot            NUMERIC(22, 0),
    fkid_provenienza   NUMERIC(22, 0),
    id_ente            NUMERIC(7, 0),
    dt_mod             TIMESTAMP,
    CONSTRAINT ot_provenienza_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ot_indice01 FOREIGN KEY (fkid_ot)
    REFERENCES ot_indice(pkid) ON DELETE CASCADE,
    CONSTRAINT ot_prov_ser_prov FOREIGN KEY (fkid_provenienza)
    REFERENCES ser_provenienza(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE pag_bolletta(
    pkid                     NUMERIC(22, 0)     NOT NULL,
    fkid_soggetto            NUMERIC(22, 0),
    cod_servizio             varchar(20),
    id_servizio              NUMERIC(22, 0),
    des_intestatario         varchar(200),
    codice_fiscale           varchar(16),
    indirizzo                varchar(200),
    recapito                 varchar(200),
    cod_bolletta             varchar(100),
    anno                     INTEGER,
    num_bolletta             varchar(20),
    num_rate                 INTEGER,
    dt_bolletta              DATE,
    oggetto                  varchar(80),
    spese_spedizione         NUMERIC(15, 2),
    tot_esente_iva           NUMERIC(15, 2),
    tot_imponibile_iva       NUMERIC(15, 2),
    tot_iva                  NUMERIC(15, 2),
    arr_prec                 NUMERIC(15, 2),
    arr_att                  NUMERIC(15, 2),
    importo_bolletta_prec    NUMERIC(15, 2),
    tot_bolletta             NUMERIC(15, 2),
    tot_pagato               NUMERIC(15, 2),
    tot_accertato            NUMERIC(15, 2),
    tot_sanzioni             NUMERIC(15, 2),
    flg_non_pagare           INTEGER,
    mot_non_pagare           varchar(100),
    flg_pagata               INTEGER,
    flg_accertamento         INTEGER,
    flg_bonifica             INTEGER,
    note                     varchar(1000),
    id_ente                  NUMERIC(7, 0),
    dt_mod                   TIMESTAMP,
    CONSTRAINT pag_bolletta_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto20 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE pag_bolletta_dett(
    pkid             NUMERIC(22, 0)    NOT NULL,
    fkid_bolletta    NUMERIC(22, 0),
    cod_voce         varchar(20),
    des_voce         varchar(200),
    importo          NUMERIC(15, 2),
    valore           varchar(200),
    id_ente          NUMERIC(7, 0),
    dt_mod           TIMESTAMP,
    CONSTRAINT pag_bolletta_dett_pkey PRIMARY KEY (pkid), 
    CONSTRAINT pag_bolletta05 FOREIGN KEY (fkid_bolletta)
    REFERENCES pag_bolletta(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE pag_bolletta_iva(
    pkid             NUMERIC(22, 0)    NOT NULL,
    fkid_bolletta    NUMERIC(22, 0),
    imponibile       NUMERIC(15, 2),
    iva              NUMERIC(6, 2),
    des_iva          varchar(80),
    imposta          NUMERIC(15, 2),
    id_ente          NUMERIC(7, 0),
    dt_mod           TIMESTAMP,
    CONSTRAINT pag_bolletta_iva_pkey PRIMARY KEY (pkid), 
    CONSTRAINT pag_bolletta03 FOREIGN KEY (fkid_bolletta)
    REFERENCES pag_bolletta(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE pag_bolletta_rata(
    pkid                NUMERIC(22, 0)    NOT NULL,
    fkid_bolletta       NUMERIC(22, 0),
    cod_servizio        varchar(20),
    num_rata            INTEGER,
    dt_scadenza_rata    DATE,
    importo_rata        NUMERIC(15, 2),
    importo_pagato      NUMERIC(15, 2),
    dt_pagamento        DATE,
    dt_reg_pagamento    DATE,
    distinta            varchar(80),
    dt_distinta         DATE,
    id_pratica          NUMERIC(22, 0),
    des_canale          varchar(80),
    des_pagamento       varchar(80),
    note                varchar(200),
    id_ente             NUMERIC(7, 0),
    dt_mod              TIMESTAMP,
    CONSTRAINT pag_bolletta_rata_pkey PRIMARY KEY (pkid), 
    CONSTRAINT pag_bolletta04 FOREIGN KEY (fkid_bolletta)
    REFERENCES pag_bolletta(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE pag_pagamento_tipo(
    pkid                  NUMERIC(22, 0)    NOT NULL,
    cod_tipo_pagamento    varchar(20),
    des_tipo_pagamento    varchar(80),
    id_ente               NUMERIC(7, 0),
    dt_mod                TIMESTAMP,
    CONSTRAINT pag_pagamento_tipo_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE pol_articolo(
    pkid                   NUMERIC(22, 0)    NOT NULL,
    articolo               CHAR(3),
    comma                  CHAR(2),
    lettera                CHAR(1),
    importomin             NUMERIC(15, 2),
    importomax             NUMERIC(15, 2),
    des_articolo           varchar(80),
    sanzioni_accessorie    varchar(80),
    id_ente                NUMERIC(7, 0),
    dt_mod                 TIMESTAMP,
    CONSTRAINT pol_articolo_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE pol_tipo_permesso(
    pkid        NUMERIC(22, 0)    NOT NULL,
    cod_tipo    varchar(20),
    des_tipo    varchar(80),
    id_ente     NUMERIC(7, 0),
    dt_mod      TIMESTAMP,
    CONSTRAINT pol_tipo_permesso_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE pol_zona_permesso(
    pkid        NUMERIC(22, 0)    NOT NULL,
    cod_zona    varchar(20),
    des_zona    varchar(80),
    id_ente     NUMERIC(7, 0),
    dt_mod      TIMESTAMP,
    CONSTRAINT pol_zona_permesso_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE pol_permesso(
    pkid                  NUMERIC(22, 0)     NOT NULL,
    fkid_soggetto         NUMERIC(22, 0),
    cod_permesso          varchar(20),
    fkid_tipo_permesso    NUMERIC(22, 0),
    id_servizio           INTEGER,
    des_tipo_permesso     varchar(80),
    fkid_zona_permesso    NUMERIC(22, 0),
    des_zona              varchar(400),
    ora_inizio            varchar(8),
    ora_fine              varchar(8),
    dt_richiesta          DATE,
    motivazione           varchar(200),
    mot_ric_ind           varchar(200),
    des_stato             varchar(80),
    note                  varchar(1024),
    id_pratica            NUMERIC(22, 0),
    id_ente               NUMERIC(7, 0),
    dt_ini                DATE,
    dt_fin                DATE,
    dt_mod                TIMESTAMP,
    CONSTRAINT pol_permesso_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto21 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT pol_tipo_perm01 FOREIGN KEY (fkid_tipo_permesso)
    REFERENCES pol_tipo_permesso(pkid) ON DELETE CASCADE,
    CONSTRAINT pol_zona_perm01 FOREIGN KEY (fkid_zona_permesso)
    REFERENCES pol_zona_permesso(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE pol_fruitori(
    pkid             NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto    NUMERIC(22, 0),
    fkid_permesso    NUMERIC(22, 0),
    des_fruitore     varchar(80),
    id_ente          NUMERIC(7, 0),
    dt_mod           TIMESTAMP,
    CONSTRAINT pol_fruitori_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto29 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT pol_permesso01 FOREIGN KEY (fkid_permesso)
    REFERENCES pol_permesso(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE pol_tipo_veicolo(
    pkid                NUMERIC(22, 0)    NOT NULL,
    cod_tipo_veicolo    varchar(20),
    des_tipo_veicolo    varchar(80),
    id_ente             NUMERIC(7, 0),
    dt_mod              TIMESTAMP,
    CONSTRAINT pol_tipo_veicolo_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE pol_veicolo(
    pkid                 NUMERIC(22, 0)    NOT NULL,
    fkid_tipo_veicolo    NUMERIC(22, 0),
    des_tipo_veicolo     varchar(80),
    marca                varchar(80),
    modello              varchar(80),
    targa                varchar(10),
    id_ente              NUMERIC(7, 0),
    dt_mod               TIMESTAMP,
    CONSTRAINT pol_veicolo_pkey PRIMARY KEY (pkid), 
    CONSTRAINT pol_tipo_veicolo01 FOREIGN KEY (fkid_tipo_veicolo)
    REFERENCES pol_tipo_veicolo(pkid) ON DELETE SET NULL
) 
;


CREATE TABLE pol_infrazione(
    pkid                 NUMERIC(22, 0)    NOT NULL,
    cod_infrazione       varchar(20),
    fkid_veicolo         NUMERIC(22, 0),
    fkid_articolo        NUMERIC(22, 0),
    dt_rilevamento       DATE,
    cod_verbale          varchar(20),
    cod_registro         varchar(20),
    stato                INTEGER,
    fkid_proprietario    NUMERIC(22, 0),
    fkid_conducente      NUMERIC(22, 0),
    fkid_comune          NUMERIC(22, 0),
    des_comune           varchar(80),
    fkid_area            NUMERIC(22, 0),
    des_area             varchar(80),
    id_ente              NUMERIC(7, 0),
    dt_mod               TIMESTAMP,
    CONSTRAINT pol_infrazione_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto17 FOREIGN KEY (fkid_proprietario)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ana_soggetto18 FOREIGN KEY (fkid_conducente)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT pol_articolo01 FOREIGN KEY (fkid_articolo)
    REFERENCES pol_articolo(pkid) ON DELETE CASCADE,
    CONSTRAINT pol_veicolo03 FOREIGN KEY (fkid_veicolo)
    REFERENCES pol_veicolo(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_area03 FOREIGN KEY (fkid_area)
    REFERENCES ter_area(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_comune07 FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE pol_vei_perm(
    pkid             NUMERIC(22, 0)    NOT NULL,
    fkid_veicolo     NUMERIC(22, 0),
    fkid_permesso    NUMERIC(22, 0),
    id_ente          NUMERIC(7, 0),
    dt_ini           DATE,
    dt_fin           DATE,
    dt_mod           TIMESTAMP,
    CONSTRAINT pol_vei_perm_pkey PRIMARY KEY (pkid), 
    CONSTRAINT pol_permesso02 FOREIGN KEY (fkid_permesso)
    REFERENCES pol_permesso(pkid) ON DELETE CASCADE,
    CONSTRAINT pol_veicolo02 FOREIGN KEY (fkid_veicolo)
    REFERENCES pol_veicolo(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE pol_vei_sogg(
    pkid             NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto    NUMERIC(22, 0),
    fkid_veicolo     NUMERIC(22, 0),
    id_ente          NUMERIC(7, 0),
    dt_ini           DATE,
    dt_fin           DATE,
    dt_mod           TIMESTAMP,
    CONSTRAINT pol_vei_sogg_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto16 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT pol_veicolo01 FOREIGN KEY (fkid_veicolo)
    REFERENCES pol_veicolo(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE pra_pratica_testa(
    pkid                     NUMERIC(22, 0)    NOT NULL,
    id_servizio              NUMERIC(22, 0)    NOT NULL,
    cod_pratica_bo           varchar(20),
    dt_pratica               DATE,
    codice_fiscale           varchar(16),
    oggetto                  varchar(200),
    tipo_notifica            varchar(20),
    cod_tipo_consegna        varchar(20),
    des_tipo_consegna        varchar(80),
    cod_modalita_allegati    varchar(20),
    des_modalita_allegati    varchar(80),
    id_canale                INTEGER,
    des_canale               varchar(80),
    doc_xml                  TEXT          NOT NULL,
    id_ente                  NUMERIC(7, 0),
    id_ente_destinatario     NUMERIC(7, 0),
    dt_mod                   TIMESTAMP,
    CONSTRAINT pra_pratica_testa_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE pra_allegato(
    pkid                    NUMERIC(22, 0)    NOT NULL,
    fkid_testa              NUMERIC(22, 0),
    cod_allegato            varchar(120),
    des_allegato            varchar(200),
    cod_tipo_allegato       varchar(20),
    des_tipo_allegato       varchar(80),
    url                     varchar(80),
    id_ente                 NUMERIC(7, 0),
    id_ente_destinatario    NUMERIC(7, 0),
    dt_mod                  TIMESTAMP,
    CONSTRAINT pra_allegato_pkey PRIMARY KEY (pkid), 
    CONSTRAINT pratica_testa02 FOREIGN KEY (fkid_testa)
    REFERENCES pra_pratica_testa(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE pra_incompleta(
    pkid                    NUMERIC(22, 0)    NOT NULL,
    id_servizio             NUMERIC(22, 0)    NOT NULL,
    dt_pratica              DATE,
    codice_fiscale          varchar(16),
    oggetto                 varchar(200),
    classe                  varchar(100),
    doc_xml                 TEXT              NOT NULL,
    id_ente                 NUMERIC(7, 0),
    id_ente_destinatario    NUMERIC(7, 0),
    dt_mod                  TIMESTAMP,
    CONSTRAINT pra_pratica_testa_pkey_1 PRIMARY KEY (pkid)
) 
;


CREATE TABLE pra_pratica_dett(
    pkid                    NUMERIC(22, 0)     NOT NULL,
    fkid_testa              NUMERIC(22, 0),
    id_responsabile         INTEGER,
    des_responsabile        varchar(80),
    id_stato_pratica        INTEGER,
    des_stato_pratica       varchar(80),
    dt_stato                DATE,
    note                    varchar(1000),
    id_ente                 NUMERIC(7, 0),
    id_ente_destinatario    NUMERIC(7, 0),
    dt_mod                  TIMESTAMP,
    CONSTRAINT pra_pratica_dett_pkey PRIMARY KEY (pkid), 
    CONSTRAINT pratica_testa03 FOREIGN KEY (fkid_testa)
    REFERENCES pra_pratica_testa(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE pra_pratica_pag(
    pkid                       NUMERIC(22, 0)    NOT NULL,
    fkid_testa                 NUMERIC(22, 0),
    dt_pagamento               DATE,
    dt_registrazione           DATE,
    dt_scadenza                DATE,
    cod_tipo_pagamento         varchar(20),
    cod_ente_tipo_pagamento    varchar(20),
    importo_pagato             NUMERIC(15, 2),
    importo_da_pagare          NUMERIC(15, 2),
    importo_totale             NUMERIC(15, 2),
    num_rata                   INTEGER,
    anno                       INTEGER,
    flg_richiesto_pagamento    INTEGER,
    numero_ordine              varchar(80),
    autorizzazione             varchar(60),
    distinta                   varchar(60),
    dt_distinta                DATE,
    id_canale                  INTEGER,
    des_canale                 varchar(80),
    causale                    varchar(80),
    des_tipo_pagamento         varchar(80),
    cod_bolletta               varchar(100),
    id_servizio                NUMERIC(22, 0),
    des_servizio               varchar(80),
    note                       varchar(1000),
    id_ente                    NUMERIC(7, 0),
    id_ente_destinatario       NUMERIC(7, 0),
    dt_mod                     TIMESTAMP,
    CONSTRAINT pra_pratica_pag_pkey PRIMARY KEY (pkid), 
    CONSTRAINT pratica_testa01 FOREIGN KEY (fkid_testa)
    REFERENCES pra_pratica_testa(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE pra_recapito(
    pkid                    NUMERIC(22, 0)    NOT NULL,
    fkid_testa              NUMERIC(22, 0),
    specie_area             varchar(20),
    des_area                varchar(80),
    num_civ                 INTEGER,
    let_civ                 varchar(5),
    scala                   varchar(9),
    interno                 varchar(9),
    cap                     varchar(10),
    presso                  varchar(50),
    telefono                varchar(30),
    fax                     varchar(30),
    email                   varchar(50),
    cod_istat_comune        INTEGER,
    des_comune              varchar(80),
    des_provincia           varchar(80),
    cod_istat_stato         INTEGER,
    des_stato               varchar(80),
    des_localita            varchar(80),
    des_contea              varchar(80),
    id_ente                 NUMERIC(7, 0),
    id_ente_destinatario    NUMERIC(7, 0),
    dt_ini                  DATE,
    dt_fin                  DATE,
    dt_mod                  TIMESTAMP,
    CONSTRAINT pra_recapito_pkey PRIMARY KEY (pkid), 
    CONSTRAINT pratica_testa04 FOREIGN KEY (fkid_testa)
    REFERENCES pra_pratica_testa(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE pra_soggetto(
    pkid                    NUMERIC(22, 0)    NOT NULL,
    fkid_testa              NUMERIC(22, 0),
    codice_fiscale          varchar(16),
    nome                    varchar(80),
    cognome                 varchar(80),
    demoninazione           varchar(100),
    des_soggetto            varchar(80),
    id_ente                 NUMERIC(7, 0),
    id_ente_destinatario    NUMERIC(7, 0),
    dt_mod                  TIMESTAMP,
    CONSTRAINT pra_soggetto_pkey PRIMARY KEY (pkid), 
    CONSTRAINT pratica_testa05 FOREIGN KEY (fkid_testa)
    REFERENCES pra_pratica_testa(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE pra_stato(
    stato        INTEGER    NOT NULL,
    des_stato    varchar(80),
    CONSTRAINT pra_stato_pkey PRIMARY KEY (stato)
) 
;


CREATE TABLE pra_tipo_allegato(
    pkid                    NUMERIC(22, 0)    NOT NULL,
    cod_tipo                varchar(20),
    des_tipo                varchar(80),
    id_ente                 NUMERIC(7, 0),
    id_ente_destinatario    NUMERIC(7, 0),
    dt_mod                  TIMESTAMP,
    CONSTRAINT pra_tipo_allegato_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE rsu_insediamento(
    pkid                 INTEGER       NOT NULL,
    cod_insediamento     varchar(20),
    fkid_soggetto        NUMERIC(22, 0)       NOT NULL,
    flg_stato            INTEGER,
    superficie           FLOAT,
    fkid_area            NUMERIC(22, 0),
    des_area             varchar(80),
    num_civ              INTEGER,
    let_civ              varchar(5),
    colore               varchar(9),
    corte                varchar(9),
    scala                varchar(9),
    interno              varchar(9),
    piano                varchar(9),
    edificio             varchar(9),
    cod_ecografico       varchar(70),
    dt_denuncia          DATE,
    num_ricevuta         varchar(10),
    dt_ricevuta          DATE,
    des_tariffa          varchar(80),
    des_tariffa1         varchar(80),
    num_accertamento     INTEGER,
    dt_accertamento      DATE,
    des_tipo_denuncia    varchar(80),
    note                 varchar(1000),
    id_ente              NUMERIC(7, 0),
    dt_ini               DATE,
    dt_fin               DATE,
    dt_mod               TIMESTAMP,
    CONSTRAINT rsu_insediamento_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto34 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_area01 FOREIGN KEY (fkid_area)
    REFERENCES ter_area(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE rsu_ins_agevolazione(
    pkid                    INTEGER    NOT NULL,
    cod_ins_agevolazione    varchar(20),
    des_agevolazione        varchar(80),
    fkid_insediamento       INTEGER,
    quantita                INTEGER,
    id_ente                 NUMERIC(7, 0),
    dt_mod                  TIMESTAMP,
    CONSTRAINT rsu_ins_agevolazione_pkey PRIMARY KEY (pkid), 
    CONSTRAINT rsu_insediamento01 FOREIGN KEY (fkid_insediamento)
    REFERENCES rsu_insediamento(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE rsu_ins_calcolo(
    pkid                 INTEGER    NOT NULL,
    fkid_insediamento    INTEGER,
    anno                 INTEGER,
    des_ruolo            varchar(80),
    imponibile           NUMERIC(15, 2),
    imposta              NUMERIC(15, 2),
    riduzione            NUMERIC(15, 2),
    importo_pf           NUMERIC(15, 2),
    importo_pv           NUMERIC(15, 2),
    tariffa_pf           NUMERIC(15, 2),
    tariffa_pv           NUMERIC(15, 2),
    cod_tributo          varchar(4),
    id_ente              NUMERIC(7, 0),
    dt_mod               TIMESTAMP,
    fkid_bolletta        NUMERIC(22, 0),
    CONSTRAINT rsu_ins_calcolo_pkey PRIMARY KEY (pkid), 
    CONSTRAINT pag_bolletta01 FOREIGN KEY (fkid_bolletta)
    REFERENCES pag_bolletta(pkid) ON DELETE CASCADE,
    CONSTRAINT rsu_insediamento03 FOREIGN KEY (fkid_insediamento)
    REFERENCES rsu_insediamento(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE rsu_ins_componente(
    pkid                 NUMERIC(22, 0)    NOT NULL,
    anno                 INTEGER,
    dt_rilevazione       DATE,
    flg_no_residente     INTEGER,
    tot_persone_reali    INTEGER,
    tot_persone_calc     INTEGER,
    fkid_insediamento    INTEGER,
    id_ente              NUMERIC(7, 0),
    dt_ini               DATE,
    dt_fin               DATE,
    dt_mod               TIMESTAMP,
    CONSTRAINT rsu_ins_componente_pkey PRIMARY KEY (pkid), 
    CONSTRAINT rsu_insediamento02 FOREIGN KEY (fkid_insediamento)
    REFERENCES rsu_insediamento(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE rsu_pag_addizionale(
    pkid                   NUMERIC(22, 0)    NOT NULL,
    cod_tributo            varchar(4),
    des_addizionale        varchar(80),
    importo_addizionale    NUMERIC(15, 2),
    id_ente                NUMERIC(7, 0),
    dt_mod                 TIMESTAMP,
    fkid_bolletta          NUMERIC(22, 0),
    CONSTRAINT rsu_pag_addizionale_pkey PRIMARY KEY (pkid), 
    CONSTRAINT pag_bolletta02 FOREIGN KEY (fkid_bolletta)
    REFERENCES pag_bolletta(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE scu_istituto(
    pkid             NUMERIC(22, 0)     NOT NULL,
    cod_istituto     varchar(20),
    des_istituto     varchar(80),
    cod_tipo         varchar(20),
    des_tipo         varchar(80),
    fkid_area        NUMERIC(22, 0),
    des_area         varchar(80),
    num_civ          INTEGER,
    let_civ          varchar(5),
    fkid_comune      NUMERIC(22, 0),
    des_comune       varchar(80),
    des_provincia    varchar(80),
    num_posti        INTEGER,
    telefono         varchar(20),
    fax              varchar(20),
    email            varchar(80),
    note             varchar(1000),
    dt_ini           DATE,
    dt_fin           DATE,
    id_ente          NUMERIC(7, 0),
    dt_mod           TIMESTAMP,
    CONSTRAINT scu_istituto_pkid PRIMARY KEY (pkid), 
    CONSTRAINT ter_area12 FOREIGN KEY (fkid_area)
    REFERENCES ter_area(pkid),
    CONSTRAINT ter_comune02 FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid)
) 
;

CREATE TABLE scu_corso(
    pkid             NUMERIC(22, 0)     NOT NULL,
    fkid_istituto    NUMERIC(22, 0),
    cod_corso        varchar(20),
    des_corso        varchar(80),
    num_posti        INTEGER,
    note             varchar(1000),
    dt_ini           DATE,
    dt_fin           DATE,
    id_ente          NUMERIC(7, 0),
    dt_mod           TIMESTAMP,
    CONSTRAINT scu_corso_pkey PRIMARY KEY (pkid), 
    CONSTRAINT Refscu_istituto430 FOREIGN KEY (fkid_istituto)
    REFERENCES scu_istituto(pkid)
) 
;

CREATE TABLE scu_classe(
    pkid               NUMERIC(22, 0)     NOT NULL,
    fkid_corso         NUMERIC(22, 0),
    cod_classe         varchar(20),
    des_classe         varchar(80),
    sezione            varchar(10),
    tempo              varchar(20),
    fkid_area          NUMERIC(22, 0),
    des_area           varchar(80),
    num_civ            INTEGER,
    let_civ            varchar(5),
    fkid_comune        NUMERIC(22, 0),
    des_comune         varchar(80),
    des_provincia      varchar(80),
    note               varchar(1000),
    dt_ini             DATE,
    dt_fin             DATE,
    id_ente            NUMERIC(7, 0),
    dt_mod             TIMESTAMP,
    CONSTRAINT scu_classe_pkey PRIMARY KEY (pkid), 
    CONSTRAINT Refscu_corso431 FOREIGN KEY (fkid_corso)
    REFERENCES scu_corso(pkid),
    CONSTRAINT ter_area09 FOREIGN KEY (fkid_area)
    REFERENCES ter_area(pkid),
    CONSTRAINT ter_comune15 FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid)
) 
;


CREATE TABLE scu_soggetto(
    pkid                NUMERIC(22, 0)     NOT NULL,
    fkid_soggetto       NUMERIC(22, 0),
    fkid_classe         NUMERIC(22, 0),
    anno_scolastico    varchar(10),
    note                varchar(1000),
    des_pediatra        varchar(80),
    des_vaccinazioni    varchar(1000),
    cod_fascia          varchar(20),
    des_fascia          varchar(80),
    dt_ini              DATE,
    dt_fin              DATE,
    id_ente             NUMERIC(7, 0),
    dt_mod              TIMESTAMP,
    CONSTRAINT scu_soggetto_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto40 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT scu_classe01 FOREIGN KEY (fkid_classe)
    REFERENCES scu_classe(pkid) ON DELETE CASCADE
) 
;

CREATE TABLE sdi_centro
(
    pkid             NUMERIC(22)     NOT NULL,
    cod_centro       varchar(20),
    des_centro       varchar(80),
    cod_tipo         varchar(20),
    des_tipo         varchar(80),
    tipo_centro      varchar(3),
    fkid_area        NUMERIC(22),
    des_area         varchar(80),
    num_civ          INTEGER,
    let_civ          varchar(5),
    fkid_comune      NUMERIC(22),
    des_comune       varchar(80),
    des_provincia    varchar(80),
    num_posti        INTEGER,
    telefono         varchar(20),
    fax              varchar(20),
    email            varchar(80),
    note             varchar(1000),
    dt_ini           DATE,
    dt_fin           DATE,
    id_ente          NUMERIC(7),
    dt_mod           TIMESTAMP,
    CONSTRAINT sdi_centro_pkid PRIMARY KEY (pkid), 
    CONSTRAINT Refter_area428 FOREIGN KEY (fkid_area)
    REFERENCES ter_area(pkid),
    CONSTRAINT Refter_comune429 FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid)
) ;


CREATE TABLE sdi_graduatoria(
    pkid               NUMERIC(22, 0)    NOT NULL,
    cod_graduatoria    varchar(20),
    des_graduatoria    varchar(80),
    dt_graduatoria     DATE,
    cod_stato          varchar(20),
    des_stato          varchar(80),
    dt_stato           DATE,
    cod_centro         varchar(20),
    des_centro         varchar(80),
    cod_corso          varchar(20),
    des_corso          varchar(80),
    cod_fascia         varchar(20),
    des_fascia         varchar(80),
    dt_ini             DATE,
    dt_fin             DATE,
    id_ente            NUMERIC(7, 0),
    dt_mod             TIMESTAMP,
    CONSTRAINT sdi_graduatoria_pkid PRIMARY KEY (pkid)
) 
;


CREATE TABLE sdi_pos_graduatoria(
    pkid                    NUMERIC(22, 0)    NOT NULL,
    fkid_soggetto           NUMERIC(22, 0),
    fkid_graduatoria        NUMERIC(22, 0),
    priorita                INTEGER,
    stato_in_graduatoria    varchar(80),
    posizione               INTEGER,
    punteggio               NUMERIC(10, 4),
    dt_ini                  DATE,
    dt_fin                  DATE,
    id_ente                 NUMERIC(7, 0),
    dt_mod                  TIMESTAMP,
    CONSTRAINT sdi_pos_graduatoria_pkid PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto42 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT sdi_pgr_sdi_gra FOREIGN KEY (fkid_graduatoria)
    REFERENCES sdi_graduatoria(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE sdi_servizio(
    pkid                   NUMERIC(22, 0)     NOT NULL,
    fkid_soggetto          NUMERIC(22, 0),
    fkid_rappresentante    NUMERIC(22, 0),
    id_servizio            NUMERIC(22, 0)     NOT NULL,
    id_pratica             NUMERIC(22, 0),
    cod_richiesta_bo       varchar(20),
    cod_bolletta		   varchar(100),
    anno_servizio          varchar(20),
    cod_servizio           varchar(20),
    des_servizio           varchar(80),
    cod_tariffa            varchar(20),
    des_tariffa            varchar(80),
    importo_tot            varchar(10),
    cod_esenzione          varchar(20),
    des_esenzione          varchar(80),
    cod_stato              INTEGER,
    des_stato              varchar(80),
    dt_pratica             DATE,
    dt_rilascio            DATE,
    dt_ini_servizio        DATE,
    dt_fin_servizio        DATE,
    note                   varchar(1000),
    id_ente                NUMERIC(7, 0),
    dt_mod                 TIMESTAMP,
    CONSTRAINT sdi_servizio_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_rapp01 FOREIGN KEY (fkid_rappresentante)
    REFERENCES ana_rappresentante(pkid) ON DELETE CASCADE,
    CONSTRAINT ana_soggetto41 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE sdi_servizio_dett(
    pkid             NUMERIC(22, 0)    NOT NULL,
    fkid_servizio    NUMERIC(22, 0),
    cod_voce         varchar(20),
    des_voce         varchar(200),
    importo          NUMERIC(15, 2),
    valore           varchar(200),
    id_ente          NUMERIC(7, 0),
    dt_mod           TIMESTAMP,
    CONSTRAINT sdi_servizio_dett_pkey PRIMARY KEY (pkid), 
    CONSTRAINT sdi_servizio01 FOREIGN KEY (fkid_servizio)
    REFERENCES sdi_servizio(pkid) ON DELETE CASCADE
) 
;

--------------------------------------------------

CREATE TABLE ser_gruppo
(
	nome       varchar(30) NOT NULL,
	des_gruppo varchar(200),
	dt_mod     TIMESTAMP,
	CONSTRAINT ser_gruppo_pkey PRIMARY KEY (nome)
);

CREATE TABLE ser_gruppo_utente
(
  gruppo varchar(30) NOT NULL,
  utente varchar(30) NOT NULL,
  CONSTRAINT ser_gruppo_utente_pkey PRIMARY KEY (gruppo, utente),
  CONSTRAINT gruppo_utente_fk1 FOREIGN KEY (gruppo)
  REFERENCES ser_gruppo(nome) ON DELETE CASCADE,
  CONSTRAINT gruppo_utente_fk2 FOREIGN KEY (utente)
  REFERENCES j2ee_users(user_name) ON DELETE CASCADE
);

CREATE TABLE ser_gruppo_servizio
(
  gruppo      varchar(30) NOT NULL,
  id_servizio NUMERIC(22) NOT NULL,
  CONSTRAINT ser_gruppo_servizio_pkey PRIMARY KEY (gruppo, id_servizio),
  CONSTRAINT gruppo_servizio_fk1 FOREIGN KEY (gruppo)
  REFERENCES ser_gruppo(nome) ON DELETE CASCADE
);

create table maad_report( 
  pkid       NUMERIC(22,0)  not null,
  id_ente    NUMERIC(7,0),
  host       varchar(80),
  dt_report  DATE,
  CONSTRAINT pkid_maad_report  PRIMARY KEY (pkid) 
); 


create table maad_event( 
  pkid        NUMERIC(22,0)   not null,
  fkid_report NUMERIC(22,0),
  id_evento   NUMERIC(7,0),
  des_evento  varchar(4000),
  id_esito    NUMERIC(22,0),
  des_esito   varchar(80),
  dt_event    TIMESTAMP,
  constraint pkid_maad_event  primary key (pkid) ,
  constraint fk_maad_report  foreign key (fkid_report) references maad_report(pkid) on delete cascade
);

CREATE TABLE ser_tipo_immagine
(
  pkid NUMERIC(22) NOT NULL,
  estensione_file varchar(20),
  tipo_immagine varchar(20),
  id_ente NUMERIC(7),
  dt_mod TIMESTAMP,  
  CONSTRAINT tipo_imm_pk PRIMARY KEY (pkid)
);

CREATE TABLE ser_coordinator(
    pkid               NUMERIC(22, 0)    NOT NULL,
    nome_tabella       varchar(80),
    ordine             INTEGER,
    fl_bidir           INTEGER,
    fl_allinea         INTEGER,
    fl_cancellabile    INTEGER,
    dt_mod             TIMESTAMP,
    id_ente            NUMERIC(7, 0),
    CONSTRAINT ser_coordinator_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE ser_esicra_info(
    nomedb            varchar(80)     NOT NULL,
    versione          varchar(20),
    dt_creazione      DATE,
    db_engine         varchar(20),
    servlet_engine    varchar(20),
    host              varchar(30),
    porta             INTEGER,
    id_ente           NUMERIC(7, 0),
    des_ente          varchar(80),
    cod_istat         NUMERIC(22, 0),
    flg_storico       INTEGER
) 
;


CREATE TABLE ser_export(
    pkid                    NUMERIC(22, 0)    NOT NULL,
    id_servizio             NUMERIC(22, 0),
    nome_file               varchar(80),
    esportati               INTEGER,
    tempo                   varchar(20),
    id_ente                 NUMERIC(7, 0),
    id_ente_destinatario    NUMERIC(7, 0),
    dt_mod                  TIMESTAMP,
    CONSTRAINT ser_export_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE ser_import(
    pkid                    NUMERIC(22, 0)    NOT NULL,
    nome_file               varchar(80),
    in_elaborazione         INTEGER,
    tempo                   varchar(20),
    elaborati               INTEGER,
    non_validi              INTEGER,
    ignorati                INTEGER,
    rigettati               INTEGER,
    id_ente                 NUMERIC(7, 0),
    id_ente_destinatario    NUMERIC(7, 0),
    dt_mod                  TIMESTAMP,
    CONSTRAINT ser_import_pkey PRIMARY KEY (pkid)
) 
;


CREATE TABLE ser_istruttoria(
    pkid              NUMERIC(22, 0)     NOT NULL,
    id_servizio       NUMERIC(22, 0)     NOT NULL,
    des_servizio      varchar(80),
    cod_iter          varchar(20),
    des_iter_breve    varchar(40),
    des_iter_lunga    varchar(400),
    num_progr         INTEGER,
    id_ente           NUMERIC(7, 0),
    note              varchar(1000),
    dt_mod            TIMESTAMP,
    CONSTRAINT PK135 PRIMARY KEY (pkid)
) 
;

CREATE TABLE ser_servizio(
    pkid                    NUMERIC(22, 0)    NOT NULL,
    id_servizio             NUMERIC(22, 0),
    descrizione             varchar(80),
    raggruppamento          varchar(20),
    des_breve               varchar(20),
    tipo_export             INTEGER,
    sigla_export            varchar(20),
    id_ente                 NUMERIC(7, 0),
    id_ente_destinatario    NUMERIC(7, 0),
    dt_mod                  TIMESTAMP,
    CONSTRAINT ser_servizio_pkey PRIMARY KEY (pkid)
) 
;

CREATE TABLE ser_transazione(
    cod_transazione         NUMERIC(20, 0)    NOT NULL,
    id_ente_destinatario    NUMERIC(7, 0),
    stato                   INTEGER,
    durata                  NUMERIC(20, 0),
    dt_ini                  DATE,
    dt_mod                  TIMESTAMP,
    nota                    varchar(80),
    CONSTRAINT ser_transazione_pkey PRIMARY KEY (cod_transazione)
) 
;


CREATE TABLE tri_concessione(
    pkid               NUMERIC(22, 0)     NOT NULL,
    cod_concessione    varchar(20),
    des_concessione    varchar(80),
    num_concessione    varchar(20),
    dt_concessione     DATE,
    cod_tipo           varchar(20),
    des_tipo           varchar(80),
    fkid_soggetto      NUMERIC(22, 0)     NOT NULL,
    des_categoria      varchar(80),
    quantita           NUMERIC(15, 3),
    lunghezza          varchar(20),
    larghezza          varchar(20),
    des_quantita       varchar(80),
    fkid_area          NUMERIC(22, 0),
    des_area           varchar(80),
    num_civ            INTEGER,
    let_civ            varchar(10),
    cod_ecografico     varchar(70),
    fkid_comune        NUMERIC(22, 0),
    des_comune         varchar(80),
    des_provincia      varchar(80),
    flg_esonero        INTEGER,
    note               varchar(1000),
    id_ente            NUMERIC(7, 0),
    dt_mod             TIMESTAMP,
    CONSTRAINT tri_concessione_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto31 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT ter_area08 FOREIGN KEY (fkid_area)
    REFERENCES ter_area(pkid) ON DELETE SET NULL,
    CONSTRAINT ter_comune17 FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE tri_con_denuncia(
    pkid                NUMERIC(22, 0)    NOT NULL,
    fkid_concessione    NUMERIC(22, 0),
    fkid_soggetto       NUMERIC(22, 0),
    id_servizio         NUMERIC(22, 0),
    cognome             varchar(36),
    nome                varchar(36),
    codice_fiscale      varchar(16),
    dt_nascita          DATE,
    loc_nascita         varchar(200),
    recapito            varchar(200),
    dt_denuncia         DATE,
    dt_ini_con          DATE,
    dt_fine_con         DATE,
    cod_denuncia        varchar(20),
    id_pratica          NUMERIC(22, 0),
    cod_tipo            varchar(20),
    des_tipo            varchar(80),
    id_ente             NUMERIC(7, 0),
    dt_mod              TIMESTAMP,
    CONSTRAINT tri_con_denuncia_pkey PRIMARY KEY (pkid), 
    CONSTRAINT ana_soggetto28 FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE,
    CONSTRAINT tri_concessione01 FOREIGN KEY (fkid_concessione)
    REFERENCES tri_concessione(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE tri_con_dett(
    pkid                NUMERIC(22, 0)    NOT NULL,
    fkid_concessione    NUMERIC(22, 0),
    cod_voce            varchar(20),
    des_voce            varchar(80),
    valore              varchar(200),
    importo             NUMERIC(15, 2),
    id_ente             NUMERIC(7, 0),
    dt_mod              TIMESTAMP,
    CONSTRAINT tri_con_dett_pkey PRIMARY KEY (pkid), 
    CONSTRAINT tri_concessione02 FOREIGN KEY (fkid_concessione)
    REFERENCES tri_concessione(pkid) ON DELETE CASCADE
) 
;


CREATE TABLE tri_con_durata(
    pkid                NUMERIC(22, 0)    NOT NULL,
    fkid_concessione    NUMERIC(22, 0),
    giorno              varchar(20),
    ora_ini             varchar(20),
    ora_fin             varchar(80),
    id_ente             NUMERIC(7, 0),
    dt_mod              TIMESTAMP,
    CONSTRAINT tri_con_durata_pkey PRIMARY KEY (pkid), 
    CONSTRAINT tri_concessione03 FOREIGN KEY (fkid_concessione)
    REFERENCES tri_concessione(pkid) ON DELETE CASCADE
) 
;

CREATE TABLE statofornitore
(
  pkstatofornitore NUMERIC(22) NOT NULL,
  cod_stato varchar(10),
  des_stato varchar(40),
  id_ente NUMERIC(22) NOT NULL,
  CONSTRAINT pk_statofornitore PRIMARY KEY (pkstatofornitore)
);

CREATE TABLE statoiter
(
  pkstatoiter NUMERIC(22) NOT NULL,
  cod_stato varchar(10),
  des_stato varchar(40),
  id_ente NUMERIC(22) NOT NULL,
  CONSTRAINT pk_statoiter PRIMARY KEY (pkstatoiter)
) ;

CREATE TABLE albo
(
  pkalbo NUMERIC(22) NOT NULL,
  des_albo varchar(80),
  flg_chiuso INTEGER,
  note varchar(1000),
  id_ente NUMERIC(22) NOT NULL,
  dt_mod timestamp,
  CONSTRAINT pk_albo PRIMARY KEY (pkalbo)
);

CREATE TABLE alboiter
(
  pkalboiter NUMERIC(22) NOT NULL,
  fkalbo NUMERIC(22) NOT NULL,
  datainiziovalidita timestamp NOT NULL,
  datafinevalidita timestamp NOT NULL,
  dt_mod timestamp,
  CONSTRAINT pk_pkalboiter PRIMARY KEY (pkalboiter),
  CONSTRAINT refalbo03 FOREIGN KEY (fkalbo) REFERENCES albo (pkalbo) ON DELETE CASCADE
);

CREATE TABLE fornitori
(
  pkfornitore NUMERIC(22) NOT NULL,
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
  id_ente NUMERIC(22) NOT NULL,
  dt_mod timestamp,
  nome varchar(100),
  cognome varchar(100),
  CONSTRAINT pk_fornitore PRIMARY KEY (pkfornitore)
) ;

CREATE TABLE categorie
(
  pkcategoria NUMERIC(22) NOT NULL,
  descategoria varchar(200),
  fkalbo NUMERIC(22),
  flg_eco INTEGER NOT NULL,
  id_ente NUMERIC(22) NOT NULL,
  CONSTRAINT pk_categorie PRIMARY KEY (pkcategoria),
  CONSTRAINT refalbo04 FOREIGN KEY (fkalbo) REFERENCES albo (pkalbo) ON DELETE CASCADE
) ;

CREATE TABLE richiesteaccreditamento
(
  pkrichiesteaccreditamento NUMERIC(22) NOT NULL,
  fkalbo NUMERIC(22),
  fkfornitore NUMERIC(22),
  datapresentazione date,
  codfiscalerichiedente varchar(20),
  denominazione varchar(100),
  partiva varchar(20),
  note varchar(1000),
  id_ente_richiedente NUMERIC(22),
  id_ente_destinatario NUMERIC(22) NOT NULL,
  dt_mod timestamp,
  CONSTRAINT pk_richieste PRIMARY KEY (pkrichiesteaccreditamento),
  CONSTRAINT refalbo01 FOREIGN KEY (fkalbo) REFERENCES albo (pkalbo) ON DELETE CASCADE,
  CONSTRAINT reffornitore01 FOREIGN KEY (fkfornitore) REFERENCES fornitori (pkfornitore) ON DELETE CASCADE
);

/* tab centrale */
CREATE TABLE albo_for_cat_stato
(
  pkalbofornitori NUMERIC(22) NOT NULL,
  fkalbo NUMERIC(22),
  fkfornitore NUMERIC(22),
  fkcategorie NUMERIC(22),
  fkstato NUMERIC(22),
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
  pkforniture NUMERIC(22) NOT NULL,
  fkcategoria NUMERIC(22) NOT NULL,
  fkrichiesta NUMERIC(22) NOT NULL,
  desfornitura varchar(200),
  importo NUMERIC(10,2),
  anno INTEGER,
  committente varchar(200),
  CONSTRAINT pk_forniture PRIMARY KEY (pkforniture),
  CONSTRAINT refcategoria01 FOREIGN KEY (fkcategoria) REFERENCES categorie (pkcategoria) ON DELETE CASCADE,
  CONSTRAINT refrichiesta02 FOREIGN KEY (fkrichiesta) REFERENCES richiesteaccreditamento (pkrichiesteaccreditamento) ON DELETE CASCADE
);

CREATE TABLE richiesteaccreditamentoiter
(
  pkiter NUMERIC(22) NOT NULL,
  fkrichiesta NUMERIC(22) NOT NULL,
  fkstatoiter NUMERIC(22),
  responsabile varchar(100),
  notestato varchar(1000),
  dataultimostato timestamp NOT NULL,
  id_ente NUMERIC(22) NOT NULL,
  dt_mod timestamp,
  CONSTRAINT pk_iter PRIMARY KEY (pkiter),
  CONSTRAINT refrichiesta01 FOREIGN KEY (fkrichiesta) REFERENCES richiesteaccreditamento (pkrichiesteaccreditamento) ON DELETE CASCADE,
  CONSTRAINT refstatoiter01 FOREIGN KEY (fkstatoiter) REFERENCES statoiter (pkstatoiter) ON DELETE CASCADE
) ;

CREATE TABLE categorieric
(
  pkcategorieric NUMERIC(22) NOT NULL,
  fkrichiesta NUMERIC(22) NOT NULL,
  fkcategorie NUMERIC(22) NOT NULL,
  CONSTRAINT pk_categorieric PRIMARY KEY (pkcategorieric),
  CONSTRAINT refcategorie05 FOREIGN KEY (fkcategorie) REFERENCES categorie (pkcategoria) ON DELETE CASCADE,
  CONSTRAINT refrichiesta05 FOREIGN KEY (fkrichiesta) REFERENCES richiesteaccreditamento (pkrichiesteaccreditamento) ON DELETE CASCADE
);

CREATE TABLE entealbo
(
  id_ente NUMERIC(7) NOT NULL,
  des_ente varchar(120),
  cod_istat NUMERIC(7),
  CONSTRAINT pk_ente PRIMARY KEY (id_ente)
);

CREATE TABLE pro_tipo
(
  pkid NUMERIC(22) NOT NULL,
  descrizione varchar(50),
  CONSTRAINT pro_tipo_pkey PRIMARY KEY (pkid)
);


CREATE TABLE pro_valore
(
  pkid NUMERIC(22) NOT NULL,
  tipo NUMERIC(22),
  descrizione varchar(50),
  ordinale INTEGER,
  id_ente NUMERIC(7),
  dt_mod timestamp,
  CONSTRAINT pro_valore_pkey PRIMARY KEY (pkid),
  CONSTRAINT pro_tipo_fkid FOREIGN KEY (tipo) REFERENCES pro_tipo (pkid) ON DELETE CASCADE
);


CREATE OR REPLACE VIEW esiidente AS
SELECT ES.incremento AS id_ente
FROM esinextid ES
WHERE pkid = 1
;

CREATE or replace VIEW v_giuridico_indirizzo_corrente AS
SELECT ana.pkid, ind.fkid_soggetto, ind.tipo, ind.fkid_area, ind.des_area, ind.num_civ, ind.let_civ, ind.colore, ind.corte, ind.scala, ind.interno, ind.piano, ind.edificio, ind.presso, ind.telefono, ind.fax, ind.email, ind.fkid_stato, ind.des_stato, ind.contea, ind.fkid_localita, ind.des_localita, ind.cap, ind.fkid_comune, ind.des_comune, ind.des_provincia, ind.fkid_consolato, ind.des_consolato, ind.id_ente, ind.dt_ini AS anaidenza_ini, ind.dt_fin AS anaidenza_fin, ana.provenienza, ana.cod_soggetto, ana.codice_fiscale, ana.flg_cf_calcolato, ana.natura, ana.tipo_natura, ana.partita_iva, ana.cognome, ana.nome, ana.altri_nomi, ana.denominazione, ana.dt_nascita, ana.precisione_dt_nascita, ana.sesso, ana.note, ana.fkid_comune_nascita, ana.des_comune_nascita, ana.des_provincia_nascita, ana.fkid_stato_nascita, ana.des_stato_nascita, ana.fkid_localita_nascita, ana.des_localita_nascita, ana.flg_stato, ana.dt_ini, ana.dt_fin, ind.cod_indirizzo
FROM ana_soggetto ana, ana_indirizzo ind
WHERE ana.partita_iva IS NOT NULL AND ana.dt_fin IS NULL AND ana.dt_mod IS NOT NULL AND ind.dt_fin IS NULL AND ind.dt_mod IS NOT NULL AND ind.tipo <> 'RES' AND ana.pkid = ind.fkid_soggetto AND ana.dt_mod =	(( SELECT max(ana1.dt_mod) AS max	   FROM ana_soggetto ana1	   WHERE ana.cod_soggetto = ana1.cod_soggetto))	         AND ind.dt_mod = (( SELECT max(ind1.dt_mod) AS max	                             FROM ana_indirizzo ind1	                             WHERE ind.fkid_soggetto = ind1.fkid_soggetto))
;

CREATE or replace VIEW v_indirizzo_corrente AS
SELECT ana.pkid, ana.fkid_soggetto, ana.tipo, ana.fkid_area, ana.des_area, ana.num_civ, ana.let_civ, ana.colore, ana.corte, ana.scala, ana.interno, ana.piano, ana.edificio, ana.presso, ana.telefono, ana.fax, ana.email, ana.fkid_stato, ana.des_stato, ana.contea, ana.fkid_localita, ana.des_localita, ana.cap, ana.fkid_comune, ana.des_comune, ana.des_provincia, ana.fkid_consolato, ana.des_consolato, ana.id_ente, ana.dt_ini, ana.dt_fin, ana.dt_mod, ana.cod_indirizzo
FROM ana_indirizzo ana
WHERE ana.tipo = 'RES' and ana.dt_fin is null and ana.dt_mod is not null
;

CREATE or replace VIEW v_movimentazioni AS
SELECT can.fkid_soggetto, can.des_motivo AS motivo, can.dt_dec, can.des_comune AS comune, can.des_stato AS stato, can.des_localita AS localita, 1 AS tipo
FROM ana_cancellazione can
WHERE can.dt_mod IS NOT NULL AND can.dt_fin IS NULL  UNION  SELECT isc.fkid_soggetto, isc.des_motivo AS motivo, isc.dt_dec, isc.des_comune AS comune, isc.des_stato AS stato, isc.des_localita AS localita, 2 AS tipo   FROM ana_iscrizione isc  WHERE isc.dt_mod IS NOT NULL AND isc.dt_fin IS NULL
;


CREATE or replace VIEW v_pratica_completa AS
	SELECT p_tes.pkid,
	       p_tes.id_servizio,
	       p_tes.cod_pratica_bo,
	       p_tes.oggetto,
	       p_tes.dt_pratica,
	       p_tes.codice_fiscale,
	       p_tes.tipo_notifica,
	       p_tes.cod_tipo_consegna,
	       p_tes.des_tipo_consegna,
	       p_tes.cod_modalita_allegati,
	       p_tes.des_modalita_allegati,
	       p_tes.id_canale,
	       p_tes.des_canale,
	       p_tes.doc_xml,
	       p_tes.id_ente,
	       p_det.pkid AS pkid_dett,
	       p_det.fkid_testa,
	       p_det.dt_stato,
	       p_det.id_responsabile,
	       p_det.des_responsabile,
	       p_det.note,
	       p_det.id_stato_pratica,
	       p_tes.id_ente_destinatario,
	       p_det.des_stato_pratica,
	       p_det.dt_mod,
	       serv.des_breve,
	       serv.raggruppamento,
	       serv.tipo_export,
	       serv.sigla_export
	FROM pra_pratica_testa p_tes, pra_pratica_dett p_det, ser_servizio serv
	WHERE p_det.fkid_testa = p_tes.pkid
	      AND
	      p_tes.id_servizio = serv.id_servizio
	      AND
	      serv.tipo_export = 1
	      AND
	      p_det.dt_mod = ( SELECT MAX(pra_pratica_dett.dt_mod) AS max 
	                       FROM pra_pratica_dett 
	                       WHERE pra_pratica_dett.fkid_testa=p_det.fkid_testa
	                     );

CREATE or replace VIEW v_ser_servizio AS 
SELECT DISTINCT ser.des_breve, ser.pkid, ser.descrizione, ser.raggruppamento, ser.tipo_export, ser.id_servizio, ser.sigla_export
FROM ser_servizio ser, pra_pratica_testa test
WHERE ser.id_servizio = test.id_servizio AND ser.tipo_export = 2
;


CREATE or replace VIEW v_ana_soggetto_corrente_res AS 
SELECT  ana.pkid, ana.provenienza, ana.cod_soggetto, ana.codice_fiscale, ana.flg_cf_calcolato, 
		ana.natura, ana.tipo_natura, ana.partita_iva, ana.cognome, ana.nome, ana.altri_nomi, 
		ana.denominazione, ana.dt_nascita, ana.precisione_dt_nascita, ana.sesso, ana.note, 
		ana.fkid_comune_nascita, ana.des_comune_nascita, ana.des_provincia_nascita, 
		ana.fkid_stato_nascita, ana.des_stato_nascita, ana.fkid_localita_nascita, 
		ana.des_localita_nascita, ana.flg_stato, ana.fkid_rappresentante, ana.id_ente, 
		ana.dt_ini, ana.dt_fin, ana.dt_mod
FROM ana_soggetto ana
WHERE ana.provenienza = 0 AND ana.dt_fin IS NULL AND ana.dt_mod IS NOT NULL 
		AND NOT (EXISTS (  SELECT ana.pkid
        				   FROM v_movimentazioni mov
				           WHERE mov.tipo = 1 
				           		AND ana.pkid = mov.fkid_soggetto 
				           		AND ((mov.dt_dec IN ( SELECT max(mov1.dt_dec) AS max
                   									  FROM v_movimentazioni mov1
                  									  WHERE mov.fkid_soggetto = mov1.fkid_soggetto)
                  					  ) OR mov.dt_dec IS NULL)
                  		)
                 );


CREATE or replace VIEW v_soggetto_indirizzo_corrente AS
SELECT ind.pkid, ind.fkid_soggetto, ind.tipo, ind.fkid_area, ind.des_area, ind.num_civ, ind.let_civ, ind.colore, ind.corte, ind.scala, ind.interno, ind.piano, ind.edificio, ind.presso, ind.telefono, ind.fax, ind.email, ind.fkid_stato, ind.des_stato, ind.contea, ind.fkid_localita, ind.des_localita, ind.cap, ind.fkid_comune, ind.des_comune, ind.des_provincia, ind.fkid_consolato, ind.des_consolato, ind.id_ente, ind.dt_ini AS residenza_ini, ind.dt_fin AS residenza_fin, res.provenienza, res.cod_soggetto, res.codice_fiscale, res.flg_cf_calcolato, res.natura, res.tipo_natura, res.partita_iva, res.cognome, res.nome, res.altri_nomi, res.denominazione, res.dt_nascita, res.precisione_dt_nascita, res.sesso, res.note, res.fkid_comune_nascita, res.des_comune_nascita, res.des_provincia_nascita, res.fkid_stato_nascita, res.des_stato_nascita, res.fkid_localita_nascita, res.des_localita_nascita, res.flg_stato, res.dt_ini, res.dt_fin, ind.cod_indirizzo, res.fkid_rappresentante
FROM v_indirizzo_corrente ind, v_ana_soggetto_corrente_res res
WHERE res.pkid = ind.fkid_soggetto
;

CREATE or replace VIEW v_ana_soggetto_corrente ( pkid, 
provenienza, cod_soggetto, codice_fiscale, flg_cf_calcolato, 
natura, tipo_natura, partita_iva, cognome, 
nome, altri_nomi, denominazione, dt_nascita, 
precisione_dt_nascita, sesso, note, fkid_comune_nascita, 
des_comune_nascita, des_provincia_nascita, fkid_stato_nascita, des_stato_nascita, 
fkid_localita_nascita, des_localita_nascita, flg_stato, fkid_rappresentante, 
id_ente, dt_ini, dt_fin, dt_mod
 ) AS SELECT v_ana_soggetto_corrente_res.pkid, v_ana_soggetto_corrente_res.provenienza, v_ana_soggetto_corrente_res.cod_soggetto, v_ana_soggetto_corrente_res.codice_fiscale, v_ana_soggetto_corrente_res.flg_cf_calcolato, v_ana_soggetto_corrente_res.natura, v_ana_soggetto_corrente_res.tipo_natura, v_ana_soggetto_corrente_res.partita_iva, v_ana_soggetto_corrente_res.cognome, v_ana_soggetto_corrente_res.nome, v_ana_soggetto_corrente_res.altri_nomi, v_ana_soggetto_corrente_res.denominazione, v_ana_soggetto_corrente_res.dt_nascita, v_ana_soggetto_corrente_res.precisione_dt_nascita, v_ana_soggetto_corrente_res.sesso, v_ana_soggetto_corrente_res.note, v_ana_soggetto_corrente_res.fkid_comune_nascita, v_ana_soggetto_corrente_res.des_comune_nascita, v_ana_soggetto_corrente_res.des_provincia_nascita, v_ana_soggetto_corrente_res.fkid_stato_nascita, v_ana_soggetto_corrente_res.des_stato_nascita, v_ana_soggetto_corrente_res.fkid_localita_nascita, v_ana_soggetto_corrente_res.des_localita_nascita, v_ana_soggetto_corrente_res.flg_stato, v_ana_soggetto_corrente_res.fkid_rappresentante, v_ana_soggetto_corrente_res.id_ente, v_ana_soggetto_corrente_res.dt_ini, v_ana_soggetto_corrente_res.dt_fin, v_ana_soggetto_corrente_res.dt_mod 
FROM v_ana_soggetto_corrente_res 
UNION 
 SELECT ana.pkid, ana.provenienza, ana.cod_soggetto, ana.codice_fiscale, 
 ana.flg_cf_calcolato, ana.natura, ana.tipo_natura, ana.partita_iva, ana.cognome, 
 ana.nome, ana.altri_nomi, ana.denominazione, ana.dt_nascita, ana.precisione_dt_nascita, 
 ana.sesso, ana.note, ana.fkid_comune_nascita, ana.des_comune_nascita, ana.des_provincia_nascita, 
 ana.fkid_stato_nascita, ana.des_stato_nascita, ana.fkid_localita_nascita, ana.des_localita_nascita, 
 ana.flg_stato, ana.fkid_rappresentante, ana.id_ente, ana.dt_ini, ana.dt_fin, ana.dt_mod 
FROM ana_soggetto ana 
WHERE ana.provenienza <> 0 AND ana.dt_fin IS NULL AND ana.dt_mod IS NOT NULL 
;

CREATE or replace VIEW v_pratica_edilizia_sogg AS 
	SELECT edipratica.pkid,
	       edipratica.id_servizio,
		   edipratica.cod_pratica,
		   edipratica.des_pratica,
		   edipratica.num_pratica,
		   edipratica.dt_pratica,
		   edipratica.num_protocollo,
		   edipratica.dt_protocollo,
		   edipratica.dt_ini_lavori,
		   edipratica.dt_fin_lavori,
		   edipratica.num_registro,
		   edipratica.oggetto,
		   edipratica.num_provvedimento,
		   edipratica.dt_provvedimento,
		   edipratica.dt_decadimento,
		   edipratica.des_decadimento,
		   edipratica.dt_agibilita,
		   edipratica.num_agibilita,
		   edipratica.cod_stato,
		   edipratica.des_stato,
		   edipratica.volume,
		   edipratica.superficie,
		   edipratica.id_pratica,
		   edipratica.id_ente,
		   edipratica.dt_mod,
		   edipratica.des_tipo_pratica,
		   edipratica.cod_tipo_pratica,
		   edisoggetto.pkid AS pkid_sogg,
		   edisoggetto.fkid_soggetto,
		   edisoggetto.cod_tipo_soggetto,
		   edisoggetto.des_tipo_soggetto,
		   edisoggetto.des_soggetto,
		   edisoggetto.cod_tipo_ruolo,
		   edisoggetto.des_tipo_ruolo,
		   edisoggetto.note
	FROM edi_pratica edipratica, edi_soggetto edisoggetto
	WHERE edipratica.pkid = edisoggetto.fkid_pratica
	      AND
		  edipratica.dt_mod IS NOT NULL
		  AND
		  edisoggetto.dt_mod IS NOT NULL;

CREATE or replace VIEW v_soggetto_provenienza AS 
	SELECT sogg.pkid AS sogg_pkid,
	       sogg.cod_soggetto AS codice_soggetto,
		    sogg.id_ente,
		    prov.dt_mod AS prov_dtmod,
		    ser.cod_provenienza,
		    ser.des_provenienza
	FROM ana_soggetto sogg,
	     ana_soggetto_prov prov,
		 ser_provenienza ser
	WHERE sogg.pkid = prov.fkid_soggetto
	      AND
		   prov.fkid_provenienza = ser.pkid
		   AND
		   sogg.dt_mod IS NOT NULL
		   AND
		   prov.dt_mod IS NOT NULL;


CREATE OR REPLACE VIEW v_indice_soggetto AS 
 SELECT sogg.pkid, 
 		sogg.cod_soggetto AS codice_soggetto, 
 		sogg.natura, 
 		sogg.codice_fiscale, 
 		sogg.partita_iva, 
 		sogg.cognome, 
 		sogg.nome, 
 		sogg.denominazione, 
 		sogg.sesso, 
 		sogg.dt_nascita AS data_nascita, 
 		sogg.des_comune_nascita AS comune_nascita, 
 		sogg.des_provincia_nascita AS provincia_nascita, 
 		sogg.des_stato_nascita AS stato_nascita, 
 		sogg.des_localita_nascita AS localita_nascita, 
 		sogg.id_ente, 
 		ser.cod_provenienza
   FROM ana_soggetto sogg, 
   		ana_soggetto_prov prov, 
   		ser_provenienza ser
  WHERE sogg.dt_fin IS NULL 
  		AND sogg.dt_mod IS NOT NULL 
  		AND prov.fkid_soggetto = sogg.pkid 
  		AND ser.pkid = prov.fkid_provenienza 
  		AND ((ser.cod_provenienza = 0 AND 
  			 prov.dt_mod = (( SELECT max(prov_1.dt_mod) AS max
           					  FROM ana_soggetto_prov prov_1, 
           					  	   ser_provenienza ser_1
          					  WHERE prov_1.fkid_soggetto = sogg.pkid 
          					  AND prov_1.fkid_provenienza = ser_1.pkid 
          					  AND ser_1.cod_provenienza = 0
          				    )) 
          	)
          	OR (ser.cod_provenienza <> 0 
          		AND prov.dt_mod = (( SELECT max(prov_2.dt_mod) AS max
           							 FROM ana_soggetto_prov prov_2, 
           								  ser_provenienza ser_2
          							 WHERE prov_2.fkid_soggetto = sogg.pkid 
          							 	   AND prov_2.fkid_provenienza = ser_2.pkid 
          							 	   AND ser_2.cod_provenienza <> 0
          						   ))
          		AND NOT (EXISTS ( SELECT prov_3.fkid_soggetto
           						  FROM ana_soggetto_prov prov_3, 
           						  	   ser_provenienza ser_3
          						  WHERE prov_3.fkid_soggetto = sogg.pkid 
          						  		AND ser_3.pkid = prov_3.fkid_provenienza 
          						  		AND ser_3.cod_provenienza = 0
          						)
          				)
          		)
          	);



CREATE OR REPLACE VIEW v_indice_sog_fis_ind AS 
 SELECT idx.pkid, 
 		idx.codice_soggetto, 
 		idx.natura, 
 		idx.codice_fiscale, 
 		idx.partita_iva, 
 		idx.cognome, 
 		idx.nome, 
 		idx.denominazione, 
 		idx.sesso, 
 		idx.data_nascita, 
 		idx.comune_nascita, 
 		idx.provincia_nascita, 
 		idx.stato_nascita, 
 		idx.localita_nascita, 
 		idx.id_ente, 
 		idx.cod_provenienza, 
 		ind.tipo, 
 		ind.comune_indirizzo, 
 		ind.area_indirizzo, 
 		ind.numero_civico_indirizzo, 
 		ind.lettera_civico_indirizzo, 
 		ind.colore, 
 		ind.corte, 
 		ind.scala, 
 		ind.interno, 
 		ind.piano, 
 		ind.edificio, 
 		ind.presso, 
 		ind.telefono, 
 		ind.fax, 
 		ind.email, 
 		ind.des_comune_indirizzo, 
 		ind.des_provincia_indirizzo, 
 		ind.cap_indirizzo, 
 		ind.des_stato_indirizzo, 
 		ind.des_loc_indirizzo, 
 		ind.contea_indirizzo, 
 		ind.des_consolato_indirizzo
   FROM v_indice_soggetto idx
   LEFT JOIN ( SELECT ind.fkid_soggetto, 
   					  ind.tipo, 
   					  ind.des_comune AS comune_indirizzo, 
   					  ind.des_area AS area_indirizzo, 
   					  ind.num_civ AS numero_civico_indirizzo, 
   					  ind.let_civ AS lettera_civico_indirizzo, 
   					  ind.colore, 
   					  ind.corte, 
   					  ind.scala, 
   					  ind.interno, 
   					  ind.piano, 
   					  ind.edificio, 
   					  ind.presso, 
   					  ind.telefono, 
   					  ind.fax, 
   					  ind.email, 
   					  ind.des_comune AS des_comune_indirizzo, 
   					  ind.des_provincia AS des_provincia_indirizzo, 
   					  ind.cap AS cap_indirizzo, 
   					  ind.des_stato AS des_stato_indirizzo, 
   					  ind.des_localita AS des_loc_indirizzo, 
   					  ind.contea AS contea_indirizzo, 
   					  ind.des_consolato AS des_consolato_indirizzo, 
   					  ind.dt_fin, 
   					  ind.dt_mod
           		FROM ana_indirizzo ind
          		WHERE ind.dt_fin IS NULL 
          			  AND ind.dt_mod IS NOT NULL
          		) ind ON idx.pkid = ind.fkid_soggetto
  WHERE idx.natura = 0 
  		AND (idx.cod_provenienza = 0 
  			 AND ind.tipo = 'RES'
  			)
  		OR  ( idx.cod_provenienza <> 0 
  			  AND ind.tipo <> 'RES'
  			  AND ind.dt_mod = (( SELECT max(ind_3.dt_mod) AS max
      							  FROM ana_indirizzo ind_3
                                  WHERE ind_3.fkid_soggetto = idx.pkid 
                                  		AND
                                  		 ind_3.tipo <> 'RES'
                                )) 
               AND idx.cod_provenienza = (( SELECT min(prov_1.cod_provenienza) AS cod_prov
      										FROM v_indice_soggetto prov_1
     										WHERE idx.codice_soggetto = prov_1.codice_soggetto
     									  ))
     		);



			
CREATE OR REPLACE VIEW v_indice_sog_giur_ind AS 
 SELECT idx.pkid, 
 		idx.codice_soggetto, 
 		idx.natura, 
 		idx.codice_fiscale, 
 		idx.partita_iva, 
 		idx.cognome, 
 		idx.nome, 
 		idx.denominazione, 
 		idx.sesso, 
 		idx.data_nascita, 
 		idx.comune_nascita, 
 		idx.provincia_nascita, 
 		idx.stato_nascita, 
 		idx.localita_nascita, 
 		idx.id_ente, 
 		idx.cod_provenienza, 
 		ind.tipo, 
 		ind.comune_indirizzo, 
 		ind.area_indirizzo, 
 		ind.numero_civico_indirizzo, 
 		ind.lettera_civico_indirizzo, 
 		ind.colore, 
 		ind.corte, 
 		ind.scala, 
 		ind.interno, 
 		ind.piano, 
 		ind.edificio, 
 		ind.presso, 
 		ind.telefono, 
 		ind.fax, 
 		ind.email, 
 		ind.des_comune_indirizzo, 
 		ind.des_provincia_indirizzo, 
 		ind.cap_indirizzo, 
 		ind.des_stato_indirizzo, 
 		ind.des_loc_indirizzo, 
 		ind.contea_indirizzo, 
 		ind.des_consolato_indirizzo
   FROM v_indice_soggetto idx
   LEFT JOIN ( SELECT ind.tipo, ind.fkid_soggetto, ind.des_comune AS comune_indirizzo, ind.des_area AS area_indirizzo, ind.num_civ AS numero_civico_indirizzo, ind.let_civ AS lettera_civico_indirizzo, ind.colore, ind.corte, ind.scala, ind.interno, ind.piano, ind.edificio, ind.presso, ind.telefono, ind.fax, ind.email, ind.des_comune AS des_comune_indirizzo, ind.des_provincia AS des_provincia_indirizzo, ind.cap AS cap_indirizzo, ind.des_stato AS des_stato_indirizzo, ind.des_localita AS des_loc_indirizzo, ind.contea AS contea_indirizzo, ind.des_consolato AS des_consolato_indirizzo, ind.dt_fin, ind.dt_mod
           	   FROM ana_indirizzo ind
          	   WHERE ind.dt_fin IS NULL 
          	   		 AND ind.dt_mod IS NOT NULL
          	  ) ind ON idx.pkid = ind.fkid_soggetto
   WHERE (idx.natura <> 0 
   		  OR ( idx.natura = 0 
   		  	   AND idx.partita_iva IS NOT NULL)
   		  	 ) 
   		  AND ( (idx.cod_provenienza = 0 
   		  		AND ind.tipo = 'RES' )
   		  		OR ( idx.cod_provenienza <> 0 
   		  			 AND ind.tipo <> 'RES' 
   		  			 AND ind.dt_mod = (( SELECT max(ind_3.dt_mod) AS max
      									 FROM ana_indirizzo ind_3
     									 WHERE ind_3.fkid_soggetto = idx.pkid 
     									 	   AND ind_3.tipo <> 'RES'
     								  )) 
     				 AND idx.cod_provenienza = (( SELECT min(soggmin.cod_provenienza) AS min
      											  FROM v_indice_soggetto soggmin
     											  WHERE soggmin.codice_soggetto = idx.codice_soggetto 
     											  		AND soggmin.cod_provenienza <> 0
     											))
     				)
     			);


CREATE or replace VIEW v_indice_oggetto_ind AS 
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
   LEFT JOIN ( SELECT ind.pkid, ind.fkid_ot, ind.des_area AS area_indirizzo, ind.num_civ AS numero_civico, ind.let_civ AS lettera_civico, ind.colore AS colore_civico, ind.corte AS corte_civico, ind.scala AS scala_civico, ind.interno AS interno_civico, ind.piano AS piano_civico, ind.edificio AS edificio_civico, ind.dt_ini, ind.dt_fin
                FROM ot_indirizzo ind
               WHERE ind.dt_mod IS NOT NULL AND ind.dt_fin IS NULL) ind ON aot.pkid = ind.fkid_ot
  WHERE aot.dt_mod IS NOT NULL;

CREATE or replace VIEW v_indice_oggetto_ide AS 
 SELECT aot.pkid AS aot_pkid, 
 		aot.cod_ecografico AS codice_ecografico, 
        aot.cod_aot,
 		aot.cod_catasto, 
 		aot.dt_mod AS aot_dtmod, 
 		aot.cod_istat_comune AS aot_cod_istat, 
 		ide.tipo_catasto,
		ide.des_identificativo,
		ide.id_comune,
		ide.sezione,
		ide.foglio,
		ide.mappale,
		ide.sub,
		ide.prot_anno,
		ide.prot_num,
		ide.id_ente AS id_ente,
		ide.dt_mod AS ide_dt_mod
   FROM ot_indice aot
   LEFT JOIN (SELECT 	ide.fkid_ot,
   						ide.tipo_catasto,
   						ide.des_identificativo,
						ide.id_comune,
						ide.sezione,
						ide.foglio,
						ide.mappale,
						ide.sub,
						ide.prot_anno,
						ide.prot_num,
						ide.id_ente, 
						ide.dt_mod 
                FROM ot_identificativo ide
                WHERE ide.dt_mod IS NOT NULL) ide ON aot.pkid = ide.fkid_ot
  WHERE aot.dt_mod IS NOT NULL;
  


CREATE or replace VIEW v_oggetto_provenienza AS 
 SELECT ogg.pkid AS ogg_pkid, 
 		ogg.cod_aot, 
 		ogg.cod_ecografico, 
 		ogg.cod_istat_comune,
 		ogg.dt_mod AS ogg_dt_mod,
		ogg.id_ente,
 		prov.dt_mod AS prov_dtmod, 
 		ser.cod_provenienza, 
 		ser.des_provenienza
   FROM ot_indice ogg, ot_provenienza prov, ser_provenienza ser
  WHERE ogg.pkid = prov.fkid_ot 
  		AND prov.fkid_provenienza = ser.pkid 
  		AND ogg.dt_mod IS NOT NULL 
  		AND prov.dt_mod IS NOT NULL;


CREATE OR REPLACE VIEW v_soggetto_classe AS 
 SELECT scusoggettoclasse.fkid_classe, scusoggettoclasse.anno_scolastico
   FROM scu_soggetto scusoggettoclasse
  GROUP BY scusoggettoclasse.fkid_classe, scusoggettoclasse.anno_scolastico;

CREATE OR REPLACE VIEW v_scu_corso_classe_sog AS 
SELECT scusoggettoclasse.fkid_classe, 
	scusoggettoclasse.anno_scolastico,  
	scuclasse.cod_classe, 
	scuclasse.des_classe, 
	scuclasse.sezione, 
	scuclasse.tempo,  
	scuclasse.fkid_area, 
	scuclasse.des_area, 
	scuclasse.num_civ, 
	scuclasse.let_civ, 
	scuclasse.fkid_comune, 
	scuclasse.des_comune, 
	scuclasse.des_provincia, 
	scuclasse.note AS note_classe, 
	scuclasse.dt_ini AS dtini_classe, 
	scuclasse.dt_fin AS dtfin_classe, 
	scuclasse.id_ente, 
	scuclasse.dt_mod AS dtmod_classe,
  	scucorso.pkid AS pkid_corso,
  	scucorso.fkid_istituto ,
	scucorso.cod_corso ,
	scucorso.des_corso,
	scucorso.num_posti,
	scucorso.note AS note_corso,
	scucorso.dt_ini AS dtini_corso,
	scucorso.dt_fin AS dtfin_corso,
	scucorso.dt_mod AS dtmod_corso
   FROM v_soggetto_classe scusoggettoclasse, scu_classe scuclasse, scu_corso scucorso
   WHERE scuclasse.dt_mod is not null 
	and scucorso.dt_mod is not null 
	and scuclasse.dt_fin is null 
	and scucorso.dt_fin  is null	 
	and scusoggettoclasse.fkid_classe = scuclasse.pkid
	and scucorso.pkid=scuclasse.fkid_corso;


CREATE INDEX idx_atto_ente ON ana_atto(id_ente)
;
CREATE INDEX idx_atto_sogg ON ana_atto(fkid_soggetto)
;
CREATE INDEX idx_annota_ente ON ana_atto_annota(id_ente)
;
CREATE INDEX idx_annota_atto ON ana_atto_annota(fkid_atto)
;
CREATE INDEX idx_ana_cancellazione ON ana_cancellazione(id_ente)
;
CREATE INDEX idx_can_sogg ON ana_cancellazione(fkid_soggetto)
;
CREATE INDEX idx_ana_carta_identita ON ana_carta_identita(id_ente)
;
CREATE INDEX idx_cia_sogg ON ana_carta_identita(fkid_soggetto)
;
CREATE INDEX idx_ana_cittadinanza ON ana_cittadinanza(id_ente)
;
CREATE INDEX idx_cit_sogg ON ana_cittadinanza(fkid_soggetto)
;
CREATE INDEX idx_economica ON ana_economica(id_ente)
;
CREATE INDEX idx_economica_sogg ON ana_economica(fkid_soggetto)
;
CREATE INDEX idx_ana_ele ON ana_ele(id_ente)
;
CREATE INDEX idx_ele_sogg ON ana_ele(fkid_soggetto)
;
CREATE INDEX idx_ana_fam ON ana_fam(id_ente)
;
CREATE INDEX idx_fam_cod ON ana_fam(cod_famiglia)
;
CREATE INDEX idx_ana_fam_componente ON ana_fam_componente(id_ente)
;
CREATE INDEX idx_comp_fam ON ana_fam_componente(fkid_fam)
;
CREATE INDEX idx_comp_sogg ON ana_fam_componente(fkid_soggetto)
;
CREATE INDEX idx_fornitore ON ana_fornitore(id_ente)
;
CREATE INDEX idx_fornitore_sogg ON ana_fornitore(fkid_soggetto)
;
CREATE INDEX idx_ana_indirizzo ON ana_indirizzo(id_ente)
;
CREATE INDEX idx_ind_sogg ON ana_indirizzo(fkid_soggetto, tipo)
;
CREATE INDEX idx_ana_iscrizione ON ana_iscrizione(id_ente)
;
CREATE INDEX idx_isc_sogg ON ana_iscrizione(fkid_soggetto)
;
CREATE INDEX idx_num_patente ON ana_patente(num_patente)
;
CREATE INDEX idx_ana_patente ON ana_patente(id_ente)
;
CREATE INDEX idx_pat_sogg ON ana_patente(fkid_soggetto)
;
CREATE INDEX idx_num_pensione ON ana_pensione(num_pensione)
;
CREATE INDEX idx_ana_pensione ON ana_pensione(id_ente)
;
CREATE INDEX idx_pen_sogg ON ana_pensione(fkid_soggetto)
;
CREATE INDEX idx_ana_premesso_soggiorno ON ana_permesso_soggiorno(id_ente)
;
CREATE INDEX idx_per_sogg ON ana_permesso_soggiorno(fkid_soggetto)
;
CREATE INDEX idx_ana_professione ON ana_professione(id_ente)
;
CREATE INDEX idx_pro_sogg ON ana_professione(fkid_soggetto)
;
CREATE INDEX idx_ana_rappresentante ON ana_rappresentante(id_ente)
;
CREATE INDEX idx_rap_sogg ON ana_rappresentante(fkid_soggetto)
;
CREATE INDEX idx_ana_relazione ON ana_relazione(cod_relazione, sesso)
;
CREATE INDEX idx_sch_sogg ON ana_scheda(fkid_soggetto)
;
CREATE INDEX idx_ana_scheda ON ana_scheda(id_ente)
;
CREATE INDEX idx_cod_soggetto ON ana_soggetto(cod_soggetto)
;
CREATE INDEX idx_ana_soggetto ON ana_soggetto(id_ente)
;
CREATE INDEX idx_cod_fiscale ON ana_soggetto(codice_fiscale)
;
CREATE INDEX idx_dtm_soggetto ON ana_soggetto(dt_mod)
;
CREATE INDEX kcod_soggetto_ele ON ana_soggetto_ele(cod_soggetto)
;
CREATE INDEX idx_ana_soggetto_ele ON ana_soggetto_ele(id_ente)
;
CREATE INDEX idx_cod_fiscale_ele ON ana_soggetto_ele(codice_fiscale)
;
CREATE INDEX idx_ele_sel ON ana_soggetto_ele(fkid_soggetto)
;
CREATE INDEX idx_prov ON ana_soggetto_prov(id_ente)
;
CREATE INDEX id_prov_soggetto ON ana_soggetto_prov(fkid_soggetto)
;
CREATE INDEX idx_ana_stciv ON ana_stciv(id_ente)
;
CREATE INDEX idx_stc_sogg ON ana_stciv(fkid_soggetto)
;
CREATE INDEX idx_stu_sogg ON ana_studio(fkid_soggetto)
;
CREATE INDEX idx_ana_studio ON ana_studio(id_ente)
;
CREATE INDEX idx_cat_aot ON cat_aot(id_ente)
;
CREATE INDEX idx_cat_aot_prg ON cat_aot_prg(id_ente)
;
CREATE INDEX idx_prg_aot ON cat_aot_prg(fkid_prg)
;
CREATE INDEX idx_cat_dati ON cat_dati(id_ente)
;
CREATE INDEX idx_cat_aot_dati ON cat_dati(fkid_aot)
;
CREATE INDEX idx_identificativo_aot ON cat_identificativo(fkid_aot)
;
CREATE INDEX idx_cat_identificativo ON cat_identificativo(id_ente)
;
CREATE INDEX idx_cat_indirizzo ON cat_indirizzo(id_ente)
;
CREATE INDEX idx_indirizzo_aot ON cat_indirizzo(fkid_aot)
;
CREATE INDEX idx_cat_aot_indirizzo_area ON cat_indirizzo(fkid_area)
;
CREATE INDEX idx_cat_prg ON cat_prg(id_ente)
;
CREATE INDEX idx_cod_prg ON cat_prg(cod_prg)
;
CREATE INDEX idx_cat_proprietario ON cat_proprietario(id_ente)
;
CREATE INDEX idx_proprieatrio_aot ON cat_proprietario(fkid_aot)
;
CREATE INDEX idx_soggetto_proprietario ON cat_proprietario(fkid_soggetto)
;
CREATE INDEX idx_aot_rendita ON cat_rendita(fkid_aot)
;
CREATE INDEX idx_rendita ON cat_rendita(id_ente)
;
CREATE INDEX idx_com_allegato ON com_allegato(id_ente)
;
CREATE INDEX idx_com_denuncia ON com_denuncia(id_ente)
;
CREATE INDEX idx_com_esercizio ON com_esercizio(id_ente)
;
CREATE INDEX idx_com_merceologico ON com_merceologico(id_ente)
;
CREATE INDEX idx_com_sogg ON com_requisiti(id_ente)
;
CREATE INDEX idx_com_soggetto ON com_soggetto(id_ente)
;
CREATE INDEX idx_edi_pag_ente ON edi_pagamento(id_ente)
;
CREATE INDEX idx_bol_dett_1 ON edi_pratica_dett(id_ente)
;
CREATE INDEX idx_edi_soggetto ON edi_soggetto(id_ente)
;
CREATE INDEX esinextidpk2 ON esinextid(nome_tabella)
;
CREATE INDEX idx_identificativo ON ot_identificativo(id_ente)
;
CREATE INDEX idx_identificativo_indice ON ot_identificativo(fkid_ot)
;
CREATE INDEX idx_ot_indice ON ot_indice(id_ente)
;
CREATE INDEX idx_ot_indirizzo ON ot_indirizzo(id_ente)
;
CREATE INDEX idx_indirizzo_indice ON ot_indirizzo(fkid_ot)
;
CREATE INDEX idx_provenienza ON ot_provenienza(id_ente)
;
CREATE INDEX idx_provenienza_indice ON ot_provenienza(fkid_ot)
;
CREATE INDEX idx_bol_sogg ON pag_bolletta(fkid_soggetto)
;
CREATE INDEX idx_pag_bolletta ON pag_bolletta(id_ente)
;
CREATE INDEX idx_bol_ser ON pag_bolletta(id_servizio)
;
CREATE INDEX idx_bol_cod ON pag_bolletta(cod_bolletta)
;
CREATE INDEX idx_bol_dett ON pag_bolletta_dett(id_ente)
;
CREATE INDEX idx_det_boll ON pag_bolletta_dett(fkid_bolletta)
;
CREATE INDEX idx_pag_bolletta_iva ON pag_bolletta_iva(id_ente)
;
CREATE INDEX idx_pag_bolletta_rata ON pag_bolletta_rata(id_ente)
;
CREATE INDEX idx_rat_boll ON pag_bolletta_rata(fkid_bolletta)
;
CREATE INDEX idx_pag_pagamento_tipo ON pag_pagamento_tipo(id_ente)
;
CREATE INDEX idx_pol_articolo ON pol_articolo(id_ente)
;
CREATE INDEX idx_pol_accertamento ON pol_infrazione(id_ente)
;
CREATE INDEX kcod_infrazione ON pol_infrazione(cod_infrazione)
;
CREATE INDEX kcod_permesso ON pol_permesso(cod_permesso, id_servizio)
;
CREATE INDEX idx_pol_permesso ON pol_permesso(id_ente)
;
CREATE INDEX idx_pol_tipo_permesso ON pol_tipo_permesso(id_ente)
;
CREATE INDEX idx_pol_tipo_veicolo ON pol_tipo_veicolo(id_ente)
;
CREATE INDEX idx_vei_perm ON pol_vei_perm(fkid_veicolo)
;
CREATE INDEX idx_per_perm ON pol_vei_perm(fkid_permesso)
;
CREATE INDEX idx_vei_sogg ON pol_vei_sogg(fkid_soggetto)
;
CREATE INDEX idx_pol_veicolo ON pol_veicolo(id_ente)
;
CREATE INDEX idx_pol_zona_permesso ON pol_zona_permesso(id_ente)
;
CREATE INDEX idx_pra_allegato ON pra_allegato(id_ente_destinatario)
;
CREATE INDEX idx_pra_pratica_testa_1 ON pra_incompleta(id_ente_destinatario)
;
CREATE INDEX idx_pra_codfisc_1 ON pra_incompleta(codice_fiscale)
;
CREATE INDEX idx_testa ON pra_pratica_dett(fkid_testa)
;
CREATE INDEX idx_pra_pratica_dett ON pra_pratica_dett(id_ente_destinatario)
;
CREATE INDEX idx_pra_pratica_pag ON pra_pratica_pag(id_ente_destinatario)
;
CREATE INDEX idx_prp_tes ON pra_pratica_pag(fkid_testa)
;
CREATE INDEX idx_prp_ser ON pra_pratica_pag(id_servizio)
;
CREATE INDEX idx_prp_bol ON pra_pratica_pag(cod_bolletta)
;
CREATE INDEX idx_pra_pratica_testa ON pra_pratica_testa(id_ente_destinatario)
;
CREATE INDEX idx_pra_codfisc ON pra_pratica_testa(codice_fiscale)
;
CREATE INDEX idx_pra_recapito ON pra_recapito(id_ente_destinatario)
;
CREATE INDEX idx_pra_soggetto ON pra_soggetto(id_ente_destinatario)
;
CREATE INDEX idx_rsu_ins_agevolazione ON rsu_ins_agevolazione(id_ente)
;
CREATE INDEX idx_rsu_calcolo_ins ON rsu_ins_calcolo(id_ente)
;
CREATE INDEX idx_rsu_ins_componente ON rsu_ins_componente(id_ente)
;
CREATE INDEX idx_rsu_insediamento ON rsu_insediamento(id_ente)
;
CREATE INDEX idx_rsu_pag_addizionale ON rsu_pag_addizionale(id_ente)
;
CREATE INDEX idx_scu_classe ON scu_classe(id_ente)
;
CREATE INDEX idx_classe_area ON scu_classe(fkid_area)
;
CREATE INDEX idx_classe_corso ON scu_classe(fkid_corso)
;
CREATE INDEX scu_classe01 ON scu_soggetto(fkid_classe)
;
CREATE INDEX kcod_centro ON sdi_centro(cod_centro)
;
CREATE INDEX idx_sdi_centro ON sdi_centro(id_ente)
;
CREATE INDEX idx_centro_area ON sdi_centro(fkid_area)
;
CREATE INDEX idx_centro_comune ON sdi_centro(fkid_comune)
;
CREATE INDEX ana_soggetto40 ON scu_soggetto(fkid_soggetto)
;
CREATE INDEX idx_scu_corso ON scu_corso(id_ente)
;
CREATE INDEX idx_scu_ist ON scu_corso(fkid_istituto)
;

CREATE INDEX ter_comune15 ON scu_classe(fkid_comune)
;

CREATE INDEX ter_area12 ON scu_istituto(fkid_area)
;

CREATE INDEX kcod_istituto ON scu_istituto(cod_istituto)
;
CREATE INDEX idx_scu_isitituto ON scu_istituto(id_ente)
;
CREATE INDEX ter_comune02 ON scu_istituto(fkid_comune)
;
CREATE INDEX idx_scu_soggetto ON scu_soggetto(id_ente)
;
CREATE INDEX idx_sdi_graduatoria ON sdi_graduatoria(id_ente)
;
CREATE INDEX idx_sdi_pos_graduatoria ON sdi_pos_graduatoria(id_ente)
;
CREATE INDEX idx_sdi_pos_grad ON sdi_pos_graduatoria(fkid_graduatoria)
;
CREATE INDEX idx_sdi_servizio ON sdi_servizio(id_ente)
;
CREATE INDEX idx_servizio_dett ON sdi_servizio_dett(id_ente)
;
CREATE INDEX idx_pol_veicolo_1_1 ON ser_import(id_ente)
;
CREATE INDEX idx_pol_veicolo_1 ON ser_servizio(id_ente)
;
CREATE INDEX idx_ter_accesso ON ter_accesso(id_ente)
;
CREATE INDEX kcod_area ON ter_area(cod_area)
;
CREATE INDEX idx_ter_area ON ter_area(id_ente)
;
CREATE INDEX idx_ter_centro_nascita ON ter_centro_nascita(id_ente)
;
CREATE INDEX kcod_comune ON ter_comune(cod_comune)
;
CREATE INDEX kcod_consolato ON ter_consolato(cod_consolato)
;
CREATE INDEX idx_ter_ente ON ter_ente(id_ente)
;
CREATE INDEX kcod_localita ON ter_localita(cod_localita)
;
CREATE INDEX idx_ter_localita ON ter_localita(id_ente)
;
CREATE INDEX kcod_provincia ON ter_provincia(cod_provincia)
;
CREATE INDEX kcod_regione ON ter_regione(cod_regione)
;
CREATE INDEX idx_ter_sez_ele ON ter_sez_ele(id_ente)
;
CREATE INDEX kcod_stato ON ter_stato(cod_stato)
;
CREATE INDEX kcod_tribunale ON ter_tribunale(cod_tribunale)
;
CREATE INDEX idx_con_den ON tri_con_denuncia(id_ente)
;
CREATE INDEX idx_den_con ON tri_con_denuncia(fkid_concessione)
;
CREATE INDEX idx_con_dett ON tri_con_dett(id_ente)
;
CREATE INDEX idx_dett_con ON tri_con_dett(fkid_concessione)
;
CREATE INDEX idx_con_dur ON tri_con_durata(id_ente)
;
CREATE INDEX idx_dur_con ON tri_con_durata(fkid_concessione)
;
CREATE INDEX idx_tri_con ON tri_concessione(id_ente)
;
CREATE INDEX idx_con_sog ON tri_concessione(fkid_soggetto)
;
CREATE INDEX ana_soggetto42 ON sdi_pos_graduatoria(fkid_soggetto)
;
CREATE INDEX ana_soggetto41 ON sdi_servizio(fkid_soggetto)
;
CREATE INDEX ana_rapp01 ON sdi_servizio(fkid_rappresentante)
;
CREATE INDEX sdi_servizio01 ON sdi_servizio_dett(fkid_servizio)
;

