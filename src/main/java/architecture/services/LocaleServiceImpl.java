package architecture.services;

import architecture.constants.AppConstants;
import architecture.domain.CountryCodes;
import architecture.services.interfaces.LocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public boolean checkOldAndroid() {
        String userAgent = this.request.getHeader("user-agent");
        System.out.println(userAgent);
        String text =
                "John writes about this, Android 4.1.2; that," +
                        " and John writes about everything. ";

        String androidPattern = "[A|a]ndroid\\s+(\\d+)";

        Pattern pattern = Pattern.compile(androidPattern);
        Matcher matcher = pattern.matcher(userAgent);
        if(matcher.find()){
            String androidVersion = matcher.group(1);
            System.out.println(androidVersion);
            int version = Integer.parseInt(androidVersion);
            return version < AppConstants.ANDROID_VERSION_WITHOUT_POLYFILLS;
        }
        return false;
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
