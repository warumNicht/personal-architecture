package architecture.error;

import architecture.config.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public abstract class BaseError extends RuntimeException {

    private MessageSource messageSource;


    public BaseError(String message) {
        super(message);
        this.messageSource=SpringContext.getBean(MessageSource.class);
        Locale locale = LocaleContextHolder.getLocale();
        String messageLocalized = this.messageSource.getMessage(message, null, locale);
        System.out.println(messageLocalized);
    }
}
