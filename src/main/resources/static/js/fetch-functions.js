import {sendXmlHttpRequest} from "./http-requests.js";

function fetchCategories(selectElement) {

    sendXmlHttpRequest('GET', '/fetch/categories/all').then(function (res) {
        res.forEach(function (category) {
            selectElement.append('<option value="' + category.id + '">' + category.name + '</option>');
        })
    }).catch(function(error){
        console.log(error);
    });
}

export {fetchCategories}