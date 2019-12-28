import {sendXmlHttpRequest} from "./http-requests.js";
import {getLocale} from "./fetch-functions.js";

window.showImages = function showImages() {
    const imageDiv = $("#image-container");
    const imageButton = $("#show-hide-images");
    if (imageDiv.html()) {
        if(imageButton[0].innerText==='Show images'){
            imageDiv.show();
            imageButton[0].innerText='Hide images';
        }else{
            imageDiv.hide();
            imageButton[0].innerText='Show images';
        }
        return;
    }
    imageButton.text('Hide images');
    const urlParts = window.location.pathname.split('/');
    const articleId = urlParts[urlParts.length - 1];
    sendXmlHttpRequest('GET', '/fetch/images/' + articleId).then(function (res) {
            if(res.length===0){
                imageDiv.append('<p>No images</p>');
            }
            console.log(imageDiv);
            res.forEach((image) => {
                const currentImageDiv = $('<div></div>');
                currentImageDiv.append(`<img src="${image.url}">`);
                const names = image.localImageNames;
                for (var key in names) {
                    console.log(key + " : " + names[key]);
                    currentImageDiv.append(`<div><span>${key}</span> ${names[key]}</div>`);
                }
                currentImageDiv.append(`<button class="btn btn-info" onclick="editImage(${image.id})">Edit</button>`)
                imageDiv.append(currentImageDiv);
            });
            console.log(res);
        }
    );
}

window.editImage = function (id) {
    console.log(id);
    const locale = getLocale(location.href);
    window.location = `${locale}admin/images/edit/${id}`;
}