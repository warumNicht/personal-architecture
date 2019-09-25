package architecture.config;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class UrlLocaleInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        request.setAttribute("dd", "ff");

        if (localeResolver == null) {
            throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
        }

        // Get Locale from LocaleResolver
        Locale locale = localeResolver.resolveLocale(request);
//        Cookie lang = new Cookie("lang", locale.getLanguage());
//        lang.setMaxAge(180);
//        response.addCookie(lang);

        localeResolver.setLocale(request, response, locale);

        return true;
    }
}
