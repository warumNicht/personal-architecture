package architecture.config;

import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CsrfGrantingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        String token = csrf.getToken();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Cookie[] cookies = httpServletRequest.getCookies();
        if(httpServletRequest.getRequestURI().contains("/session")){
            Cookie cookie = new Cookie("ses", token);
            cookie.setPath("/");
//            ((HttpServletResponse) response).addCookie(cookie);
        }
        System.out.println(cookies);
        chain.doFilter(request, response);
    }
}
