package architecture.web.controllers;

import architecture.domain.CountryCodes;
import architecture.services.interfaces.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseController {
    @Autowired
    private UrlService urlService;

    protected CountryCodes getCurrentCookieLocale() {
        return this.urlService.getCurrentCookieLocale();
    }
}
