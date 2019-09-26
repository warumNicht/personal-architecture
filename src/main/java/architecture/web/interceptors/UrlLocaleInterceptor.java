package architecture.web.interceptors;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class UrlLocaleInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("Url interceptor");

        String newLocale = request.getParameter("lang");
        if (newLocale != null) {
            return true;
        }
        String requestedUri = request.getRequestURI();
        String currentLocaleInUri = requestedUri.substring(1, 3);
        Cookie actualCookie = WebUtils.getCookie(request, "lang");
        if (actualCookie == null || actualCookie.getValue().equals(currentLocaleInUri) == false) {
            String redirect = requestedUri + "?lang=" + currentLocaleInUri;
            response.sendRedirect(redirect);
            return false;
        }

        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);

        if (localeResolver == null) {
            throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
        }
        Locale locale = localeResolver.resolveLocale(request);
        localeResolver.setLocale(request, response, locale);
        return true;
    }
}
