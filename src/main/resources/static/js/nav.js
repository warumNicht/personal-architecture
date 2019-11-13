import {fetchCategories} from "./fetch-functions.js";

$('ul.navbar-nav li.dropdown').hover(function () {
    $(this).find(".dropdown-menu").stop(true, true).delay(200).fadeIn(500);
}, function () {
    $(this).find(".dropdown-menu").stop(true, true).delay(200).fadeOut(500);
});

$(document).ready(function () {
    $("#select-locales").change(function () {
        let selectedOption = $('#select-locales').val();
        if (selectedOption != '') {
            location.replace('?lang=' + selectedOption);
        }
    });
    const select = $('#select-categories');
    fetch('/fetch/categories/all')
        .then((response) => response.json())
        .then(function(json){
            json.forEach(function(category){
                select.append('<option value="'+ category.id +'">'+ category.name +'</option>');
            });
        })
        .catch((err) => console.log(err));
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


