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
    fetchCategories();
});

function fetchCategories() {
    fetch('/categories/all')
        .then((response) => {response.json()})
        .then((json) => {
            $('#categories').empty();
            $('#categories').append('<option value="all">All</option>');
            json.forEach((category) => {
                $('#categories').append('<option value="'+ category.id +'">'+ category.name +'</option>');
            });
        })
        .catch((err) => console.log(err));
}
