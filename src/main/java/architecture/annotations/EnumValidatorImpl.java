package architecture.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator,Enum<?>> {
    private Class enumToValidate;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        this.enumToValidate = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        try {
            Enum currentEnum = Enum.valueOf(this.enumToValidate, value.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
