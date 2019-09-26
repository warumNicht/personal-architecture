package architecture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        customLocalResolver.setCookieMaxAge(120);
        return customLocalResolver;
    }


}
