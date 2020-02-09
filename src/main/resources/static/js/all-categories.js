function editCategory(categoryId) {
    const url = `/${locale}/admin/category/edit/${categoryId}`;
    window.location.href = url;
}

function showAllCategories() {
    const categoriesContainer = $('div.article-container');
    const categoriesSelect = $('#select-categories option');

    categoriesSelect.each(function (index) {
        if (index !== 0) {
            const value = $(this).val();
            const innerText = $(this).text();
            const currentCategoryDiv = $('<div></div>');
            currentCategoryDiv.append(`<div>${innerText}</div>`);
            currentCategoryDiv.append(`<button class="btn btn-info" onclick="editCategory(${value})">Edit Category</button>`);
            categoriesContainer.append(currentCategoryDiv);
        }
    });
    $('div.spinner-border').hide();
}