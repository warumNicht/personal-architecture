function fetchCategories(selectElement) {
    fetch('/fetch/categories/all')
        .then(function(response){return response.json()})
        .then(function(json){
            json.forEach(function(category){
                selectElement.append('<option value="'+ category.id +'">'+ category.name +'</option>');
            });
        })
        .catch(function(err) {console.log(err)});
}

export {fetchCategories}