function sendXmlHttpRequest(method, url, data, token) {
    return new Promise(function (resolve, reject) {
        try {
            const xhttp = new XMLHttpRequest();
            xhttp.open(method, url, true);
            xhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            if(token){
                xhttp.setRequestHeader(token.header, token.content);
            }
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4) {
                    resolve(JSON.parse(this.response));
                }
            };
            xhttp.send(data);
        } catch (e) {
            reject(e);
        }
    });
}

function createJsonFromInputs(inputs) {
    const data = {};
    inputs.forEach(function (i) {
        data[i.name] = i.value;
    });
    return data;
}

export {sendXmlHttpRequest, createJsonFromInputs}