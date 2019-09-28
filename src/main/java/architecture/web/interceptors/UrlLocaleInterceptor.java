package architecture.web.interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UrlLocaleInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        System.out.print("Url interceptor ");
        System.out.println(request.getRequestURI());

        String newLocale = request.getParameter("lang");
        if (newLocale != null) {
            request.setAttribute("lang",true);
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
        return true;
    }
}
