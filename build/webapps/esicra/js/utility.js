/******************************************************************/
//Funzioni java script utilizzate dalle JSP
/******************************************************************/
/******************************************************************/
//Funzione isDate controlla l'esattezza formale della data inserita
/******************************************************************/
function controllaData(dtStr){
  // Declaring valid date character, minimum year and maximum year

  var dtCh= "/";
  var minYear=1900;
  var maxYear=2100;
  //alert("Sono in isDate");
  //alert("Data in form "+dtStr);
	var daysInMonth = DaysArray(12);
	var pos1=dtStr.indexOf(dtCh);
	var pos2=dtStr.indexOf(dtCh,pos1+1);
	var strDay=dtStr.substring(0,pos1);
	var strMonth=dtStr.substring(pos1+1,pos2);
	var strYear=dtStr.substring(pos2+1);
	strYr=strYear;
	if (strDay.charAt(0)=="0" && strDay.length>1) 
  {strDay=strDay.substring(1);}
	if (strMonth.charAt(0)=="0" && strMonth.length>1) 
  {strMonth=strMonth.substring(1);}
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) 
    {strYr=strYr.substring(1);}
	}
	month=parseInt(strMonth);
	day=parseInt(strDay);
	year=parseInt(strYr);
	if (pos1==-1 || pos2==-1){
		alert("Attenzione il formato della data non e' corretto. Utilizzare il formato : gg/mm/yyyy");
		return false;
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Attenzione il mese inserito non e' valido. Prego inserire un mese valido");
		return false;
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Attenzione il giorno inserito non e' valido. Prego inserire un giorno valido");
		return false;
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Prego inserire quattro cifre per l'anno. Anno accettato dal "+minYear+" al "+maxYear);
		return false;
	}
	/*if (dtStr.indexOf(dtlh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("Attenzione la data inserita non e' valida. Prego inserire una data valida");
		return false;
	}*/
return true;
}
/******************************************************************/
//Funzione daysInFebruary utilizata dalla funzioe isDate
/******************************************************************/
function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
/******************************************************************/
//Funzione DaysArray utilizata dalla funzioe isDate
/******************************************************************/
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31;
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}

/******************************************************************/
//Funzione che controlla che il carattere inserito sia un intero
/******************************************************************/
function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}
/******************************************************************/
/******************************************************************/
function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}
/*************************************************************************************************/
//funzione checkTxtIsInsert controlla che un campo di testo sia stato inserito perche obbligatorio
/*************************************************************************************************/
function checkTxtIsInsert(item)
{ alert("Controllo checkTxtIsInsert ");
  if(item.value.length!=0){
	  alert("Per me item.value.length = " +item.value.length);
    return true;}
  else{
      alert("Prego accertarsi di aver completato tutti i campi ");
      return false;
    }
}
/******************************************************************/
function controllaOra(Ora)
/******************************************************************/
{
 //alert("Attenzione lunghezza ora inserita " + Ora.length);	
  
  if ((Ora.length==4)||(Ora.length==5))
  {	
		if (Ora.length==4)
		{
			if (Ora.charAt(1)!=":")
			  {
				alert("Attenzione : Ora inserita nel formato errato 4");  
				
				return false;
			  };
		}
		if (Ora.length==5)
		{
			  if (Ora.charAt(2)!=":")
			  {
				alert("Attenzione: Ora inserita nel formato errato 5");  
				
				return false;
			  };
		}
	
	  var arrOra = Ora.split(":");
		  if (!(oraCoerente(parseInt(arrOra[0]),parseInt(arrOra[1]))))
		  {
			alert("Attenzione: Ora inserita errata.");  	
			return false;
		  }else{return true};

  }
	  else {alert("Attenzione: Ora inserita nel formato errato. Utilizzare il formato HH:MM"); 		
			return false;}
}

//funzione utilizzata da controllaOra
function oraCoerente(h,m)
{ if ((h>=1)&&(h<=24)&&(m>=0)&&(m<60))return true;
  else return false;
}
/******************************************************************/
/******************************************************************/
//Funzione che controlla se due date sono precedenti temporalmente
/******************************************************************/
function checkBeforeDate(dataInizio,dataFine){
	var dataInizioArr = dataInizio.split("/");
	var dataFineArr = dataFine.split("/");
	var AnnoIni = dataInizioArr[2];
	var MeseIni = dataInizioArr[1];
	var GiornoIni = dataInizioArr[0];
	var AnnoFine = dataFineArr[2];
	var MeseFine = dataFineArr[1];
	var GiornoFine = dataFineArr[0];
		/*
		alert("Giorno Inizio "+GiornoIni);
		alert("Mese Inizio "+MeseIni);
		alert("Anno Inizio "+AnnoIni);
		alert("Giorno Fine "+GiornoFine);
		alert("Mese Fine "+MeseFine);
		alert("Anno Fine "+AnnoFine);*/

		if(AnnoIni>AnnoFine){
			alert("Attenzione: controllare che la Data Inizio sia precedente alla Data Fine");
			return false;
		}
		if(AnnoIni==AnnoFine){
			//alert("Controllo Mesi perche AnnoIni==AnnoFine ");
			if(MeseIni>MeseFine){
				//alert("Attenzione MeseIni>MeseFine--->>>Return false");
				alert("Attenzione: controllare che la Data Inizio sia precedente alla Data Fine");
				return false;
				}
				
			if(MeseIni==MeseFine){
				//alert("Attenzione MeseIni=MeseFine--->>>Controllo Giorni");
					if(GiornoIni>GiornoFine){
						//alert("Controllo Giorni--->>>GiornoIni>>GiornoFine-->>Return false");
						alert("Attenzione: controllare che la Data Inizio sia precedente alla Data Fine");
						return false;
					}
					if(GiornoIni==GiornoFine){
						//alert("Controllo Giorni--->>>GiornoIni=GiornoFine-->>Return true");
						return true;
					}
					if(GiornoIni<GiornoFine){
						//alert("Controllo Giorni--->>>GiornoIni<<GiornoFine-->>Return true");
						return true;
					}
				}

			if(MeseIni<MeseFine){
				//alert("Attenzione MeseIni<MeseFine return true");
					return true;
		}//End if AnnoIni==AnnoFine*/

	}else{return true;}
}//End Function
/**************************************************************************************/
//funzione per controllare che un campo di testo sia stato inserito perche obbligatorio
/**************************************************************************************/
function checkLbxIsInsert(item) 
{ 
  if(item.options[item.selectedIndex].value!=0)
    return true;
  else
    return false;
}
/******************************************************************/
//Trim
/******************************************************************/
  function trim(valore) 
  {
     re=/\s+$|^\s+/g;
     var cString = valore.replace(re,"");
     return cString;
  }


  function ltrim(valore)
   {
     re=/^\s+/g;
     var cString = valore.replace(re,"");
     return cString;
   }
  
  function rtrim(valore)
   {
     re=/\s+$/g;
     var cString = valore.replace(re,"");
     return cString;
   }
  

/*************************************************************************
La funzione VisualizzaCampoId accende e spegne il CampoId in base al valore
che si passa alla funzione se il valore é :
- true accende il CampoId
- false spegne il CampoId

In seguito un esempio di utilizzo.

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<SCRIPT>

function VisualizzaCampoId(campo,valore)
{
	alert('Entro nella funzione');
	if(valore == true)
	{	
		alert('Accendo il div');
		document.getElementById(campoDiv).style.display="inline";
		

	}
	else
	{
  	alert('Spengo il div');
	document.getElementById(campoDiv).style.display="none";
	}
}
</SCRIPT>
</head>
<body>
<P>Campo1 <input type="text" id="Campo1" onblur="javascript:if(this.value!='') VisualizzaDiv('Div1',false); else VisualizzaDiv('Div1',true);return true;"> </P>
<div id="Div1">
<P>Campo2 <input type="text" id="Campo2"> </P>
<P>Campo3 <input type="text" id="Campo3"> </P>
</div>
<P>Campo4 <input type="text" id="Campo3"> </P>
</body>
</html>


*************************************************************************/


function VisualizzaCampoId(campo,valore)
{
	if(valore == true)
	{	
		document.getElementById(campo).style.display="inline";
	}
	else
	{
	document.getElementById(campo).style.display="none";
	}
}

/*****************************************************************
Description:      Funzione per il controllo validita' EMAIL
Return:             boolean true - email corretta
*****************************************************************/
function AddressVerify(szAddress)
{
	//controlli sul carattere '@'
	if ( -1 == szAddress.indexOf("@"))
		{
		alert("Attenzione, l'indirizzo di email deve contenere il carattere @");
		return false;
		}
	if (0 == szAddress.indexOf("@"))
		{
		alert("Attenzione, l'indirizzo di email non puo' contenere '@' come primo carattere");
		return false;
		}
	if ((szAddress.length-1) == szAddress.lastIndexOf("@"))
		{
		alert("Attenzione, l'indirizzo di email non puo' contenere '@' come ultimo carattere");
		return false;
		}
	var aAtCount = new Array();
	aAtCount = szAddress.split("@");
	if (aAtCount.length>=3)
		{
		alert("Attenzione, l'indirizzo di email non puo' contenere piu' di un'occorrenza del carattere '@'");
		return false;
		}
		
	//controlli sul carattere '.'		
	if ( -1 == szAddress.indexOf("."))
		{
		alert("Attenzione, l'indirizzo di email deve contenere il carattere '.'");
		return false;
		}
	if (0 == szAddress.indexOf("."))
		{
		alert("Attenzione, l'indirizzo di email non puo' contenere '.' come primo carattere");
		return false;
		}	
	if ((szAddress.length-1) == szAddress.lastIndexOf("."))
		{
		alert("Attenzione, l'indirizzo di email non puo' contenere '.' come ultimo carattere");
		return false;
		}
	
	//controlli su posizione @ e .
	var szSubAddress;
	szSubAddress=szAddress.substr(szAddress.lastIndexOf("@") + 1);
	
	if (-1 == szSubAddress.indexOf("."))
		{
		alert("Attenzione, l'indirizzo di email deve contenere il carattere . dopo il carattere @");
		return false;
		}		
	if (0 == szSubAddress.indexOf("."))
		{
		alert("Attenzione, l'indirizzo di email non puo' contenere il carattere . dopo il carattere @");
		return false;
		}				

	//controlli su caratteri validi
	if (!IsStringValid(szAddress, "@._-abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"))		
		{
		alert("Attenzione, nell'indirizzo email non sono consentiti caratteri speciali o spazi vuoti! (Sono consentiti @, ., _ )");
		return false;
		}
	
	return true;
}


/******************************************************************/
//Funzione controlla l'esattezza formale di un campo money
/******************************************************************/
function controllaMoney(mStr){
  var mCh= ".";
  var pos1=mStr.indexOf(mCh);
  var s1 = trim(mStr.substr(0,pos1));
  var s2 = trim(mStr.substr(pos1+1));

  if (s1.length < 1 || s2.length != 2) {
     alert("Attenzione il formato del campo Importo non è valido. Utilizzare il formato : nnnnnnn.nn");
	 return false;
  }

  if (isInteger(s1) && isInteger(s2)) {
      return true;
    } else {
         alert("Attenzione il formato del campo Importo non è valido. Utilizzare il formato : nnnnnnn.nn");
        alert (s1+"-"+s2);
         return false;
  }
  return true;  
}


function normalizzaMoney(mStr) {
    mStr=mStr.replace(",","."); 
    var mCh= ".";
    var pos1=mStr.indexOf(mCh);
    var s1 = trim(mStr.substr(0,pos1));
    var s2 = trim(mStr.substr(pos1+1));
    if ((pos1 == -1 ) && (isInteger(mStr)) ) {
         mStr=mStr+".00";
    }
    if ((pos1 > 0) && (s2.length != 2) && (isInteger(s2))) {
        var s3 = s2+"00";
        s3 = s3.substr(0,2);
        mStr = s1+"."+s3;
    }
    return mStr;
    }

function normalizzaData(mStr) {
    mStr=mStr.replace(/-/g,"/"); 
 
    return mStr;
    }
