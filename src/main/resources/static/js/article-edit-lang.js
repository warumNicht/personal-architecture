import {sendXmlHttpRequest, createJsonFromInputs} from "./http-requests.js";

$(document).ready(()=>{
    const button=document.getElementById("submit-button");
    button.onclick=function () {
        const inputs=document.querySelectorAll('main input');
        const data = createJsonFromInputs(inputs);
        const json = JSON.stringify(data);

        sendXmlHttpRequest('PATCH', '/admin/articles/edit', json, (request)=>{
            console.log(request.response);
            window.location=request.response;
        });
    };

    function action() {

    }
});