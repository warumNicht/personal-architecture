import {sendXmlHttpRequest} from "./http-requests.js";

$(document).ready(()=>{
    const button=document.getElementById("submit-button");
    button.onclick=function () {
        const inputs=document.querySelectorAll('main input');
        var data = {};
        inputs.forEach(i=>{
            data[i.name]=i.value;
        });

        var json = JSON.stringify(data);

        sendXmlHttpRequest('PATCH', '/en/admin/articles/edit', json, (request)=>{
            console.log(request.response);
            window.location=request.response;
        });
    };

    function action() {

    }
});