function decodeSesso(s){
  if(s==1){
    document.write("M");
  }else
  if(s==2){
    document.write("F");
  }else{
    document.write("");
  }
}

function decodeBoolean(s){
  
  if(s==1){
    document.write("<font color='green'>Si</font>");
  }else
  if(s==0){
    document.write("<font color='red'>No</font>");
  }else{
    document.write("");
  }
}

function decodeNatura(s){
  if(s==0){
    document.write("Fisica");
  }else
  if(s==1){
    document.write("Giuridica");
  }else{
    document.write("Sconosciuta");
  }
}

function decodeImmobile(s){
  if(s=='F'){
    document.write("Fabbricato");
  }else
  if(s=='T'){
    document.write("Terreno");
  }

}

