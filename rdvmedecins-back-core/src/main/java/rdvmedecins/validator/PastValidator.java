package rdvmedecins.validator;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PastValidator implements ConstraintValidator<Past, Temporal> {

    @Override
    public void initialize(Past constraintAnnotation) {
    }

    @Override
    public boolean isValid(Temporal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        LocalDate ld = LocalDate.from(value);
        if (ld.isBefore(LocalDate.now())) {
            return true;
        }
        return false;
    }

}
