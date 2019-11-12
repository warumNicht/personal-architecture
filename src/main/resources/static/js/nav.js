
import {fetchCategories} from "./fetch-functions.js";


$(document).ready(function () {
    console.log('aaaaaaaaaaaaaaaaaaaaaaaaaa');
    $("#select-locales").change(function () {
        let selectedOption = $('#select-locales').val();
        if (selectedOption != '') {
            location.replace('?lang=' + selectedOption);
        }
    });
    const select = $('#select-categories');
    fetchCategories(select);
    select.change(function () {
        let selectedOption = select.val();
        if (selectedOption != '') {
            const url = location.href;
            const regex = /^.*\/(fr|en|bg|es|de)\//g;
            const found = url.match(regex);
            location.href = found[0] + 'projects/category/'+ selectedOption;
        }
    });
});

function addCategories(select) {
    const xhttp = new XMLHttpRequest();
    xhttp.open('GET', '/fetch/categories/all', true);
    xhttp.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhttp.onreadystatechange= function(){
        if(this.readyState==4){
            const data=JSON.parse(this.response);
            console.log(data);
            data.forEach(function(c){
                console.log(c);
                select.append('<option value="'+ c.id +'">'+ c.name +'</option>');
            })
        }
    };
    xhttp.send(null);
}




