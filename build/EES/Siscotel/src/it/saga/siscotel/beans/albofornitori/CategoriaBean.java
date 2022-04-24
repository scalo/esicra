package it.saga.siscotel.beans.albofornitori;

import it.saga.siscotel.beans.SiscotelToStringStyle;

import java.io.Serializable;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;

public class CategoriaBean implements Serializable
{
  public static Integer ECOLOGICO = new Integer(1);  
  public static  Integer NON_ECOLOGICO = new Integer(0);
  
  //MANDATORY - chiiave univoca, per l'inserimento valorizzata a NULL
  private BigDecimal idCategoria;
  
  //MANDATORY - identificativo dell'ente
  private BigDecimal idEnte;
  
  //MANDATORY - identificativo ALBO
  private BigDecimal idAlbo;
  
  //Descrizione della categoria
  private String desCategoria;
  
  //Identifica se è una fornitura ecologica
  private Integer flgEco = NON_ECOLOGICO;

  
  public CategoriaBean()
  {
  
  }

  public CategoriaBean(BigDecimal idCategoria, 
                       BigDecimal idEnte,
                       BigDecimal idAlbo,
                       String desCategoria,
                       Integer flgEco)
  {
    this.idCategoria = idCategoria;
    this.idEnte = idEnte;
    this.idAlbo = idAlbo;
    this.desCategoria = desCategoria;
    this.flgEco = flgEco;
  }

  public BigDecimal getIdCategoria()
  {
    return this.idCategoria;
  }
  public void setIdCategoria(BigDecimal idCategoria )
  {
    this.idCategoria = idCategoria;
  }

  public BigDecimal getIdEnte()
  {
    return this.idEnte;
  }
  public void setIdEnte(BigDecimal idEnte )
  {
    this.idEnte = idEnte;
  }

  public BigDecimal getIdAlbo()
  {
    return this.idAlbo;
  }
  public void setIdAlbo(BigDecimal idAlbo )
  {
    this.idAlbo = idAlbo;
  }

  public String getDesCategoria()
  {
    return this.desCategoria;
  }
  public void setDesCategoria(String desCategoria )
  {
    this.desCategoria = desCategoria;
  }

  public Integer getFlgEco()
  {
    return this.flgEco;
  }
  public void setFlgEco(Integer flgEco )
  {
    this.flgEco = flgEco;
  }

  public static CategoriaBean test()
  {
    CategoriaBean bean = new CategoriaBean();
    bean.setDesCategoria("TEST DES CATEGORIA");
    bean.setFlgEco(bean.NON_ECOLOGICO);
    bean.setIdCategoria(new BigDecimal(1));
    bean.setIdEnte(new BigDecimal(8240));
    
    return bean;
  }

  public String toString()
  {
    return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
  }
}
