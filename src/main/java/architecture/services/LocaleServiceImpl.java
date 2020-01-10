package architecture.services;

import architecture.constants.AppConstants;
import architecture.domain.CountryCodes;
import architecture.services.interfaces.LocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service(value = "localeService")
public class LocaleServiceImpl implements LocaleService {
    private HttpServletRequest request;

    @Autowired
    public LocaleServiceImpl(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getLocale() {
        return this.getCurrentCookieLocale().toString().toLowerCase();
    }

    @Override
    public CountryCodes getCurrentCookieLocale() {
        Cookie actualCookie = WebUtils.getCookie(this.request, AppConstants.LOCALE_COOKIE_NAME);
        if (actualCookie != null) {
            return CountryCodes.valueOf(actualCookie.getValue().toUpperCase());
        }
        return AppConstants.DEFAULT_COUNTRY_CODE;
    }
}
