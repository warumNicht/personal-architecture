import {sendXmlHttpRequest, createJsonFromInputs} from "./http-requests.js";

$(document).ready(function () {
    const button = document.getElementById("submit-button");
    button.onclick = function () {
        const inputs = document.querySelectorAll('main input');
        console.log(inputs);
        const data = createJsonFromInputs(inputs);
        const json = JSON.stringify(data);
        
        sendXmlHttpRequest('PATCH', '/admin/articles/edit', json).then(function (res) {
                console.log(res);
                window.location = res;
            }
        );
    };
});