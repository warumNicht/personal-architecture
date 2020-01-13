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
    public static final int NAME_MAX_LENGTH = 256;
    public static final int CATEGORY_MAX_LENGTH = 40;
    public static final int DESCRIPTION_MIN_LENGTH = 5;


}
