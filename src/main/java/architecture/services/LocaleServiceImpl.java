package architecture.services;

import architecture.constants.ApplicationConstants;
import architecture.domain.CountryCodes;
import architecture.services.interfaces.LocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service(value = "localeService")
public class LocaleServiceImpl implements LocaleService {
    @Autowired
    private HttpServletRequest request;

    @Override
    public String getLocale() {
        return this.getCurrentCookieLocale().toString().toLowerCase();
    }

    @Override
    public CountryCodes getCurrentCookieLocale() {
        Cookie actualCookie = WebUtils.getCookie(this.request, ApplicationConstants.LOCALE_COOKIE_NAME);
        if (actualCookie != null) {
            return CountryCodes.valueOf(actualCookie.getValue().toUpperCase());
        }
        return ApplicationConstants.DEFAULT_COUNTRY_CODE;
    }
}
