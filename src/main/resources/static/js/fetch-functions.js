import {sendXmlHttpRequest} from "./http-requests.js";

function fetchCategories(selectElement) {
    let urlParts = window.location.toString().split('/');
    let categoryId = urlParts[urlParts.length - 1];

    sendXmlHttpRequest('GET', '/fetch/categories/all').then(function (res) {
        res.forEach(function (category) {
            const isSelected = categoryId == category.id ? 'selected ' : '';
            selectElement.append('<option ' + isSelected + 'value="' + category.id + '">' + category.name + '</option>');
        })
    }).catch(function (error) {
        console.log(error);
    });
}

function getLocale(url){
            const regex = /^.*\/(fr|en|bg|es|de)\//g;
            const found = url.match(regex);
            return found[0];
}

export {fetchCategories, getLocale}