function sendXmlHttpRequest(method, url, data, callbackFunction) {
    var xhttp = new XMLHttpRequest();
    xhttp.open(method, url, true);
    xhttp.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhttp.onreadystatechange= function(){
        if(this.readyState==4){
            callbackFunction(this);
        }
    };
    xhttp.send(data);
}

export {sendXmlHttpRequest}