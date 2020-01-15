function showFieldErrors(errorMap){
    for (const [key, value] of Object.entries(errorMap)) {
        const currentFieldDiv = $(`#${key.replace('.','\\.')}Div`);
        console.log(`#${key}Div`);
        console.log(currentFieldDiv);
        currentFieldDiv.addClass('text-danger');
        const currentSmall = $('<small></small>').addClass('text-danger');

        value.forEach((v)=>{
            currentSmall.append(`${v}<br>`)
        })
        currentFieldDiv.append(currentSmall);
     }
}

function removeOldErrors(fieldsNames){
    const divFieldsIds=['image.url', 'image.name'];
    fieldsNames.forEach((name)=>{
        $(`#${name}Div`).removeClass('text-danger');
    })
    $('small').remove('.text-danger');
}

export {showFieldErrors, removeOldErrors}
