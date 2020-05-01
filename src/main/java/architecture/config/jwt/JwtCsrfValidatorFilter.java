package architecture.config.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class JwtCsrfValidatorFilter extends OncePerRequestFilter {
    private String[] ignoreCsrfAntMatchers = { "/users/authentication" };

    @Autowired
    private SecretService secretService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // NOTE: A real implementation should have a nonce cache so the token cannot be reused

        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");

        if (
            // only care if it's a POST
                "POST".equals(request.getMethod()) &&
                        // ignore if the request path is in our list
                        Arrays.binarySearch(ignoreCsrfAntMatchers, request.getServletPath()) < 0 &&
                        // make sure we have a token
                        token != null) {
            // CsrfFilter already made sure the token matched. Here, we'll make sure it's not expired
            try {
                Jwts.parser()
                        .setSigningKeyResolver(secretService.getSigningKeyResolver())
                        .parseClaimsJws(token.getToken());
            } catch (JwtException e) {
                // most likely an ExpiredJwtException, but this will handle any
                request.setAttribute("exception", e);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                RequestDispatcher dispatcher = request.getRequestDispatcher("expired-jwt");
                dispatcher.forward(request, response);
            }
        }

        filterChain.doFilter(request, response);
    }
}
