import {sendXmlHttpRequest, createJsonFromInputs} from "./http-requests.js";

$(document).ready(()=>{
    const button=document.getElementById("submit-button");
    button.onclick=function () {
        const inputs=document.querySelectorAll('main input');
        const data = createJsonFromInputs(inputs);
        const json = JSON.stringify(data);

        function callback(request) {
            console.log(request.response);
            window.location=request.response;
        }
        sendXmlHttpRequest('PATCH', '/admin/articles/edit', json, callback);
    };
});