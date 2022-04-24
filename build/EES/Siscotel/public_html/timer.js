  /*
  		FUNZIONI javascript utilizzate nel form principale del timer

  ! ATTENZIONE ! il nome dell'applicazione è cablato nella funzione aggiungi()
  
  */
  
	function esci(){
    if (confirm("Uscire applicazione")){
      // logout pulito
      // TODO vedere javascript per chiudere finestra top
    }
  }

  // solo per debug
  
  function aggiungiTest(form){
    form.operazione.value="aggiungi_test";
    form.submit();
  }
  
  function aggiungi(){
    //alert("aggiungi un task");
    //location.href="http://10.77.1.152:8988/timer/EesAddTask.jsp";
    location.pathname="esicra/EesAddTask.jsp";
  }

  function modifica(){
    alert("modifica un task");
    //location.pathname="EesAddTask.jsp";
  }

  function selezionato(form){
    //cerca il selezionato
    //alert(form.selezione.length);
    for(i=0;i<=form.selezione.length;i++){
      if(form.selezione[i].checked){
        //alert("i="+i);
        return form.selezione[i].value;
      }
    }
    // caso particolare se cè solo 1 elemento !!!
    if(form.selezione.checked){
    	//alert("checked");
    	return form.selezione.value;
    }
    
    return -1;
  }
    
  function cancella(form){
    form.operazione.value="cancella";
    id=selezionato(form);
    //alert("id: "+id);
    if(id!=-1){  
      form.id.value=id;
      if (confirm('cancellare task '+id+"?" )){
        form.submit();
      }
    }else{
      alert('Nessun elemento selezionato');
    }
  }

  function rimuoviTutti(form){
    form.operazione.value="rimuovi_tutti";
    if(confirm("Rimuovere TUTTI task ? \n"+ form.operazione.value))
      if(confirm("Sei veramente sicuro ?"))
        form.submit(); 
  }

  function rimuoviCompletati(form){
    form.operazione.value="rimuovi_completati";
    if(confirm("Rimuovere i tasks completati ? \n")){
      form.submit();
    }
  }
  
  function status(stato){
    var res="";
    if(stato) img="up.gif"
    else img="down.gif";
    res="<img src=\" "+img+ "\"/>";
    //alert("status \n"+ res);
    return res;
  }