package architecture.web.interceptors;

import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomLocalInterceptor extends LocaleChangeInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {

        System.out.print("Local interceptor   ");
        System.out.println(request.getRequestURI());
        return super.preHandle(request, response, handler);
    }
}
