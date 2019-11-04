package architecture.web.controllers;

import architecture.domain.CountryCodes;
import architecture.services.interfaces.LocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseController {
    @Autowired
    private LocaleService localeService;

    protected CountryCodes getCurrentCookieLocale() {
        return this.localeService.getCurrentCookieLocale();
    }

    protected String getLocale(){
        return this.localeService.getLocale();
    }
}
