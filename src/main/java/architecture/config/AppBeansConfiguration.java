package architecture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    
}
