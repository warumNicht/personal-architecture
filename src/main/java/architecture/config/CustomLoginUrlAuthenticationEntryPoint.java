package architecture.config;

import architecture.services.interfaces.LocaleService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private final LocaleService localeService;

    public CustomLoginUrlAuthenticationEntryPoint(String loginFormUrl, LocaleService localeService) {
        super(loginFormUrl);
        this.localeService = localeService;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        response.setHeader("ref", request.getRequestURI());
        request.getSession().setAttribute("ref", request.getRequestURI());
        super.commence(request, response, authException);
    }

    @Override
    public String getLoginFormUrl() {
        String locale = this.localeService.getLocale();
        return "/" + locale + super.getLoginFormUrl();
    }
}
