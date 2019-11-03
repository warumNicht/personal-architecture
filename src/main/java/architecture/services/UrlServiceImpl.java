package architecture.services;

import architecture.constants.ApplicationConstants;
import architecture.domain.CountryCodes;
import architecture.services.interfaces.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class UrlServiceImpl implements UrlService {
    @Autowired
    private HttpServletRequest request;

    @Override
    public String getLocaleFromUrl(){
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
