/*
 *ER/Studio 5.5 SQL Code Generation
 * Project :      Model
 *
 * Date Created : Friday, November 14, 2003 15:21:15
 * Target DBMS : Microsoft SQL Server 2000
 *

Versione 1.21 LTT solo tabelle ana e ter
 */


CREATE TABLE ana_atto(
    pkid                   decimal(22, 0)    NOT NULL,
    fkid_soggetto          decimal(22, 0)    NULL,
    tipo_atto              int               NULL,
    dt_riferimento         datetime          NULL,
    fkid_com_reg           decimal(22, 0)    NULL,
    des_com_reg            varchar(80)       NULL,
    des_prov_reg           varchar(80)       NULL,
    fkid_centro_nascita    decimal(22, 0)    NULL,
    des_centro_nascita     varchar(80)       NULL,
    anno_iscrizione        int               NULL,
    num_iscrizione         int               NULL,
    parte_iscrizione       varchar(2)        NULL,
    serie_iscrizione       varchar(6)        NULL,
    vol_iscrizione         varchar(4)        NULL,
    uff_iscrizione         varchar(4)        NULL,
    fkid_com_tras          decimal(22, 0)    NULL,
    des_com_tras           varchar(80)       NULL,
    des_prov_tras          varchar(80)       NULL,
    anno_trascrizione      int               NULL,
    num_trascrizione       int               NULL,
    parte_trascrizione     varchar(2)        NULL,
    serie_trascrizione     varchar(6)        NULL,
    vol_trascrizione       varchar(4)        NULL,
    uff_trascrizione       varchar(4)        NULL,
    n_sen_divorzio         int               NULL,
    fkid_tribunale         decimal(22, 0)    NULL,
    des_tribunale          varchar(80)       NULL,
    dt_sen_divorzio        datetime          NULL,
    id_ente                numeric(7, 0)     NULL,
    dt_mod                 datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_atto_annota(
    pkid          decimal(22, 0)    NOT NULL,
    fkid_atto     decimal(22, 0)    NULL,
    dt_nota       datetime          NULL,
    codifica      varchar(20)       NULL,
    nota          varchar(255)      NULL,
    flg_stampa    int               NULL,
    id_ente       numeric(7, 0)     NULL,
    dt_mod        datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_cancellazione(
    pkid                 decimal(22, 0)    NOT NULL,
    fkid_soggetto        decimal(22, 0)    NULL,
    cod_cancellazione    varchar(20)       NULL,
    des_cancellazione    varchar(80)       NULL,
    dt_can               datetime          NULL,
    motivo               varchar(50)       NULL,
    dt_dec               datetime          NULL,
    fkid_comune          decimal(22, 0)    NULL,
    des_comune           varchar(80)       NULL,
    des_provincia        varchar(80)       NULL,
    num_pratica          int               NULL,
    dt_def_pratica       datetime          NULL,
    fkid_stato           decimal(22, 0)    NULL,
    des_stato            varchar(80)       NULL,
    fkid_localita        decimal(22, 0)    NULL,
    des_localita         varchar(80)       NULL,
    id_ente              numeric(7, 0)     NULL,
    dt_ini               datetime          NULL,
    dt_fin               datetime          NULL,
    dt_mod               datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_carta_identita(
    pkid                 decimal(22, 0)    NOT NULL,
    fkid_soggetto        decimal(22, 0)    NULL,
    dt_emissione         datetime          NULL,
    numcarta             varchar(20)       NULL,
    flg_residente        int               NULL,
    flg_espatrio         int               NULL,
    flg_elettronica      int               NULL,
    tipo                 int               NULL,
    stato                int               NULL,
    prog_denuncia        int               NULL,
    dt_denuncia          datetime          NULL,
    dt_scadenza          datetime          NULL,
    fkid_comune          decimal(22, 0)    NULL,
    des_comune           varchar(80)       NULL,
    des_provincia        varchar(80)       NULL,
    des_statocivile      varchar(80)       NULL,
    professione          varchar(80)       NULL,
    statura              varchar(20)       NULL,
    capelli              varchar(80)       NULL,
    occhi                varchar(80)       NULL,
    segniparticolari1    varchar(80)       NULL,
    segniparticolari2    varchar(80)       NULL,
    segniparticolari3    varchar(80)       NULL,
    dt_rinnovo           datetime          NULL,
    id_ente              numeric(7, 0)     NULL,
    dt_ini               datetime          NULL,
    dt_fin               datetime          NULL,
    dt_mod               datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_cittadinanza(
    pkid                 decimal(22, 0)    NOT NULL,
    fkid_soggetto        decimal(22, 0)    NULL,
    fkid_cittadinanza    decimal(22, 0)    NULL,
    des_cittadinanza     varchar(80)       NULL,
    id_ente              numeric(7, 0)     NULL,
    dt_ini               datetime          NULL,
    dt_fin               datetime          NULL,
    dt_mod               datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_ele(
    pkid              decimal(22, 0)    NOT NULL,
    fkid_soggetto     decimal(22, 0)    NULL,
    fkid_sez_ele      decimal(22, 0)    NULL,
    sesso             int               NULL,
    num_lista_gen     int               NULL,
    num_lista_sez     int               NULL,
    num_sez           int               NULL,
    num_fascicolo     int               NULL,
    num_tessera       int               NULL,
    flg_scrutatore    int               NULL,
    flg_presidente    int               NULL,
    anno_iscr         int               NULL,
    fkid_accesso      int               NULL,
    ind_sede_ele      varchar(50)       NULL,
    id_ente           numeric(7, 0)     NULL,
    dt_mod            datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_fam(
    pkid            decimal(22, 0)    NOT NULL,
    cod_famiglia    varchar(20)       NULL,
    tipo_ana        int               NULL,
    tipo_scheda     int               NULL,
    num_scheda      int               NULL,
    intestatario    varchar(80)       NULL,
    specie_conv     varchar(80)       NULL,
    des_conv        varchar(80)       NULL,
    id_ente         numeric(7, 0)     NULL,
    dt_ini          datetime          NULL,
    dt_fin          datetime          NULL,
    dt_mod          datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_fam_componente(
    pkid                  decimal(22, 0)    NOT NULL,
    fkid_fam              decimal(22, 0)    NULL,
    fkid_soggetto         decimal(22, 0)    NULL,
    fkid_relazione        decimal(22, 0)    NULL,
    des_relazione         varchar(80)       NULL,
    motivo_ingresso       varchar(30)       NULL,
    num_ordine            int               NULL,
    dt_def_pratica_ing    datetime          NULL,
    dt_dec_ingresso       datetime          NULL,
    motivo_uscita         varchar(30)       NULL,
    dt_def_pratica_usc    datetime          NULL,
    dt_dec_uscita         datetime          NULL,
    id_ente               numeric(7, 0)     NULL,
    dt_ini                datetime          NULL,
    dt_fin                datetime          NULL,
    dt_mod                datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_indirizzo(
    pkid              decimal(22, 0)    NOT NULL,
    fkid_soggetto     decimal(22, 0)    NULL,
    tipo              varchar(3)        NOT NULL,
    fkid_area         decimal(22, 0)    NULL,
    des_area          varchar(80)       NULL,
    num_civ           int               NULL,
    let_civ           varchar(5)        NULL,
    colore            varchar(10)       NULL,
    corte             varchar(9)        NULL,
    scala             varchar(9)        NULL,
    interno           varchar(9)        NULL,
    piano             varchar(9)        NULL,
    edificio          varchar(9)        NULL,
    presso            varchar(50)       NULL,
    telefono          varchar(30)       NULL,
    fax               varchar(30)       NULL,
    email             varchar(50)       NULL,
    fkid_stato        decimal(22, 0)    NULL,
    des_stato         varchar(80)       NULL,
    contea            varchar(50)       NULL,
    fkid_localita     decimal(22, 0)    NULL,
    des_localita      varchar(80)       NULL,
    cap               varchar(10)       NULL,
    fkid_comune       decimal(22, 0)    NULL,
    des_comune        varchar(80)       NULL,
    des_provincia     varchar(80)       NULL,
    fkid_consolato    decimal(22, 0)    NULL,
    des_consolato     varchar(80)       NULL,
    id_ente           numeric(7, 0)     NULL,
    dt_ini            datetime          NULL,
    dt_fin            datetime          NULL,
    dt_mod            datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_iscrizione(
    pkid              decimal(22, 0)    NOT NULL,
    fkid_soggetto     decimal(22, 0)    NULL,
    cod_iscrizione    varchar(20)       NULL,
    des_iscrizione    varchar(80)       NULL,
    dt_isc            datetime          NULL,
    des_motivo        varchar(80)       NULL,
    dt_dec            datetime          NULL,
    fkid_comune       decimal(22, 0)    NULL,
    des_comune        varchar(80)       NULL,
    des_provincia     varchar(80)       NULL,
    num_pratica       int               NULL,
    dt_def_pratica    datetime          NULL,
    fkid_stato        decimal(22, 0)    NULL,
    des_stato         varchar(80)       NULL,
    fkid_localita     decimal(22, 0)    NULL,
    des_localita      varchar(80)       NULL,
    des_com_aire      varchar(80)       NULL,
    id_ente           numeric(7, 0)     NULL,
    dt_ini            datetime          NULL,
    dt_fin            datetime          NULL,
    dt_mod            datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_patente(
    pkid                 decimal(22, 0)    NOT NULL,
    fkid_soggetto        decimal(22, 0)    NULL,
    num_patente          varchar(20)       NULL,
    categoria            varchar(2)        NULL,
    dt_rilascio          datetime          NULL,
    dt_scadenza          datetime          NULL,
    des_ente_rilascio    varchar(80)       NULL,
    punti                int               NULL,
    id_ente              numeric(7, 0)     NULL,
    dt_ini               datetime          NULL,
    dt_fin               datetime          NULL,
    dt_mod               datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_pensione(
    pkid             decimal(22, 0)    NOT NULL,
    fkid_soggetto    decimal(22, 0)    NULL,
    des_pensione     varchar(80)       NULL,
    cod_ente         varchar(20)       NULL,
    des_ente         varchar(80)       NULL,
    dt_ass           datetime          NULL,
    num_pensione     int               NULL,
    id_ente          numeric(7, 0)     NULL,
    dt_ini           datetime          NULL,
    dt_fin           datetime          NULL,
    dt_mod           datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_permesso_soggiorno(
    pkid             decimal(22, 0)    NOT NULL,
    fkid_soggetto    decimal(22, 0)    NULL,
    fkid_comune      decimal(22, 0)    NULL,
    des_comune       varchar(80)       NULL,
    des_provincia    varchar(80)       NULL,
    tipo             int               NULL,
    numero           varchar(20)       NULL,
    dt_rilascio      datetime          NULL,
    dt_rinnovo       datetime          NULL,
    dt_scadenza      datetime          NULL,
    motivo           varchar(100)      NULL,
    id_ente          numeric(7, 0)     NULL,
    dt_ini           datetime          NULL,
    dt_fin           datetime          NULL,
    dt_mod           datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_professione(
    pkid                decimal(22, 0)    NOT NULL,
    fkid_soggetto       decimal(22, 0)    NULL,
    des_professione     varchar(80)       NULL,
    cond_professione    varchar(38)       NULL,
    set_prof            varchar(18)       NULL,
    id_ente             numeric(7, 0)     NULL,
    dt_ini              datetime          NULL,
    dt_fin              datetime          NULL,
    dt_mod              datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_relazione(
    pkid             decimal(22, 0)    NOT NULL,
    cod_relazione    varchar(20)       NULL,
    sesso            int               NULL,
    des_relazione    varchar(80)       NULL,
    grado            varchar(15)       NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_scheda(
    pkid                 decimal(22, 0)    NOT NULL,
    fkid_soggetto        decimal(22, 0)    NULL,
    cod_scheda           varchar(20)       NULL,
    tipo_ana             int               NULL,
    motivo_iscrizione    varchar(80)       NULL,
    fkid_padre           decimal(22, 0)    NULL,
    cognome_padre        varchar(36)       NULL,
    nome_padre           varchar(36)       NULL,
    fkid_madre           decimal(22, 0)    NULL,
    cognome_madre        varchar(36)       NULL,
    nome_madre           varchar(36)       NULL,
    flg_certificato      int               NULL,
    flg_cittadinanza     int               NULL,
    id_ente              numeric(7, 0)     NULL,
    dt_ini               datetime          NULL,
    dt_fin               datetime          NULL,
    dt_mod               datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_soggetto(
    pkid                     decimal(22, 0)    NOT NULL,
    provenienza              int               NULL,
    cod_soggetto             varchar(20)       NULL,
    codice_fiscale           varchar(16)       NULL,
    flg_cf_calcolato         int               NULL,
    natura                   int               NULL,
    tipo_natura              int               NULL,
    partita_iva              varchar(11)       NULL,
    cognome                  varchar(36)       NULL,
    nome                     varchar(36)       NULL,
    altri_nomi               varchar(36)       NULL,
    denominazione            varchar(73)       NULL,
    dt_nascita               datetime          NULL,
    precisione_dt_nascita    int               NULL,
    sesso                    int               NULL,
    note                     varchar(1000)     NULL,
    fkid_comune_nascita      decimal(22, 0)    NULL,
    des_comune_nascita       varchar(80)       NULL,
    des_provincia_nascita    varchar(80)       NULL,
    fk_stato_nascita         decimal(22, 0)    NULL,
    des_stato_nascita        varchar(80)       NULL,
    fkid_localita_nascita    decimal(22, 0)    NULL,
    des_localita_nascita     varchar(80)       NULL,
    flg_stato                int               NULL,
    fkid_rappresentante      int               NULL,
    id_ente                  numeric(7, 0)     NULL,
    dt_ini                   datetime          NULL,
    dt_fin                   datetime          NULL,
    dt_mod                   datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_soggetto_ele(
    pkid                     decimal(22, 0)    NOT NULL,
    fkid_soggetto            decimal(22, 0)    NULL,
    cod_soggetto             varchar(20)       NULL,
    codice_fiscale           varchar(16)       NULL,
    cognome                  varchar(36)       NULL,
    nome                     varchar(36)       NULL,
    altri_nomi               varchar(36)       NULL,
    dt_nascita               datetime          NULL,
    precisione_dt_nascita    int               NULL,
    sesso                    int               NULL,
    note                     varchar(1000)     NULL,
    fkid_comune_nascita      decimal(22, 0)    NULL,
    des_comune_nascita       varchar(80)       NULL,
    des_provincia_nascita    varchar(80)       NULL,
    fkid_stato_nascita       decimal(22, 0)    NULL,
    des_stato_nascita        varchar(80)       NULL,
    fkid_localita_nascita    decimal(22, 0)    NULL,
    des_localita_nascita     varchar(80)       NULL,
    des_stato_civ            varchar(80)       NULL,
    coniuge                  varchar(80)       NULL,
    id_ente                  numeric(7, 0)     NULL,
    dt_ini                   datetime          NULL,
    dt_fin                   datetime          NULL,
    dt_mod                   datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_stciv(
    pkid              decimal(22, 0)    NOT NULL,
    fkid_soggetto     decimal(22, 0)    NULL,
    cod_stciv         varchar(20)       NULL,
    des_stciv         varchar(80)       NULL,
    dt_stciv          datetime          NULL,
    cf_coniuge        varchar(16)       NULL,
    fkid_coniuge      decimal(22, 0)    NULL,
    cognome_cg        varchar(36)       NULL,
    nome_cg           varchar(36)       NULL,
    fkid_comune       decimal(22, 0)    NULL,
    des_comune        varchar(80)       NULL,
    des_provincia     varchar(80)       NULL,
    fkid_stato        decimal(22, 0)    NULL,
    des_stato         varchar(80)       NULL,
    fkid_localita     decimal(22, 0)    NULL,
    des_localita      varchar(80)       NULL,
    fkid_consolato    decimal(22, 0)    NULL,
    des_consolato     varchar(80)       NULL,
    id_ente           numeric(7, 0)     NULL,
    dt_ini            datetime          NULL,
    dt_fin            datetime          NULL,
    dt_mod            datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ana_studio(
    pkid             decimal(22, 0)    NOT NULL,
    fkid_soggetto    decimal(22, 0)    NULL,
    anno             int               NULL,
    des_studio       varchar(80)       NULL,
    des_istituto     varchar(80)       NULL,
    fkid_comune      decimal(22, 0)    NULL,
    des_comune       varchar(80)       NULL,
    des_provincia    varchar(80)       NULL,
    fkid_stato       decimal(22, 0)    NULL,
    des_stato        varchar(80)       NULL,
    fkid_localita    decimal(22, 0)    NULL,
    des_localita     varchar(80)       NULL,
    id_ente          numeric(7, 0)     NULL,
    dt_ini           datetime          NULL,
    dt_fin           datetime          NULL,
    dt_mod           datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ESINEXTID(
    pkid                     decimal(22, 0)    NOT NULL,
    nome_tabella             varchar(255)      NOT NULL,
    incremento               decimal(7, 0)      DEFAULT 1 NOT NULL,
    next_id                  decimal(22, 0)    NOT NULL,
    dt_ultimo_popolamento    datetime          NULL,
    dt_ins                   datetime          NOT NULL,
    fkid_utente_ins          decimal(22, 0)    NOT NULL,
    fkid_proc_ins            decimal(22, 0)    NOT NULL,
    dt_mod                   datetime          NULL,
    fkid_utente_mod          decimal(22, 0)    NULL,
    fkid_proc_mod            decimal(22, 0)    NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go

CREATE TABLE tab_rappresentante(
    pkid             int               NOT NULL,
    des_relazione    varchar(80)       NULL,
    fkid_soggetto    decimal(22, 0)    NULL,
    id_ente          numeric(7, 0)     NULL,
    dt_ini           datetime          NULL,
    dt_fin           datetime          NULL,
    dt_mod           datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ter_accesso(
    pkid              int               NOT NULL,
    fkid_area         decimal(22, 0)    NULL,
    civico_num        int               NULL,
    civico_let        varchar(5)        NULL,
    fl_km             int               NULL,
    zona_rsu          varchar(4)        NULL,
    zona_osap         varchar(4)        NULL,
    sez_censimento    int               NULL,
    sez_elettorale    int               NULL,
    codice_acq        int               NULL,
    codice_gas        int               NULL,
    cap               varchar(10)       NULL,
    fkid_asl          decimal(22, 0)    NULL,
    fkid_scuola       decimal(22, 0)    NULL,
    fkid_cc           decimal(22, 0)    NULL,
    id_ente           numeric(7, 0)     NULL,
    dt_mod            datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ter_area(
    pkid              decimal(22, 0)    NOT NULL,
    cod_area          varchar(20)       NULL,
    cod_istat_area    int               NULL,
    specie            varchar(20)       NULL,
    des_area          varchar(80)       NULL,
    ordinamento       varchar(100)      NULL,
    id_ente           numeric(7, 0)     NULL,
    dt_ini            datetime          NULL,
    dt_fin            datetime          NULL,
    dt_mod            datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ter_centro_nascita(
    pkid           decimal(22, 0)    NOT NULL,
    cod_centro     varchar(20)       NULL,
    des_centro     varchar(80)       NULL,
    via_den        varchar(80)       NULL,
    civ_num        int               NULL,
    civ_let        varchar(5)        NULL,
    fkid_comune    decimal(22, 0)    NULL,
    id_ente        numeric(7, 0)     NULL,
    dt_mod         datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ter_cittadinanza(
    pkid                decimal(22, 0)    NOT NULL,
    cod_cittadinanza    varchar(20)       NULL,
    cod_istat           int               NULL,
    des_cittadinanza    varchar(80)       NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ter_comune(
    pkid              decimal(22, 0)    NOT NULL,
    cod_comune        varchar(20)       NULL,
    cod_istat         int               NULL,
    des_comune        varchar(80)       NULL,
    des_comune2       varchar(80)       NULL,
    fkid_provincia    decimal(22, 0)    NOT NULL,
    des_provincia     varchar(80)       NULL,
    cap_comune        varchar(10)       NULL,
    cod_belfiore      varchar(4)        NULL,
    cod_tribunale     int               NULL,
    cod_leva          int               NULL,
    dt_ini            datetime          NULL,
    dt_fin            datetime          NULL,
    fl_sto            int               NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ter_consolato(
    pkid             decimal(22, 0)    NOT NULL,
    cod_consolato    varchar(20)       NULL,
    des_consolato    varchar(80)       NULL,
    cod_istat        int               NULL,
    tipo             int               NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ter_ente(
    pkid           decimal(22, 0)    NOT NULL,
    cod_ente_bo    varchar(20)       NULL,
    des_ente       varchar(80)       NULL,
    via_deno       varchar(80)       NULL,
    num_civ        int               NULL,
    let_civ        varchar(5)        NULL,
    cap            varchar(10)       NULL,
    fkid_comune    decimal(22, 0)    NULL,
    e_mail         varchar(50)       NULL,
    id_ente        numeric(7, 0)     NULL,
    dt_mod         datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ter_localita(
    pkid              decimal(22, 0)    NOT NULL,
    cod_localita      varchar(20)       NULL,
    des_localita      varchar(80)       NULL,
    cap_localita      varchar(10)       NULL,
    fkid_stato        decimal(22, 0)    NULL,
    des_stato         varchar(80)       NULL,
    fkid_consolato    decimal(22, 0)    NULL,
    des_consolato     varchar(80)       NULL,
    id_ente           numeric(7, 0)     NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ter_provincia(
    pkid                   decimal(22, 0)    NOT NULL,
    cod_provincia          varchar(20)       NULL,
    des_provincia          varchar(80)       NULL,
    sigla                  varchar(2)        NULL,
    cod_istat_provincia    int               NULL,
    fkid_regione           decimal(22, 0)    NOT NULL,
    id_ente                numeric(7, 0)     NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ter_regione(
    pkid                 decimal(22, 0)    NOT NULL,
    cod_regione          varchar(20)       NULL,
    des_regione          varchar(80)       NULL,
    cod_istat_regione    int               NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ter_sez_ele(
    pkid                          decimal(22, 0)    NOT NULL,
    num_sezione                   int               NULL,
    denominazione                 varchar(38)       NULL,
    tipo_sezione                  int               NULL,
    des_via                       varchar(80)       NULL,
    num_civ                       int               NULL,
    let_civ                       varchar(4)        NULL,
    circ_parlamentoeuropeo        varchar(80)       NULL,
    collegio_parlamentoeuropeo    varchar(80)       NULL,
    circ_senato                   varchar(80)       NULL,
    collegio_senato               varchar(80)       NULL,
    circ_camera                   varchar(80)       NULL,
    collegio_camera               varchar(80)       NULL,
    circ_regionali                varchar(80)       NULL,
    collegio_regionali            varchar(80)       NULL,
    circ_provinciali              varchar(80)       NULL,
    collegio_provinciali          varchar(80)       NULL,
    circ_circoscrizione           varchar(80)       NULL,
    collegio_circoscrizionali     varchar(80)       NULL,
    id_ente                       numeric(7, 0)     NULL,
    dt_mod                        datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ter_stato(
    pkid                      decimal(22, 0)    NOT NULL,
    cod_stato                 varchar(20)       NULL,
    cod_istat                 int               NULL,
    des_stato                 varchar(80)       NULL,
    cod_catasto               varchar(4)        NULL,
    sigla                     varchar(3)        NULL,
    cod_leva                  int               NULL,
    fl_cee                    int               NULL,
    cod_istat_cittadinanza    int               NULL,
    des_cittadinanza_gen      varchar(80)       NULL,
    des_cittadinanza_mas      varchar(80)       NULL,
    des_cittadinanza_fem      varchar(80)       NULL,
    dt_ini                    datetime          NULL,
    dt_fin                    datetime          NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE TABLE ter_tribunale(
    pkid             decimal(22, 0)    NOT NULL,
    cod_tribunale    varchar(20)       NULL,
    des_tribunale    varchar(80)       NULL,
    PRIMARY KEY NONCLUSTERED (pkid)
) 
go


CREATE VIEW ESIIDENTE AS
SELECT ES.incremento id_ente
FROM ESINEXTID ES
WHERE pkid = 1
go

CREATE VIEW v_ana_soggetto_corrente AS
SELECT ana.pkid, ana.provenienza, ana.cod_soggetto, ana.codice_fiscale, ana.flg_cf_calcolato, ana.natura, ana.tipo_natura, ana.partita_iva, ana.cognome, ana.nome, ana.altri_nomi, ana.denominazione, ana.dt_nascita, ana.precisione_dt_nascita, ana.sesso, ana.note, ana.fkid_comune_nascita, ana.des_comune_nascita, ana.des_provincia_nascita, ana.fk_stato_nascita, ana.des_stato_nascita, ana.fkid_localita_nascita, ana.des_localita_nascita, ana.flg_stato, ana.fkid_rappresentante, ana.id_ente, ana.dt_ini, ana.dt_fin, ana.dt_mod
FROM ana_soggetto ana
WHERE ana.dt_fin is null and ana.dt_mod is not null
go

CREATE VIEW v_indirizzo_corrente AS
SELECT ana.pkid, ana.fkid_soggetto, ana.tipo, ana.fkid_area, ana.des_area, ana.num_civ, ana.let_civ, ana.colore, ana.corte, ana.scala, ana.interno, ana.piano, ana.edificio, ana.presso, ana.telefono, ana.fax, ana.email, ana.fkid_stato, ana.des_stato, ana.contea, ana.fkid_localita, ana.des_localita, ana.cap, ana.fkid_comune, ana.des_comune, ana.des_provincia, ana.fkid_consolato, ana.des_consolato, ana.id_ente, ana.dt_ini, ana.dt_fin, ana.dt_mod
FROM ana_indirizzo ana
WHERE ana.dt_fin is null and ana.dt_mod is not null
go


CREATE VIEW v_soggetto_indirizzo_corrente AS
SELECT ind.pkid, ind.fkid_soggetto, ind.tipo, ind.fkid_area, ind.des_area, ind.num_civ, ind.let_civ, ind.colore, ind.corte, ind.scala, ind.interno, ind.piano, ind.edificio, ind.presso, ind.telefono, ind.fax, ind.email, ind.fkid_stato, ind.des_stato, ind.contea, ind.fkid_localita, ind.des_localita, ind.cap, ind.fkid_comune, ind.des_comune, ind.des_provincia, ind.fkid_consolato, ind.des_consolato, ind.id_ente, ind.dt_ini residenza_ini, ind.dt_fin residenza_fin, ana.provenienza, ana.cod_soggetto, ana.codice_fiscale, ana.flg_cf_calcolato, ana.natura, ana.tipo_natura, ana.partita_iva, ana.cognome, ana.nome, ana.altri_nomi, ana.denominazione, ana.dt_nascita, ana.precisione_dt_nascita, ana.sesso, ana.note, ana.fkid_comune_nascita, ana.des_comune_nascita, ana.des_provincia_nascita, ana.fk_stato_nascita, ana.des_stato_nascita, ana.fkid_localita_nascita, ana.des_localita_nascita, ana.flg_stato, ana.fkid_rappresentante, ana.dt_ini, ana.dt_fin
FROM v_indirizzo_corrente ind, v_ana_soggetto_corrente ana
WHERE ana.pkid = ind.fkid_soggetto
go

CREATE INDEX idx_ana_atto ON ana_atto(id_ente)
go
CREATE INDEX idx_ana_atto_annota ON ana_atto_annota(id_ente)
go
CREATE INDEX idx_ana_cancellazione ON ana_cancellazione(id_ente)
go
CREATE INDEX idx_ana_carta_identita ON ana_carta_identita(id_ente)
go
CREATE INDEX idx_ana_cittadinanza ON ana_cittadinanza(id_ente)
go
CREATE INDEX idx_ana_ele ON ana_ele(id_ente)
go
CREATE INDEX idx_ana_fam ON ana_fam(id_ente)
go
CREATE INDEX idx_ana_fam_componente ON ana_fam_componente(id_ente)
go
CREATE INDEX idx_ana_indirizzo ON ana_indirizzo(id_ente)
go
CREATE INDEX idx_ana_iscrizione ON ana_iscrizione(id_ente)
go
CREATE INDEX idx_num_patente ON ana_patente(num_patente)
go
CREATE INDEX idx_ana_patente ON ana_patente(id_ente)
go
CREATE INDEX idx_num_pensione ON ana_pensione(num_pensione)
go
CREATE INDEX idx_ana_pensione ON ana_pensione(id_ente)
go
CREATE INDEX idx_ana_premesso_soggiorno ON ana_permesso_soggiorno(id_ente)
go
CREATE INDEX idx_ana_professione ON ana_professione(id_ente)
go
CREATE INDEX idx_ana_relazione ON ana_relazione(cod_relazione, sesso)
go
CREATE INDEX idx_ana_scheda ON ana_scheda(id_ente)
go
CREATE INDEX kcod_soggetto ON ana_soggetto(cod_soggetto)
go
CREATE INDEX idx_ana_soggetto ON ana_soggetto(id_ente)
go
CREATE INDEX idx_cod_fiscale ON ana_soggetto(codice_fiscale)
go
CREATE INDEX kcod_soggetto_ele ON ana_soggetto_ele(cod_soggetto)
go
CREATE INDEX idx_ana_soggetto_ele ON ana_soggetto_ele(id_ente)
go
CREATE INDEX idx_cod_fiscale_ele ON ana_soggetto_ele(codice_fiscale)
go
CREATE INDEX idx_ana_stciv ON ana_stciv(id_ente)
go
CREATE INDEX idx_ana_studio ON ana_studio(id_ente)
go
CREATE INDEX ESINEXTIDPK2 ON ESINEXTID(nome_tabella)
go
CREATE INDEX idx_tab_rappresentante ON tab_rappresentante(id_ente)
go
CREATE INDEX idx_ter_accesso ON ter_accesso(id_ente)
go
CREATE INDEX kcod_area ON ter_area(cod_area)
go
CREATE INDEX idx_ter_area ON ter_area(id_ente)
go
CREATE INDEX idx_ter_centro_nascita ON ter_centro_nascita(id_ente)
go
CREATE INDEX kcod_comune ON ter_comune(cod_comune)
go
CREATE INDEX kcod_consolato ON ter_consolato(cod_consolato)
go
CREATE INDEX idx_ter_ente ON ter_ente(id_ente)
go
CREATE INDEX kcod_localita ON ter_localita(cod_localita)
go
CREATE INDEX idx_ter_localita ON ter_localita(id_ente)
go
CREATE INDEX kcod_provincia ON ter_provincia(cod_provincia)
go
CREATE INDEX kcod_regione ON ter_regione(cod_regione)
go
CREATE INDEX idx_ter_sez_ele ON ter_sez_ele(id_ente)
go
CREATE INDEX kcod_stato ON ter_stato(cod_stato)
go
CREATE INDEX kcod_tribunale ON ter_tribunale(cod_tribunale)
go

ALTER TABLE ana_atto ADD CONSTRAINT Refter_tribunale230 
    FOREIGN KEY (fkid_tribunale)
    REFERENCES ter_tribunale(pkid)
go

ALTER TABLE ana_atto ADD CONSTRAINT Refana_soggetto103 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go

ALTER TABLE ana_atto ADD CONSTRAINT Refter_centro_nascita246 
    FOREIGN KEY (fkid_centro_nascita)
    REFERENCES ter_centro_nascita(pkid)
go

ALTER TABLE ana_atto ADD CONSTRAINT Refter_comune58 
    FOREIGN KEY (fkid_com_tras)
    REFERENCES ter_comune(pkid)
go

ALTER TABLE ana_atto ADD CONSTRAINT Refter_comune59 
    FOREIGN KEY (fkid_com_reg)
    REFERENCES ter_comune(pkid)
go


ALTER TABLE ana_atto_annota ADD CONSTRAINT Refana_atto302 
    FOREIGN KEY (fkid_atto)
    REFERENCES ana_atto(pkid)
go


ALTER TABLE ana_cancellazione ADD CONSTRAINT Refter_localita236 
    FOREIGN KEY (fkid_localita)
    REFERENCES ter_localita(pkid)
go

ALTER TABLE ana_cancellazione ADD CONSTRAINT Refana_soggetto182 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go

ALTER TABLE ana_cancellazione ADD CONSTRAINT Refter_comune183 
    FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid)
go

ALTER TABLE ana_cancellazione ADD CONSTRAINT Refter_stato184 
    FOREIGN KEY (fkid_stato)
    REFERENCES ter_stato(pkid)
go


ALTER TABLE ana_carta_identita ADD CONSTRAINT Refter_comune237 
    FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid)
go

ALTER TABLE ana_carta_identita ADD CONSTRAINT Refana_soggetto296 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go


ALTER TABLE ana_cittadinanza ADD CONSTRAINT Refana_soggetto116 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go

ALTER TABLE ana_cittadinanza ADD CONSTRAINT Refter_cittadinanza247 
    FOREIGN KEY (fkid_cittadinanza)
    REFERENCES ter_cittadinanza(pkid)
go


ALTER TABLE ana_ele ADD CONSTRAINT Refter_sez_ele222 
    FOREIGN KEY (fkid_sez_ele)
    REFERENCES ter_sez_ele(pkid) ON DELETE CASCADE
go

ALTER TABLE ana_ele ADD CONSTRAINT Refter_accesso109 
    FOREIGN KEY (fkid_accesso)
    REFERENCES ter_accesso(pkid)
go

ALTER TABLE ana_ele ADD CONSTRAINT Refana_soggetto176 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go


ALTER TABLE ana_fam_componente ADD CONSTRAINT Refana_fam201 
    FOREIGN KEY (fkid_fam)
    REFERENCES ana_fam(pkid) ON DELETE CASCADE
go

ALTER TABLE ana_fam_componente ADD CONSTRAINT Refana_soggetto202 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go

ALTER TABLE ana_fam_componente ADD CONSTRAINT Refana_relazione203 
    FOREIGN KEY (fkid_relazione)
    REFERENCES ana_relazione(pkid)
go


ALTER TABLE ana_indirizzo ADD CONSTRAINT Refter_consolato232 
    FOREIGN KEY (fkid_consolato)
    REFERENCES ter_consolato(pkid)
go

ALTER TABLE ana_indirizzo ADD CONSTRAINT Refana_soggetto233 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go

ALTER TABLE ana_indirizzo ADD CONSTRAINT Refter_localita108 
    FOREIGN KEY (fkid_localita)
    REFERENCES ter_localita(pkid)
go

ALTER TABLE ana_indirizzo ADD CONSTRAINT Refter_area238 
    FOREIGN KEY (fkid_area)
    REFERENCES ter_area(pkid) ON DELETE CASCADE
go

ALTER TABLE ana_indirizzo ADD CONSTRAINT Refter_comune15 
    FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid)
go

ALTER TABLE ana_indirizzo ADD CONSTRAINT Refter_stato16 
    FOREIGN KEY (fkid_stato)
    REFERENCES ter_stato(pkid)
go


ALTER TABLE ana_iscrizione ADD CONSTRAINT Refana_soggetto104 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go

ALTER TABLE ana_iscrizione ADD CONSTRAINT Refter_localita235 
    FOREIGN KEY (fkid_localita)
    REFERENCES ter_localita(pkid)
go

ALTER TABLE ana_iscrizione ADD CONSTRAINT Refter_comune36 
    FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid)
go

ALTER TABLE ana_iscrizione ADD CONSTRAINT Refter_stato37 
    FOREIGN KEY (fkid_stato)
    REFERENCES ter_stato(pkid)
go


ALTER TABLE ana_patente ADD CONSTRAINT Refana_soggetto215 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go


ALTER TABLE ana_pensione ADD CONSTRAINT Refana_soggetto291 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go


ALTER TABLE ana_permesso_soggiorno ADD CONSTRAINT Refana_soggetto239 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go

ALTER TABLE ana_permesso_soggiorno ADD CONSTRAINT Refter_comune240 
    FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid)
go


ALTER TABLE ana_professione ADD CONSTRAINT Refana_soggetto117 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go


ALTER TABLE ana_scheda ADD CONSTRAINT Refana_soggetto228 
    FOREIGN KEY (fkid_padre)
    REFERENCES ana_soggetto(pkid)
go

ALTER TABLE ana_scheda ADD CONSTRAINT Refana_soggetto229 
    FOREIGN KEY (fkid_madre)
    REFERENCES ana_soggetto(pkid)
go

ALTER TABLE ana_scheda ADD CONSTRAINT Refana_soggetto177 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go


ALTER TABLE ana_soggetto ADD CONSTRAINT Refter_localita147 
    FOREIGN KEY (fkid_localita_nascita)
    REFERENCES ter_localita(pkid)
go

ALTER TABLE ana_soggetto ADD CONSTRAINT Refter_comune38 
    FOREIGN KEY (fkid_comune_nascita)
    REFERENCES ter_comune(pkid)
go

ALTER TABLE ana_soggetto ADD CONSTRAINT Refter_stato39 
    FOREIGN KEY (fk_stato_nascita)
    REFERENCES ter_stato(pkid)
go

ALTER TABLE ana_soggetto ADD CONSTRAINT Reftab_rappresentante200 
    FOREIGN KEY (fkid_rappresentante)
    REFERENCES tab_rappresentante(pkid)
go


ALTER TABLE ana_soggetto_ele ADD CONSTRAINT Refter_comune310 
    FOREIGN KEY (fkid_comune_nascita)
    REFERENCES ter_comune(pkid)
go

ALTER TABLE ana_soggetto_ele ADD CONSTRAINT Refter_stato311 
    FOREIGN KEY (fkid_stato_nascita)
    REFERENCES ter_stato(pkid)
go

ALTER TABLE ana_soggetto_ele ADD CONSTRAINT Refana_soggetto312 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid)
go


ALTER TABLE ana_stciv ADD CONSTRAINT Refana_soggetto227 
    FOREIGN KEY (fkid_coniuge)
    REFERENCES ana_soggetto(pkid)
go

ALTER TABLE ana_stciv ADD CONSTRAINT Refter_localita110 
    FOREIGN KEY (fkid_localita)
    REFERENCES ter_localita(pkid)
go

ALTER TABLE ana_stciv ADD CONSTRAINT Refana_soggetto112 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go

ALTER TABLE ana_stciv ADD CONSTRAINT Refter_stato114 
    FOREIGN KEY (fkid_stato)
    REFERENCES ter_stato(pkid)
go

ALTER TABLE ana_stciv ADD CONSTRAINT Refter_comune24 
    FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid)
go

ALTER TABLE ana_stciv ADD CONSTRAINT Refter_consolato301 
    FOREIGN KEY (fkid_consolato)
    REFERENCES ter_consolato(pkid)
go


ALTER TABLE ana_studio ADD CONSTRAINT Refana_soggetto106 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go

ALTER TABLE ana_studio ADD CONSTRAINT Refter_comune292 
    FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid)
go

ALTER TABLE ana_studio ADD CONSTRAINT Refter_localita293 
    FOREIGN KEY (fkid_localita)
    REFERENCES ter_localita(pkid)
go

ALTER TABLE ana_studio ADD CONSTRAINT Refter_stato294 
    FOREIGN KEY (fkid_stato)
    REFERENCES ter_stato(pkid)
go

ALTER TABLE tab_rappresentante ADD CONSTRAINT Refana_soggetto199 
    FOREIGN KEY (fkid_soggetto)
    REFERENCES ana_soggetto(pkid) ON DELETE CASCADE
go


ALTER TABLE ter_accesso ADD CONSTRAINT Refter_area78 
    FOREIGN KEY (fkid_area)
    REFERENCES ter_area(pkid) ON DELETE CASCADE
go

ALTER TABLE ter_accesso ADD CONSTRAINT Refter_ente83 
    FOREIGN KEY (fkid_asl)
    REFERENCES ter_ente(pkid)
go

ALTER TABLE ter_accesso ADD CONSTRAINT Refter_ente84 
    FOREIGN KEY (fkid_scuola)
    REFERENCES ter_ente(pkid)
go

ALTER TABLE ter_accesso ADD CONSTRAINT Refter_ente85 
    FOREIGN KEY (fkid_cc)
    REFERENCES ter_ente(pkid)
go


ALTER TABLE ter_centro_nascita ADD CONSTRAINT Refter_comune93 
    FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid)
go


ALTER TABLE ter_comune ADD CONSTRAINT Refter_provincia13 
    FOREIGN KEY (fkid_provincia)
    REFERENCES ter_provincia(pkid)
go


ALTER TABLE ter_ente ADD CONSTRAINT Refter_comune92 
    FOREIGN KEY (fkid_comune)
    REFERENCES ter_comune(pkid)
go


ALTER TABLE ter_localita ADD CONSTRAINT Refter_consolato82 
    FOREIGN KEY (fkid_consolato)
    REFERENCES ter_consolato(pkid)
go

ALTER TABLE ter_localita ADD CONSTRAINT Refter_stato14 
    FOREIGN KEY (fkid_stato)
    REFERENCES ter_stato(pkid)
go


ALTER TABLE ter_provincia ADD CONSTRAINT Refter_regione12 
    FOREIGN KEY (fkid_regione)
    REFERENCES ter_regione(pkid)
go





