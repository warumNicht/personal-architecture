package architecture.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BeginUppercaseImpl implements ConstraintValidator<BeginUppercase,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value==null||value.isEmpty()){
            return false;
        }
        char firstChar = value.charAt(0);
        return Character.isUpperCase(firstChar);
    }
}
