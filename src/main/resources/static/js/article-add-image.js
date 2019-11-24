import {sendXmlHttpRequest, createJsonFromInputs} from "./http-requests.js";
/*<![CDATA[*/
const art = [[${article}]];  //Make sure this is bills and not bill as you have done it.
console.log(art);
/*]]>*/

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