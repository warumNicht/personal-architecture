import {fetchCategories, fetchCategoriesDropdown, getLocale} from "./fetch-functions.js";


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
        $(this).hover(function () {
            console.log('hover -> in');
            $(this).removeClass('dropdown-out');
        }, function () {
            $(this).addClass('dropdown-out');
            setTimeout(function(){
                $(this).removeClass('dropdown-out');
                console.log('removed class')
            },2000);
            console.log('hover -> out')

        })
    });

    document.querySelectorAll('.dropdown-arch').forEach(function(dropdown){
        const currentLang=dropdown.querySelector('.dropdown-icon');
        const content=dropdown.querySelector('.dropdown-content-arch');
        let index=0;
        if(currentLang){
            currentLang.addEventListener('click', function(event){
                console.log(event);
                if(index%2===0){
                    dropdown.classList.toggle('expanded-dropdown-arch');
                    dropdown.classList.remove('dropdown-out');
                }else {
                    dropdown.classList.toggle('dropdown-out');
                    dropdown.classList.remove('expanded-dropdown-arch');
                }
                index++;

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




