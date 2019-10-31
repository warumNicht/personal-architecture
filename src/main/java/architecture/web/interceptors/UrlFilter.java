package architecture.web.interceptors;

import architecture.domain.CountryCodes;
import architecture.web.controllers.BaseController;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class UrlFilter extends BaseController implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();

        boolean hasLocale = false;
        try {
            char slash = requestURI.charAt(3);
            if (slash == '/') {
                hasLocale = true;
            }
        } catch (Exception e) {
        }
        if (hasLocale) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String currentCookieLocale = super.getCurrentCookieLocale().toString().toLowerCase();
        servletRequest.getRequestDispatcher("/de/admin/listAll").forward(servletRequest, servletResponse);
//        res.sendRedirect("/" + currentCookieLocale + requestURI);
        return;

    }
}
