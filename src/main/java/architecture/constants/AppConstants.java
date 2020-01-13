package architecture.constants;

import architecture.domain.CountryCodes;

import java.util.Locale;

public final class AppConstants {
    public static final String LOCALE_COOKIE_NAME = "lang";

    public static final Locale DEFAULT_LOCALE = Locale.US;

    public static final CountryCodes DEFAULT_COUNTRY_CODE = CountryCodes.BG;

    public static final long CASH_MAX_AGE = 2L;
    public static final int COUNTRY_SIZE = 5;

    public static final String DEFAULT_ERROR_VIEW = "error/error";
    public static final String CONTROLLER_ERROR_VIEW = "error/custom-error";

    //Image controller
    public static final String IMAGE_EDIT_VIEW = "edit-image";
    public static final String IMAGE_EDIT_BindingModel_Name = "imageEdit";

    //Admin controller
    public static final String CATEGORY_CREATE_VIEW = "categories/category-create";
    public static final String CATEGORY_EDIT_VIEW = "categories/category-edit";
    public static final String CATEGORIES_LIST = "categories/categories-list";
    public static final String ARTICLES_LIST_ALL = "listAll";

}
