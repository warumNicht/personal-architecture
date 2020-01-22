import {sendXmlHttpRequest} from "./http-requests.js";
import {showFieldErrors, removeOldErrors} from "./functions.js";

$(document).ready(function () {
    const button = document.getElementById("submit-button");
    button.onclick = function () {
        const selectLang = document.getElementById('lang');
        const lang = selectLang.options[selectLang.selectedIndex].value;
        const data = {
            id: article.id,
            lang: lang,
            image: {
                url: document.getElementById('url').value,
                name: document.getElementById('name').value
            }
        };
        const json = JSON.stringify(data);

        sendXmlHttpRequest('PUT', location.href, json).then(function (res) {

                if (typeof (res) === 'string') {
                    window.location = res;
                } else {
                    removeOldErrors(['image.url', 'image.name']);
                    showFieldErrors(res)
                }
            }
        );
    };
});

//function showFieldErrors(errorMap){
//    for (const [key, value] of Object.entries(errorMap)) {
//        const currentFieldDiv = $(`#${key.replace('.','\\.')}Div`);
//        console.log(`#${key}Div`);
//        console.log(currentFieldDiv);
//        currentFieldDiv.addClass('text-danger');
//        const currentSmall = $('<small></small>').addClass('text-danger');
//
//        value.forEach((v)=>{
//            currentSmall.append(`${v}<br>`)
//        })
//        currentFieldDiv.append(currentSmall);
//     }
//}
//
//function removeOldErrors(){
//    const divFieldsIds=['image.url', 'image.name'];
//    divFieldsIds.forEach((name)=>{
//        $(`#${name}Div`).removeClass('text-danger');
//    })
//    $('small').remove('.text-danger');
//}