import {sendXmlHttpRequest} from "./http-requests.js";
import {getLocale} from "./fetch-functions.js";

window.showImages = function showImages() {
    console.log('AAAAAAA');
    const imageDiv = $("#image-container");
    if(imageDiv.html()){
    return;
    }
    const urlParts=window.location.pathname.split('/');
    const articleId=urlParts[urlParts.length-1];
            sendXmlHttpRequest('GET', '/fetch/images/'+ articleId).then(function (res) {

                    console.log(imageDiv);
                    res.forEach((image)=> {
                           const currentImageDiv=$('<div></div>');
                           currentImageDiv.append(`<img src="${image.url}">`);
                           const names=image.localImageNames;
                          for (var key in names) {
                            console.log( key + " : " + names[key] );
                            currentImageDiv.append(`<div><span>${key}</span> ${names[key]}</div>`);
                          }

                          currentImageDiv.append(`<button class="btn btn-info" onclick="editImage(${image.id})">Edit</button>`)
                           imageDiv.append(currentImageDiv);
                        });
                    console.log(res);
                }
            );
}

window.editImage = function (id){
console.log(id);
const locale= getLocale(location.href);
window.location=`${locale}admin/images/edit/${id}`;
}

$(document).ready(function () {
//    const button = document.getElementById("submit-button");
//    button.onclick = function () {
//        const selectLang = document.getElementById('lang');
//        const lang = selectLang.options[selectLang.selectedIndex].value;
//        const data = {
//            id: article.id,
//            lang: lang,
//            image: {
//                url: document.getElementById('url').value,
//                name: document.getElementById('name').value
//            }
//        };
//        const json = JSON.stringify(data);
//
//        sendXmlHttpRequest('PUT', '/admin/articles/add-image', json).then(function (res) {
//                console.log(res);
//                window.location = res;
//            }
//        );
//    };
});