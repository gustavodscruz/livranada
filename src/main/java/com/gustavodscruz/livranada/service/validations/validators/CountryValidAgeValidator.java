package com.gustavodscruz.livranada.service.validations.validators;

import com.gustavodscruz.livranada.model.User;
import com.gustavodscruz.livranada.service.validations.annotations.CountryValidAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;

import static com.gustavodscruz.livranada.service.validations.rules.MinAgeRule.minAgePerCountryValidationRule;

@Component
public class CountryValidAgeValidator implements ConstraintValidator<CountryValidAge, User> {


    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (user == null) return true;

        String userCountry = user.getCountry();
        LocalDate birthDate = user.getBirthDate();

        if (userCountry == null || birthDate == null) return true;

        String normalizedCountry = userCountry.toUpperCase(Locale.ROOT);

        if (minAgePerCountryValidationRule().containsKey(normalizedCountry)) {
            int minAge = Integer.parseInt(minAgePerCountryValidationRule().get(normalizedCountry));
            boolean result = birthDate.getYear() < LocalDate.now().getYear() - minAge;
            if (!result) {
                context.buildConstraintViolationWithTemplate("User must be at least " + minAge + " years old to register from " + normalizedCountry + ".")
                        .addPropertyNode("birthDate")
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
