import {fetchCategories, getLocale, fetchCategoriesDropdown} from "./fetch-functions.js";





//test for touch events support and if not supported, attach .no-touch class to the HTML tag.

if (!("ontouchstart" in document.documentElement)) {
    document.documentElement.className += " no-touch";
}

$('ul.navbar-nav li.dropdown').hover(function () {
    $(this).find(".dropdown-menu").stop(true, true).delay(200).fadeIn(500);
}, function () {
    $(this).find(".dropdown-menu").stop(true, true).delay(200).fadeOut(500);
});

$('ul.navbar-nav').find('.dropdown-arch').click(function (e) {
    e.stopPropagation();
});

$(document).ready(function () {

    // $("#select-locales").change(function () {
    //     let selectedOption = $('#select-locales').val();
    //     if (selectedOption !== '') {
    //         location.replace('?lang=' + selectedOption.toLowerCase());
    //     }
    // });

    // $('.dropdown').each(function (){
    //     this.addEventListener('click',function (event){
    //         console.log(event);
    //
    //         $(this).find( 'div.dropdown-content' ).css({display:"block"})
    //     });
    // });

    $('.dropdown-arch').each(function (index, e) {
        console.log(index, e);
        $(this).mouseleave(function () {

                    $(this).find('.dropdown-content-arch').slideUp( "slow");
                    console.log('hover -> out')

            })
    });

    document.querySelectorAll('.dropdown-arch').forEach(function(dropdown){
        const currentLang=dropdown.querySelector('.dropdown-icon');
        const content=dropdown.querySelector('.dropdown-content-arch');
        if(currentLang){
            currentLang.addEventListener('click', function(event){
                console.log(event);
                dropdown.classList.toggle('expanded-dropdown-arch');
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




