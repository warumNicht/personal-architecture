function sendXmlHttpRequest(method, url, data, callbackFunction) {
    return new Promise(function (resolve, reject) {
        try {
            const xhttp = new XMLHttpRequest();
            xhttp.open(method, url, true);
            xhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4) {
                    console.log(this.response);
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