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
        System.out.println("Url interceptor");

        String newLocale = request.getParameter("lang");
        if(newLocale!=null){
            return true;
        }

        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        request.setAttribute("dd", "ff");

        if (localeResolver == null) {
            throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
        }

        Locale locale = localeResolver.resolveLocale(request);


        localeResolver.setLocale(request, response, locale);

        return true;
    }
}
