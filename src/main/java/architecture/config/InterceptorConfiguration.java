package architecture.config;

import architecture.web.interceptors.CustomLocalInterceptor;
import architecture.web.interceptors.LocalizeURLInterceptor;
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
        LocalizeURLInterceptor localizeURLInterceptor = new LocalizeURLInterceptor();
        registry.addInterceptor(localizeURLInterceptor).excludePathPatterns("/js/**","/css/**", "/error").order(Ordered.HIGHEST_PRECEDENCE);

        UrlLocaleInterceptor localeInterceptor = new UrlLocaleInterceptor();
        registry.addInterceptor(localeInterceptor).excludePathPatterns("/js/**","/css/**", "/error").order(Ordered.HIGHEST_PRECEDENCE-1);

        LocaleChangeInterceptor lci = new CustomLocalInterceptor();
        lci.setParamName("lang");
        registry.addInterceptor(lci).excludePathPatterns("/js/**","/css/**", "/error").order(Ordered.LOWEST_PRECEDENCE);
    }
}
