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
    fieldsNames.forEach((name)=>{
        $(`#${name.replace('.','\\.')}Div`).removeClass('text-danger');
    })
    $('small').remove('.text-danger');
}

function showAllCategories () {
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
                          }

export {showFieldErrors, removeOldErrors, showAllCategories}
