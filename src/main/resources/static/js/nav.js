import {fetchCategories, getLocale} from "./fetch-functions.js";

//test for touch events support and if not supported, attach .no-touch class to the HTML tag.

if (!("ontouchstart" in document.documentElement)) {
    document.documentElement.className += " no-touch";
}

$('ul.navbar-nav li.dropdown').hover(function () {
    $(this).find(".dropdown-menu").stop(true, true).delay(200).fadeIn(500);
}, function () {
    $(this).find(".dropdown-menu").stop(true, true).delay(200).fadeOut(500);
});

$('ul.navbar-nav').find('.dropdown').click(function (e) {
    e.stopPropagation();
});

$(document).ready(function () {
    // $("#select-locales").change(function () {
    //     let selectedOption = $('#select-locales').val();
    //     if (selectedOption !== '') {
    //         location.replace('?lang=' + selectedOption.toLowerCase());
    //     }
    // });

    $('.dropdown').each(function (){
        this.addEventListener('click',function (event){
            console.log(event);

            $(this).find( 'div.dropdown-content' ).css({display:"block"})
        });
    });
    const select = $('#select-categories');
    fetchCategories(select);
    select.change(function () {
        let selectedOption = select.val();
        if (selectedOption !== 'all') {
            //IE does not support `${var}`
            location.href = getLocale(location.href) + 'projects/category/' + selectedOption;
        }
    });
});




