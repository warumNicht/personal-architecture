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


//    @Bean(name = "messageSource")
//    public MessageSource getMessageResource()  {
//        ReloadableResourceBundleMessageSource messageResource= new ReloadableResourceBundleMessageSource();
//
//        // Read i18n/messages_xxx.properties file.
//        // For example: i18n/messages_en.properties
//        messageResource.setBasename("classpath:i18n/messages");
////        messageResource.setDefaultEncoding("UTF-8");
//        return messageResource;
//    }


    @Bean
    public  LocaleResolver localeResolver(){
        CustomLocalResolver customLocalResolver = new CustomLocalResolver();
        customLocalResolver.setDefaultLocale(Locale.US);
        customLocalResolver.setCookieHttpOnly(true);
        customLocalResolver.setCookieName("lang");
        customLocalResolver.setCookieMaxAge(60*60);
        return customLocalResolver;
    }
//
//    @Bean
//    public DispatcherServlet dispatcherServlet() {
//        return new DispatcherServlet();
//    }

    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {

        ServletRegistrationBean registration = new ServletRegistrationBean(
                dispatcherServlet, "/bg/*", "/de/*", "/en/*", "/fr/*", "/es/*");

        registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
        return registration;
    }

//    @Bean
//    public ServletRegistrationBean dispatcherServletRegistration(DispatcherServlet dispatcherServlet) {
//        ServletRegistrationBean registration = new ServletRegistrationBean(
//                dispatcherServlet, "/", "/bg/", "/de/", "/en/", "/fr/", "/es/");
//        registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
//        return registration;
//    }


}
