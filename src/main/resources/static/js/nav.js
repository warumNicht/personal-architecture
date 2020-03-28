import {fetchCategories, getLocale, fetchCategoriesDropdown} from "./fetch-functions.js";

$('ul.navbar-nav li.dropdown').hover(function () {
    $(this).find(".dropdown-menu").stop(true, true).delay(200).fadeIn(500);
}, function () {
    $(this).find(".dropdown-menu").stop(true, true).delay(200).fadeOut(500);
});

$('ul.navbar-nav').find('.dropdown').click(function (e) {
    e.stopPropagation();
});

$(document).ready(function () {

    document.querySelectorAll('.dropdown').forEach(function(dropdown){
        const currentLang=dropdown.querySelector('.dropdown-icon');
        const content=dropdown.querySelector('.dropdown-content');
        if(currentLang){
            currentLang.addEventListener('click', function(event){
                console.log(event);
                dropdown.classList.toggle('expanded-dropdown');
            })
        }
    });

    const select = $('#select-categories');
    const selectDropdown = $('#select-categories2');
    fetchCategories(select);
    fetchCategoriesDropdown(selectDropdown);
    select.change(function () {
        let selectedOption = select.val();
        if (selectedOption !== 'all') {
            //IE does not support `${var}`
            location.href = getLocale(location.href) + 'projects/category/' + selectedOption;
        }
    });
});




