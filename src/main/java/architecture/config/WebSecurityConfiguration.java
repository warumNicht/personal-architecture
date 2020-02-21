package architecture.config;

import architecture.constants.AppConstants;
import architecture.error.CustomAccessDeniedHandler;
import architecture.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .csrfTokenRepository(this.csrfTokenRepository())
                .and()
                .authorizeRequests()
                .antMatchers("/**/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()

                .loginPage("/users/login")
//                .defaultSuccessUrl("/users/log")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler((req,res,auth)->{    //Success handler invoked after successful authentication
                    for (GrantedAuthority authority : auth.getAuthorities()) {
                        System.out.println(authority.getAuthority());
                    }
                    String contextPath = Arrays.stream(req.getCookies())
                            .filter(c -> c.getName().equals(AppConstants.LOCALE_COOKIE_NAME))
                            .findFirst().orElse(null).getValue();
                    System.out.println(auth.getName());
                    res.sendRedirect("/" + contextPath + "/"); // Redirect user to index/home page
                })
                .and()
                .logout()
                .logoutSuccessHandler((req,res,auth)->{   // Logout handler called after successful logout
                    String contextPath = Arrays.stream(req.getCookies())
                            .filter(c -> c.getName().equals(AppConstants.LOCALE_COOKIE_NAME))
                            .findFirst().orElse(null).getValue();
                    req.getSession().setAttribute("message", "You are logged out successfully.");
                    res.sendRedirect("/" + contextPath + "/"); // Redirect user to login page with message.
                })
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository =
                new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}
