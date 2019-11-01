package architecture.web.interceptors;

import antlr.StringUtils;
import architecture.domain.CountryCodes;
import architecture.web.controllers.BaseController;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component

public class UrlFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest hsRequest = (HttpServletRequest) request;

        String url = hsRequest.getRequestURI().substring(hsRequest.getContextPath().length());

        String concat = "/en/admin/category/create";
        //If not, we just have to add /en/ at start of the URL
        RequestDispatcher dispatcher = request.getRequestDispatcher(concat);
        dispatcher.forward(request, response);
    }

    public void destroy() {
    }
}
