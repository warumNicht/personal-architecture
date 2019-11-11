function fetchCategories(selectElement) {
    const xhttp = new XMLHttpRequest();
    xhttp.open('GET', '/fetch/categories/all', true);
    xhttp.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhttp.onreadystatechange= function(){
        if(this.readyState==4){
            selectElement.append('<option value="' + '">' + 'darhbytq354' + '</option>');
        }
    };
    xhttp.send(null);
}

export {fetchCategories}