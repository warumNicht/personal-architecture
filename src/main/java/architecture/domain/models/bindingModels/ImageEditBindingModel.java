package architecture.domain.models.bindingModels;

import architecture.domain.CountryCodes;
import architecture.domain.models.BaseModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;

public class ImageEditBindingModel extends BaseModel {
    @NotNull
    @NotEmpty
    private String url;
    private LinkedHashMap<CountryCodes, String> localImageNames;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LinkedHashMap<CountryCodes, String> getLocalImageNames() {
        return localImageNames;
    }

    public void setLocalImageNames(LinkedHashMap<CountryCodes, String> localImageNames) {
        this.localImageNames = localImageNames;
    }
}
