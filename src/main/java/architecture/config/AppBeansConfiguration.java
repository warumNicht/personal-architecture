package architecture.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import java.util.Locale;

@Configuration
public class AppBeansConfiguration {
//
//    @Bean
//    public LocaleResolver localeResolver() {
//        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
//        cookieLocaleResolver.setDefaultLocale(Locale.US);
//        cookieLocaleResolver.setCookieHttpOnly(true);
//        cookieLocaleResolver.setCookieName("lang");
//        cookieLocaleResolver.setCookieMaxAge(120);
//        return cookieLocaleResolver;
//    }

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

    // To solver URL like:
    // /SomeContextPath/en/login2
    // /SomeContextPath/vi/login2
    // /SomeContextPath/fr/login2
//    @Bean(name = "localeResolver")
//    public LocaleResolver getLocaleResolver() {
//        LocaleResolver resolver = new UrlLocaleResolver();
//        return resolver;
//    }

    @Bean
    public  CustomLocalResolver getCustomLocalResolver(){
        CustomLocalResolver customLocalResolver = new CustomLocalResolver();
        customLocalResolver.setDefaultLocale(Locale.US);
        customLocalResolver.setCookieHttpOnly(true);
        customLocalResolver.setCookieName("lang");
        customLocalResolver.setCookieMaxAge(120);
        return customLocalResolver;
    }



}
