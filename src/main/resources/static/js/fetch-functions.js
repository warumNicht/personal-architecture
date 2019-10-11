function fetchCategories( selectElement) {
    fetch('/fetch/categories/all')
        .then((response) => response.json())
        .then((json) => {
            json.forEach((category) => {
                selectElement.append('<option value="'+ category.id +'">'+ category.name +'</option>');
            });
        })
        .catch((err) => console.log(err));
}

export {fetchCategories}