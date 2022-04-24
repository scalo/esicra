package it.saga.siscotel.beans.albofornitori;

import it.saga.siscotel.beans.SiscotelToStringStyle;

import java.io.Serializable;

import java.math.*;

import org.apache.commons.lang.builder.ToStringBuilder;

public class FornitureBean implements Serializable
{ 
  //MANDATORY - chiave univoca
  private BigDecimal idForniture;
  
  //MANDATORY - descrizione della fornitura
  private String desFornitura;
  
  //MANDATORY -
  private String committente;

  private BigDecimal importo;
  
  //MANDATORY - 
  private Integer anno;
    
  private CategoriaBean categoria;

  public FornitureBean()
  {
  }
  
  public FornitureBean(BigDecimal idForniture, 
                       String desFornitura,
                       String committente,
                       BigDecimal importo,
                       Integer anno,
                       CategoriaBean categoria)
  {
    this.idForniture = idForniture;
    this.desFornitura = desFornitura;
    this.committente = committente;
    this.importo = importo;
    this.anno = anno;
    this.categoria = categoria;
  }
  
  public BigDecimal getIdForniture()
  {
    return this.idForniture;
  }
  public void setIdForniture(BigDecimal idForniture)
  {
    this.idForniture = idForniture;
  }

  public String getDesFornitura()
  {
    return this.desFornitura;
  }
  public void setDesFornitura(String desFornitura)
  {
    this.desFornitura = desFornitura;
  }

  public String getCommittente()
  {
    return this.committente;
  }
  public void setCommittente(String committente)
  {
    this.committente = committente;
  }

  public BigDecimal getImporto()
  {
    return this.importo;
  }
  public void setImporto(BigDecimal importo)
  {
    this.importo = importo;
  }

  public Integer getAnno()
  {
    return this.anno;
  }
  public void setAnno(Integer anno)
  {
    this.anno = anno;
  }
  
  public CategoriaBean getCategoria()
  {
    return this.categoria;
  }
  public void setCategoria(CategoriaBean categoria)
  {
    this.categoria = categoria;
  }
  
  public static FornitureBean test()
  {
    FornitureBean bean = new FornitureBean();
    bean.setAnno(new Integer(2005));
    bean.setCommittente("TEST COMMITTENTE");
    bean.setDesFornitura("TEST DES FORNITURA");
    bean.setIdForniture(new BigDecimal(1));
    bean.setImporto(new BigDecimal("10.6"));
    
    CategoriaBean categoria = new CategoriaBean();
    categoria.setDesCategoria("TEST CATEGORIA");
    categoria.setFlgEco(CategoriaBean.NON_ECOLOGICO);
    categoria.setIdCategoria(new BigDecimal(1));
    categoria.setIdEnte(new BigDecimal(8240));
    
    bean.setCategoria(categoria);
        
    return bean;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
  } 
}
