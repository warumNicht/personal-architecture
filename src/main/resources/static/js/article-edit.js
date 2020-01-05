import {sendXmlHttpRequest} from "./http-requests.js";
import {getLocale} from "./fetch-functions.js";

window.getArticleId = function () {
    const urlParts = window.location.pathname.split('/');
    return urlParts[urlParts.length - 1];
};

$(document).ready(function () {
    const catChangeButton = $('#categoryChange');

    catChangeButton.click(function () {
        const selectedCatId = $('#catSelect option:selected').val();
        console.log(selectedCatId);

        sendXmlHttpRequest('PATCH', `/admin/articles/change-category/${getArticleId()}`, selectedCatId).then(res => {
            const responseDiv = $('#message');
            responseDiv.append(`Category for article: ${res.title} successfully changed from ${res.oldCategoryName} to ${res.newCategoryName}!`);
            catChangeButton.attr('disabled', true);
            setTimeout(function () {
                responseDiv.empty();
            }, 4000);
        });
    })


});


window.showImages = function showImages() {
    const imageDiv = $("#image-container");
    const imageButton = $("#show-hide-images");
    if (imageDiv.html()) {
        if (imageButton[0].innerText === 'Show images') {
            imageDiv.show();
            imageButton[0].innerText = 'Hide images';
        } else {
            imageDiv.hide();
            imageButton[0].innerText = 'Show images';
        }
        return;
    }
    imageButton.text('Hide images');
    const urlParts = window.location.pathname.split('/');
    const articleId = getArticleId();
    sendXmlHttpRequest('GET', '/fetch/images/' + articleId).then(function (res) {
            if (res.length === 0) {
                imageDiv.append('<p>No images</p>');
            }
            res.forEach((image) => {
                const currentImageDiv = $('<div></div>');
                currentImageDiv.append(`<img src="${image.url}">`);
                const names = image.localImageNames;
                for (var key in names) {
                    currentImageDiv.append(`<div><span>${key}</span> ${names[key]}</div>`);
                }
                currentImageDiv.append(`<button class="btn btn-info" onclick="editImage(${image.id})">Edit</button>`)
                imageDiv.append(currentImageDiv);
            });
        }
    );
};

window.editImage = function (id) {
    const locale = getLocale(location.href);
    window.location = `${locale}admin/images/edit/${id}`;
};

window.showAllCategories = function () {
    const categoriesContainer = $('div.article-container');
    const categoriesSelect = $('#catSelect');
    const categoriesOptions = $('#select-categories option');

    categoriesOptions.each(function (index) {
        const selectedOption = $('#catSelect option:selected');
        const selectedOptionId = selectedOption.val();
        if (index != 0) {
            const value = $(this).val();
            const innerText = $(this).text();

            if (selectedOptionId === value) {
                selectedOption.text(innerText);
            } else {
                categoriesSelect.append(`<option value="${value}">${innerText}</option>`);
            }
        }
    });
    categoriesSelect.change(function () {
        $('#categoryChange').removeAttr('disabled');
    });
};

