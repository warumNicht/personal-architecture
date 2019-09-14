package architecture.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    private LocaleChangeInterceptor localeChangeInterceptor;
    @Autowired
    public InterceptorConfiguration(LocaleChangeInterceptor localeChangeInterceptor) {
        this.localeChangeInterceptor=localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.localeChangeInterceptor);
    }
}
