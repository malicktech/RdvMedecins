package rdvmedecins.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
import javax.validation.Constraint;
import javax.validation.Payload;
 /**
  * http://www.jdev.it/custom-jsr-303-bean-validations-jsr-310-new-datetime-api/
  * @author Malick
  *
  */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PastValidator.class)
@Documented
public @interface Past {
 
    String message() default "it.jdev.example.jsr310.validator.Past.message";
 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
}
