package living_alone.eat.web.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidFileValidator.class)
public @interface ValidFile {
    String message() default "{required.recipeImage}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
