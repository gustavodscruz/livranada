package com.gustavodscruz.livranada.utils;

import com.gustavodscruz.livranada.exception.ValidationFieldError;
import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ValidationUtils<T> {

    /**
     * @param violations a set of constraint violations
     * @return the message of the first violation, or a default message if there are no violations
     */
    public String getMessage(Set<ConstraintViolation<T>> violations){
        return violations.stream().findFirst().map(ConstraintViolation::getMessage).orElse("The request is not valid.");
    }

    /**
     * @param violations a set of constraint violations
     * @return a list of validation field errors, each containing the default message, the code (the simple name of the annotation), and the field (the property path) of the violation
     */
    public List<ValidationFieldError> getValidationFieldErrors(Set<ConstraintViolation<T>> violations){
        return violations.stream().map(violation -> ValidationFieldError.builder()
                .defaultMessage(violation.getMessage())
                .code(violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName())
                .field(violation.getPropertyPath().toString())
                .build()
        ).toList();
    }

}
