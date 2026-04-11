package com.gustavodscruz.livranada.service.validations.annotations;


import com.gustavodscruz.livranada.service.validations.validators.CountryValidAgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CountryValidAgeValidator.class)
@Target({ ElementType.TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CountryValidAge {
    String message() default "User doesn't have the min age for the country.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
