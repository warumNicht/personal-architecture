import {sendXmlHttpRequest} from "./http-requests.js";

function fetchCategories(selectElement) {
    // function callback(request, selectElement) {
    //
    //     console.log(request.response);
    //     const data =request.response;
    //     // data.forEach((category) => {
    //     //     console.log(category);
    //         selectElement.append('<option value="' + '">' + 'darhbytq354' + '</option>');
    //     // });
    // }
    const xhttp = new XMLHttpRequest();
    xhttp.open('GET', '/fetch/categories/all', true);
    xhttp.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhttp.onreadystatechange= function(){
        if(this.readyState==4){
            selectElement.append('<option value="' + '">' + 'darhbytq354' + '</option>');
        }
    };
    xhttp.send(null);
    // sendXmlHttpRequest('GET', '/fetch/categories/all', selectElement ,callback)
    // fetch('/fetch/categories/all')
    //     .then((response) => response.json())
    //     .then((json) => {
    //         json.forEach((category) => {
    //             selectElement.append('<option value="'+ category.id +'">'+ category.name +'</option>');
    //         });
    //     })
    //     .catch((err) => console.log(err));
}

export {fetchCategories}