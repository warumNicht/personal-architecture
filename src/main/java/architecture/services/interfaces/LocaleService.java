package architecture.services.interfaces;

import architecture.domain.CountryCodes;

public interface LocaleService {

    String getLocale();

    CountryCodes getCurrentCookieLocale();
}
