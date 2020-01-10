package architecture.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.TYPE_USE})
@Constraint(validatedBy = BeginUppercaseImpl.class)
public @interface BeginUppercase {
    String message() default "Must begin with uppercase letter!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
