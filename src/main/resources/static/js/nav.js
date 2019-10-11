import {fetchCategories} from "./fetch-functions.js";

$('ul.navbar-nav li.dropdown').hover(function () {
    $(this).find(".dropdown-menu").stop(true, true).delay(200).fadeIn(500);
}, function () {
    $(this).find(".dropdown-menu").stop(true, true).delay(200).fadeOut(500);
});

$(document).ready(function () {
    $("#locales").change(function () {
        let selectedOption = $('#locales').val();
        if (selectedOption != '') {
            location.replace('?lang=' + selectedOption);
        }
    });
    const select= $('#categories');
    fetchCategories(select);
});


