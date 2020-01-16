package architecture.domain.models.bindingModels;

import architecture.annotations.BeginUppercase;
import architecture.constants.AppConstants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ImageBindingModel extends ImageBaseBindingModel{
    @NotNull
    @NotEmpty(message = "{text.empty}")
    @Size(min = AppConstants.NAME_MIN_LENGTH, max = AppConstants.NAME_MAX_LENGTH, message = "{text.length.between}")
    @BeginUppercase(allowEmpty = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
