package architecture.constants;

import architecture.domain.CountryCodes;

import java.util.Locale;

public final class AppConstants {
    public static final String LOCALE_COOKIE_NAME = "lang";

    public static final Locale DEFAULT_LOCALE = Locale.US;

    public static final CountryCodes DEFAULT_COUNTRY_CODE = CountryCodes.BG;

    public static final long CASH_MAX_AGE = 2L;
    public static final int COUNTRY_SIZE = 5;


}
