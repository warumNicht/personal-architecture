package architecture.constants;

import architecture.domain.CountryCodes;

import java.util.Locale;

public final class AppConstants {
    public static final String LOCALE_COOKIE_NAME = "lang";

    public static final Locale DEFAULT_LOCALE = Locale.US;

    public static final CountryCodes DEFAULT_COUNTRY_CODE = CountryCodes.BG;

    public static final long CASH_MAX_AGE = 2L;
    public static final int COUNTRY_SIZE = 5;

    public static final int NAME_MIN_LENGTH = 2;
    public static final int NAME_MAX_LENGTH = 100;
    public static final int CATEGORY_MAX_LENGTH = 40;

    public static final int USERNAME_MAX_LENGTH = 40;
    public static final int USER_EMAIL_MAX_LENGTH = 80;
    public static final int USER_PASSWORD_MAX_LENGTH = 80;
    public static final int USER_ROLE_MAX_LENGTH = 10;

    public static final int DESCRIPTION_MIN_LENGTH = 5;

    public static final String URL_REGEX_PATTERN = "^https?:\\/\\/(www\\.)?(?!w{0,2}\\.)[^\"'\\s]{3,}\\.(png|PNG|jpg|JPG|jpeg|JPEG|gif|GIF|png|PNG|svg|SVG|webp|WEBP)$|^$";


}
