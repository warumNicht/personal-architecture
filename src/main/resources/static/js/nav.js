$('ul.navbar-nav li.dropdown').hover(function () {
    $(this).find(".dropdown-menu").stop(true, true).delay(200).fadeIn(500);
}, function () {
    $(this).find(".dropdown-menu").stop(true, true).delay(200).fadeOut(500);
});

$(document).ready(function () {
    $("#locales").change(function () {
        let selectedOption = $('#locales').val();
        if (selectedOption != '') {
            let url = location.href;
            console.log(url)
            // http://localhost:8080/de/
            location.assign('/'+ selectedOption + '/')
            // location.replace('?lang=' + selectedOption);
        }
    });
});

$(document).ready(function () {
    let url = location.href;
    let parts = url.split('?lang=');
    if (parts.length > 1) {
        location.assign(parts[0]);
    }
});