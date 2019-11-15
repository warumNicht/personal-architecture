import {sendXmlHttpRequest} from "./http-requests.js";

function fetchCategories(selectElement) {
    let urlParts = window.location.toString().split('/');
    let categoryId = urlParts[urlParts.length - 1];
    console.log(categoryId)

    sendXmlHttpRequest('GET', '/fetch/categories/all').then(function (res) {
        res.forEach(function (category) {
            const selected = categoryId == category.id ? 'selected ' : '';
            selectElement.append('<option ' + selected + 'value="' + category.id + '">' + category.name + '</option>');
        })
    }).catch(function (error) {
        console.log(error);
    });
}

export {fetchCategories}