package architecture.config;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import java.util.Locale;

@Configuration
public class AppBeansConfiguration {

    @Bean
    public  LocaleResolver localeResolver(){
        CustomLocalResolver customLocalResolver = new CustomLocalResolver();
        customLocalResolver.setDefaultLocale(Locale.US);
        customLocalResolver.setCookieHttpOnly(true);
        customLocalResolver.setCookieName("lang");
        customLocalResolver.setCookieMaxAge(60*60);
        return customLocalResolver;
    }

    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {

        ServletRegistrationBean registration = new ServletRegistrationBean(
                dispatcherServlet, "/bg/*", "/de/*", "/en/*", "/fr/*", "/es/*");

        registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
        return registration;
    }

}
