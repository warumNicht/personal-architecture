import {fetchCategories} from "./fetch-functions.js";

// $(document).ready(function () {
//     const select = $('#cat');
//     fetchCategories(select);
// });

window.showAllCategories = function () {
    const categoriesSelect = $('#cat');
    const selectedOption = $('#cat option:selected');
    const selectedOptionId = selectedOption.val();
    categoriesSelect.empty();
    const categoriesOptions = $('#select-categories option');

    categoriesOptions.each(function (index) {

        if (index != 0) {
            const value = $(this).val();
            const innerText = $(this).text();
            if (selectedOptionId === value) {
                selectedOption.text(innerText);
                categoriesSelect.append(selectedOption);
            } else {
                categoriesSelect.append(`<option value="${value}">${innerText}</option>`);
            }
        }
    });
    categoriesSelect.change(function () {
        $('#categoryChange').removeAttr('disabled');
    });
};