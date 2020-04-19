package architecture.config;

import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.*;
import java.io.IOException;

public class CsrfGrantingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        String token = csrf.getToken();
        chain.doFilter(request, response);
    }
}
