function sendXmlHttpRequest(method, url, data, callbackFunction) {
    const xhttp = new XMLHttpRequest();
    xhttp.open(method, url, true);
    xhttp.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhttp.onreadystatechange= function(){
        if(this.readyState==4){
            callbackFunction(this);
        }
    };
    xhttp.send(data);
}

function createJsonFromInputs(inputs) {
    const data = {};
    inputs.forEach(i=>{
        data[i.name]=i.value;
    });
    return data;
}

export {sendXmlHttpRequest, createJsonFromInputs}