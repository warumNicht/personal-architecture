package architecture.config;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Locale;

public class CustomLocalResolver extends CookieLocaleResolver implements LocaleResolver {
    private static final String URL_LOCALE_ATTRIBUTE_NAME = "URL_LOCALE_ATTRIBUTE_NAME";


    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        // ==> /SomeContextPath/en/...
        // ==> /SomeContextPath/fr/...
        // ==> /SomeContextPath/WEB-INF/pages/...
//        request.removeAttribute("org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE");


        Locale cookieResolverResult = super.resolveLocale(request);
        if(cookieResolverResult!=null){
            return cookieResolverResult;
        }

        String uri = request.getRequestURI();

        Cookie actualCookie = WebUtils.getCookie(request, "lang");
        if (actualCookie != null) {
            Locale forLanguageTag = Locale.forLanguageTag(actualCookie.getValue());
            return forLanguageTag;
        }


        System.out.println("URI=" + uri);

        String prefixEn = request.getServletContext().getContextPath() + "/en/";
        String prefixFr = request.getServletContext().getContextPath() + "/fr/";
        String prefixDe = request.getServletContext().getContextPath() + "/de/";
        String prefixBg = request.getServletContext().getContextPath() + "/bg/";
        String prefixEs = request.getServletContext().getContextPath() + "/es/";



        Locale locale = null;
        // English
        if (uri.startsWith(prefixEn)) {
            locale = Locale.ENGLISH;
        }
        // French
        else if (uri.startsWith(prefixFr)) {
            locale = Locale.FRENCH;


        } else if (uri.startsWith(prefixDe)) {
            locale = Locale.GERMAN;
        } else if (uri.startsWith(prefixBg)) {
            locale = Locale.forLanguageTag("bg");
        } else if (uri.startsWith(prefixEs)) {
            locale = Locale.forLanguageTag("es");
        }
        if (locale != null) {
            request.getSession().setAttribute(URL_LOCALE_ATTRIBUTE_NAME, locale);
        }
        if (locale == null) {
            locale = (Locale) request.getSession().getAttribute(URL_LOCALE_ATTRIBUTE_NAME);
            if (locale == null) {
                locale = Locale.ENGLISH;
            }
        }
        return locale;
    }

}
