package architecture.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BeginUppercaseImpl implements ConstraintValidator<BeginUppercase, String> {
    private boolean allowEmpty;

    @Override
    public void initialize(BeginUppercase constraintAnnotation) {
        this.allowEmpty = constraintAnnotation.allowEmpty();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        if (value.isEmpty()){
            return this.allowEmpty;
        }
        char firstChar = value.charAt(0);
        return Character.isUpperCase(firstChar);
    }
}
