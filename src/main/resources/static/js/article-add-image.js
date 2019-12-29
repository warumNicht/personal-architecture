import {sendXmlHttpRequest} from "./http-requests.js";

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

        sendXmlHttpRequest('PUT', '/admin/articles/add-image/1', json).then(function (res) {
                console.log(res);
                window.location = res;
            }
        );
    };
});