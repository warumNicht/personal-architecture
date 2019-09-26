package architecture.config;

import architecture.web.interceptors.CustomLocalInterceptor;
import architecture.web.interceptors.UrlLocaleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        UrlLocaleInterceptor localeInterceptor = new UrlLocaleInterceptor();
        registry.addInterceptor(localeInterceptor).addPathPatterns("/en/**", "/fr/**", "/de/**","/bg/**","/es/**").order(Ordered.HIGHEST_PRECEDENCE);

        LocaleChangeInterceptor lci = new CustomLocalInterceptor();
        lci.setParamName("lang");
        registry.addInterceptor(lci).excludePathPatterns("/js/**","/css/**", "/error").order(Ordered.LOWEST_PRECEDENCE);
    }
}
