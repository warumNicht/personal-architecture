package architecture.web.controllers;

import architecture.constants.ApplicationConstants;
import architecture.domain.CountryCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {

    @Autowired
    private HttpServletRequest request;

    protected CountryCodes getCurrentCookieLocale() {
        Cookie actualCookie = WebUtils.getCookie(this.request, ApplicationConstants.LOCALE_COOKIE_NAME);
        if (actualCookie != null) {
            return CountryCodes.valueOf(actualCookie.getValue().toUpperCase());
        }
        return ApplicationConstants.DEFAULT_COUNTRY_CODE;
    }
}
