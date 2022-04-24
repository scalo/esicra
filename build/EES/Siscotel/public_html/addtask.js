/*
  		FUNZIONI javascript utilizzate nel form per l'aggiunta di un task
  */
  
  function giorniSettimana(){
    var lista="";
    /*
    for(i=0;i<7;i++){
      if (document.forms[0].settimana[i].checked)
        lista+=i+" ";
    }
    */
    if (document.forms[0].lunedi.checked) 
      lista+=document.forms[0].lunedi.value;
    if (document.forms[0].martedi.checked) 
      lista+=document.forms[0].martedi.value;
    if (document.forms[0].mercoledi.checked) 
      lista+=document.forms[0].mercoledi.value;
    if (document.forms[0].giovedi.checked) 
      lista+=document.forms[0].giovedi.value;
    if (document.forms[0].venerdi.checked) 
      lista+=document.forms[0].venerdi.value;
    if (document.forms[0].sabato.checked) 
      lista+=document.forms[0].sabato.value;
    if (document.forms[0].domenica.checked) 
      lista+=document.forms[0].domenica.value;
    return lista;
  }

  function calcolaData(form){
    // new Date(anno,mese,giorno,ora,minuti,0).
    // NB IL mese javasctipt va da 0-11
    // recupera la data corrente per impostare il default
    var today = new Date();
    // imposta come valore predefinito 1/1/1070
    // la data corrente viene lasciata al server
    today.setTime(0);
    var anno=form.anno.value;
    anno=(anno=="")?today.getFullYear():anno;
    var mese=form.mese.value;
    mese = (mese=="")?today.getMonth():mese;
    var giorno=form.giorno.value;
    giorno=(giorno=="")?today.getDate():giorno;
    var ora=form.ora.value;
    ora=(ora=="")?today.getHours():ora;
    var minuti=form.minuti.value;
    minuti=(minuti=="")?today.getMinutes():minuti;
    //alert(anno+"\n"+mese+"\n"+giorno+"\n"+ora+"\n"+minuti+"\n");
    var data = new Date(
      anno,
      mese,
      giorno,
      ora,
      minuti,
      0
    );
    //alert("data"+data);
    return data.getTime();
  }
  
  /* 
   * Verifica il formato di una data
  */
  function checkDate(strdate){
     var rex = /^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/
       if(rex.test(strdate)){
          return true;
       }
     return false;
  }
  
  /* 
   * Calcola frequenza totale espressa in millisecondi
  */
  function calcolaFreqTot(){
     g = document.forms[0].fg.value;
     h = document.forms[0].fo.value;
     m = document.forms[0].fm.value;
    if (g == "") g=0;
    if (h == "") h=0;
    if (m == "") m=0;
    var parz = 0;
    if(g!=0) {
    	parz = 24*(parseInt(g));
    	//alert("ore:"+parz);
    }
    if(h!=0){ 
    	//alert(parseInt(parz)+parseInt(h));
       parz = 60* (parseInt(parz)+parseInt(h));
       //alert("Min:"+parz);
    }
    else if(h==0){
      parz = 60*parseInt(parz);
    }
    if(m!=0){ 
       parz = (parseInt(parz)+parseInt(m))*60;
       //alert("sec:"+parz);
    }
    else if(m==0){
      parz = 60*parseInt(parz);
    }
    parz = parz*1000;
    
    //var tot=((g*24+h)*60+m)*60*1000;  //millisecondi totali
    //alert("MS tot:"+parz);
    //alert( desFrequenza());
    return parz;
  }
  
  function desGiorniSettimana(str){
  	var descrizione=["Lunedì","Martedì","Mercoledi","Giovedì","Venerdì","Sabato","Domenica"];	
  	var res="";
  	for(i=0;i<str.length;i++){
  		n = str.charAt(i);
  		res += descrizione[eval(n)]+"\n";
  	}
  	return res;
  }

  function desFrequenza(){
    var g = document.forms[0].fg.value;
    var o = document.forms[0].fo.value;
    var m = document.forms[0].fm.value;
    var msg = "";
    if (g) msg += "giorni "+g+"\n";
    if (o) msg += "ore "+o+"\n";
    if (m) msg += "minuti "+m+"\n";
    return msg;
  }

  function errore(msg){
    alert(msg);
    return;
  }
  
  function validaForm(form){
	  /*	
    var str = "anno: "+document.forms[0].anno.value+"\n"+
      "mese: "+document.forms[0].mese.value+"\n"+
      "giorno: "+document.forms[0].giorno.value+"\n"+
      "ora: "+ document.forms[0].ora.value+"\n"+
      "minuti: "+document.forms[0].minuti.value+"\n"+
      "giorni settimana: "+giorniSettimana()+"\n"+
      "freq giorni: "+document.forms[0].fg.value+"\n"+
      "freq ore: "+document.forms[0].fo.value+"\n"+
      "freq min: "+document.forms[0].fm.value+"\n"+
      "Frequenza tot: "+ calcolaFreqTot() +" min";
    alert("PARAMETRI FORM \n"+str);
		*/
    // imposta campi nascosti
    form.operazione.value="inserisci";
    //alert(form.operazione.value);
    var dat =calcolaData(form);
    form.data_evento.value=dat;
    var giorni=giorniSettimana();
    form.giorni_settimana.value=giorni;
    // valida velocemente la frequenza
    var err ="Frequenza non impostata correttamente";
    /*
    var rex = new RegExp("^(d*)","g");
    if (!rex.exec(document.forms[0].fg.value)) {errore(err);return ;}
    if (!rex.exec(document.forms[0].fo.value)) {errore(err);return ;}
    if (!rex.exec(document.forms[0].fm.value)) {errore(err);return ;}
    */
    var freq = calcolaFreqTot();
    //alert("freq="+freq);
    if(!(freq>=0)){errore(err);return;}
    form.frequenza.value=freq;
    var ini =new Date(dat);
    var task = form.task.value;
    
    if ((task=="")||(!task)){
    	 alert("Tipo di task non specificato");
    	 return;
    }
    
    if ((task=="ExportPagamentiTask")&&(form.servizio_pagamento.value=="")){
    	 alert("Specificare il tipo di Pagamenti");
    	 return;
    }
    
    if ((task=="ExportPraticheTask")&&(form.servizio_pratica.value=="")){
    	 alert("Specificare il tipo di Pratiche");
    	 return;
    }
    
    var now = new Date(); 
    var diff = ini.getTime()-now.getTime();
    //alert("ini="+ini.getTime() +"\n now="+now.getTime()+"\n diff="+diff);
    if(diff<0 && ini.getTime()!=0){
    	alert("Non è possibile specificare una data precedente a quella attuale");
    	return;
    }
    
    if(!checkDate(form.data_ini.value)){
    	alert("La data iniziale di esportazione inserita: "+form.data_ini.value+"\n deve essere nel formato gg/mm/aaaa");
    	return;
    }
    
    if(!checkDate(form.data_fine.value)){
    	alert("La data finale di esportazione inserita: "+form.data_ini.value+"\n deve essere nel formato gg/mm/aaaa");
    	return;
    }
    
    var data_locale = (ini.getTime()!=0)?"\n in data "+ini.toLocaleString():"\n Per l'esecuzione immediata";
    //var data_locale = dat.toLocaleString();
    var msg = "Verrà aggiunto un task : "+task+data_locale+"\n";
    if(freq) msg += "con frequenza : \n " + desFrequenza();
    if(giorni && freq) msg += "per i giorni: \n"+desGiorniSettimana(giorni);
    if(confirm(msg))
    	form.submit();
    else
    	return;
  }
  
