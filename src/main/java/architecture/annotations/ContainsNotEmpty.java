package architecture.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Constraint(validatedBy = ContainsNotEmptyImpl.class)
public @interface ContainsNotEmpty {

    String message() default "Must contain min 1 not empty value!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
