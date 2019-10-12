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
    const select= $('#select-categories');
    fetchCategories(select);
});


