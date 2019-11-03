package architecture.services.interfaces;

import architecture.domain.CountryCodes;

public interface UrlService {
    String getLocaleFromUrl();


    CountryCodes getCurrentCookieLocale();
}
