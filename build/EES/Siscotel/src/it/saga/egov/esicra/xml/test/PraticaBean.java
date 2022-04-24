package it.saga.egov.esicra.xml.test;

import java.math.BigDecimal;
import java.util.Date;
import it.saga.egov.esicra.xml.test.RecapitoBean;

public class PraticaBean  {

  private BigDecimal idPratica;
  private Date dataPratica;
  private String oggetto;
  private String tipoNotifica;
  private Integer codTipoConsegna;
  private String desTipoConsegna;
  private Integer codModalitaAllegato;
  private String desModalitaAllegato;
  private Integer canale;
  //private String desCanale;
  //private BigDecimal idEnte;
  private BigDecimal idServizio;
  private RecapitoBean recapito;
  private AllegatoBean[] listaAllegato;
  private String codPraticaBo;
  private BigDecimal idEnteDestinatario;
  private PagamentoBean[] pagamenti;

  /* Decodifica Canali */
  private static String CANALI[] ={"Portale","Call Center","Chiosco","Wireless"};
  
  public PraticaBean(){
    this.recapito = new RecapitoBean(); 
  }
    
  /**
   * Non usare.
   * @deprecated
   */
  public PraticaBean( BigDecimal idPratica,
                      Date dataPratica,
                      String oggetto,
                      String tipoNotifica,
                      Integer codTipoConsegna,
                      String desTipoConsegna,
                      Integer codModalitaAllegato,
                      String desModalitaAllegato,
                      Integer canale,
                      /*String desCanale,*/
                      //Integer stato,
                      BigDecimal newIdEnteDestinatario,
                      BigDecimal newIdServizio,
                      RecapitoBean newRecapito,
                      AllegatoBean[] newListaAllegato,
                      String newCodPraticaBo
                      ) {
    this.idPratica=idPratica;
    this.dataPratica=dataPratica;
    this.oggetto=oggetto;
    this.tipoNotifica=tipoNotifica;
    this.codTipoConsegna=codTipoConsegna;
    this.desTipoConsegna=desTipoConsegna;
    this.codModalitaAllegato=codModalitaAllegato;
    this.desModalitaAllegato=desModalitaAllegato;
    this.canale=canale;
    /*this.desCanale=desCanale;*/
    //this.stato=stato;
    this.idEnteDestinatario=newIdEnteDestinatario;
    this.idServizio=newIdServizio;
    this.recapito=newRecapito;
    this.listaAllegato=newListaAllegato;
    this.codPraticaBo=newCodPraticaBo;
  }
  
  public PraticaBean( BigDecimal idPratica,
                      Date dataPratica,
                      String oggetto,
                      String tipoNotifica,
                      Integer codTipoConsegna,
                      String desTipoConsegna,
                      Integer codModalitaAllegato,
                      String desModalitaAllegato,
                      Integer canale,
                      /*String desCanale,*/
                      //Integer stato,
                      BigDecimal newIdEnteDestinatario,
                      BigDecimal newIdServizio,
                      RecapitoBean newRecapito,
                      AllegatoBean[] newListaAllegato,
                      String newCodPraticaBo,
                      PagamentoBean[] pagamenti
                      ) {
    this.idPratica=idPratica;
    this.dataPratica=dataPratica;
    this.oggetto=oggetto;
    this.tipoNotifica=tipoNotifica;
    this.codTipoConsegna=codTipoConsegna;
    this.desTipoConsegna=desTipoConsegna;
    this.codModalitaAllegato=codModalitaAllegato;
    this.desModalitaAllegato=desModalitaAllegato;
    this.canale=canale;
    /*this.desCanale=desCanale;*/
    //this.stato=stato;
    this.idEnteDestinatario=newIdEnteDestinatario;
    this.idServizio=newIdServizio;
    this.recapito=newRecapito;
    this.listaAllegato=newListaAllegato;
    this.codPraticaBo=newCodPraticaBo;
    this.pagamenti = pagamenti;
  }

  public void setIdPratica(BigDecimal idPratica){
    this.idPratica=idPratica;
  }

  public BigDecimal getIdPratica(){
    return this.idPratica;
  }

  public void setDataPratica(Date dataPratica){
    this.dataPratica=dataPratica;
  }

  public Date getDataPratica(){
    return this.dataPratica;
  }

  public void setOggetto(String oggetto){
    this.oggetto=oggetto;
  }

  public String getOggetto(){
    return this.oggetto;
  }

  public void setTipoNotifica(String tipoNotifica){
    this.tipoNotifica=tipoNotifica;
  }

  public String getTipoNotifica(){
    return this.tipoNotifica;
  }

  public void setCodTipoConsegna(Integer codTipoConsegna){
    this.codTipoConsegna=codTipoConsegna;
  }

  public Integer getCodTipoConsegna(){
    return this.codTipoConsegna;
  }
  
  public void setDesTipoConsegna(String desTipoConsegna){
    this.desTipoConsegna=desTipoConsegna;
  }

  public String getDesTipoConsegna(){
    return this.desTipoConsegna;
  }

  public void setCodModalitaAllegato(Integer codModalitaAllegato){
    this.codModalitaAllegato=codModalitaAllegato;
  }

  public Integer getCodModalitaAllegato(){
    return this.codModalitaAllegato;
  }

  public void setDesModalitaAllegato(String desdModalitaAllegato){
    this.desModalitaAllegato=desdModalitaAllegato;
  }

  public String getDesModalitaAllegato(){
    return this.desModalitaAllegato;
  }

  public void setCanale(Integer canale){
    this.canale=canale;
  }

  public Integer getCanale(){
    return this.canale;
  }
/*
  public void setDesCanale(String desCanale){
    this.desCanale=desCanale;
  }

  public String getDesCanale(){
    return this.desCanale;
  }
*/
/*

  public void setIdEnte(BigDecimal newIdEnte){
    this.idEnte=newIdEnte;
  }

  public BigDecimal getIdEnte(){
    return idEnte;
  }
*/

  public void setIdServizio(BigDecimal newIdServizio){
    this.idServizio=newIdServizio;
  }

  public BigDecimal getIdServizio(){
    return this.idServizio;
  }
  
  public RecapitoBean getRecapito() {
    return recapito;
  }

  public void setRecapito(RecapitoBean newRecapito) {
    recapito = newRecapito;
  }


  public AllegatoBean[] getListaAllegato() {
    return listaAllegato;
  }

  public void setListaAllegato(AllegatoBean[] newListaAllegato) {
    listaAllegato = newListaAllegato;
  }
  
  public String getCodPraticaBo() {
    return codPraticaBo;
  }

  public void setCodPraticaBo(String newCodPraticaBo) {
    codPraticaBo = newCodPraticaBo;
  }

  public BigDecimal getIdEnteDestinatario() {
    return idEnteDestinatario;
  }

  public void setIdEnteDestinatario(BigDecimal newIdEnteDestinatario) {
    idEnteDestinatario = newIdEnteDestinatario;
  }

  public static String decDesCanale(Integer integ){
    int i = integ.intValue();
    if((i>0)&&(i<CANALI.length)){
      return CANALI[i];
    }
    return "";
  }




  
  public String toString(){
    StringBuffer sb = new StringBuffer();
    sb.append("[");
    sb.append("IdPratica = "+idPratica+", ");
    sb.append("DataPratica = "+dataPratica+", ");
    sb.append("Oggetto = "+oggetto+", ");
    sb.append("TipoNotifica = "+tipoNotifica+", ");
    sb.append("CodTipoConsegna = "+codTipoConsegna+", ");
    sb.append("DesTipoConsegna = "+desTipoConsegna+", ");
    sb.append("CodModalitaAllegato = "+codModalitaAllegato+", ");
    sb.append("DesModalitaAllegato = "+desModalitaAllegato+", ");

    if( this.canale!=null ){
    sb.append("Canale = "+canale+", ");}
        else{sb.append("Canale = null , ");}
      
    //sb.append("DesCanale = "+decDesCanale(canale)+", ");
    //sb.append("Stato="+stato+", ");
    sb.append("IdEnteDestinatario = "+idEnteDestinatario+", ");
    sb.append("IdServizio = "+idServizio+", ");
    sb.append("Recapito = "+recapito+", ");
    sb.append("Allegati = ");
    sb.append("{ ");
    for(int i=0;i<((listaAllegato!=null)?listaAllegato.length:-1);i++){
      sb.append(listaAllegato[i]);
    };
    sb.append(" }");
    sb.append("CodPraticaBo = "+codPraticaBo);
    sb.append("]");
    sb.toString();
    return sb.toString();
  }

  public static PraticaBean test(){
    PraticaBean b = new PraticaBean(
      new BigDecimal("101"),
      new Date(System.currentTimeMillis()),
      "",
      "Tipo notifica",
      new Integer(1),
      "TipoConsegna",
      new Integer(2),
      "Modalita Allegato",
      new Integer(1),
      /*"Des Canale",*/
      //new Integer(666),
      new BigDecimal("7734"), //ente destianatario
      new BigDecimal(1),
      RecapitoBean.test4(),
      new AllegatoBean[]{AllegatoBean.test(),AllegatoBean.test2()},
      "TEST BEAN"
    );
    return b;
  }
  
  public static void main(String[] args){
    
    System.out.println(PraticaBean.test());
  }


  public void setPagamenti(PagamentoBean[] pagamenti)
  {
    this.pagamenti = pagamenti;
  }


  public PagamentoBean[] getPagamenti()
  {
    return pagamenti;
  }

}