function fetchCategories(selectElement) {

    const xhttp = new XMLHttpRequest();
    xhttp.open('GET', '/fetch/categories/all', true);
    xhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            const json = JSON.parse(this.response);
            json.forEach(function(category){
                selectElement.append('<option value="' + category.id + '">' + category.name + '</option>');
            });
        }
    };
    xhttp.send(null);
}

export {fetchCategories}