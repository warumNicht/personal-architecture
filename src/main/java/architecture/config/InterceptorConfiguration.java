package architecture.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        UrlLocaleInterceptor localeInterceptor = new UrlLocaleInterceptor();
        registry.addInterceptor(localeInterceptor).addPathPatterns("/en/*", "/fr/*", "/de/*","/bg/*","/es/*").order(Ordered.HIGHEST_PRECEDENCE);

        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        registry.addInterceptor(lci).order(Ordered.LOWEST_PRECEDENCE);
    }
}
