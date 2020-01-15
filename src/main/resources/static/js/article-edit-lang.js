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
                if (typeof (res) === 'string') {
                    window.location = res;
                }else{
                    removeOldErrors();
                    showFieldErrors(res)
                }
            }
        );
    };
});

function showFieldErrors(errorMap){
    for (const [key, value] of Object.entries(errorMap)) {
        const currentFieldDiv = $(`#${key}Div`);
        currentFieldDiv.addClass('text-danger');
        const currentSmall = $('<small></small>').addClass('text-danger');

        value.forEach((v)=>{
            currentSmall.append(`${v}<br>`)
        })
        currentFieldDiv.append(currentSmall);
     }
}

function removeOldErrors(){
    const divFieldsIds=['title', 'mainImageName', 'content'];
    divFieldsIds.forEach((name)=>{
        $(`#${name}Div`).removeClass('text-danger');
    })
    $('small').remove('.text-danger');
}
