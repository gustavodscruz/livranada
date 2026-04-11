package com.gustavodscruz.livranada.utils;

import com.gustavodscruz.livranada.exception.ValidationFieldError;
import com.gustavodscruz.livranada.exception.ViolationObject;
import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ValidationUtils<T> {
    public ViolationObject normalizeFieldErrors(Set<ConstraintViolation<T>> violations) {
        if (violations.isEmpty()) return null;

        String message = violations.stream().findFirst().map(ConstraintViolation::getMessage).orElse("The request is not valid.");
        List<ValidationFieldError> validationFieldErrors = violations.stream().map(violation -> ValidationFieldError.builder()
                .defaultMessage(violation.getMessage())
                .code(violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName())
                .field(violation.getPropertyPath().toString())
                .build()
        ).toList();

        return ViolationObject.builder()
                .message(message)
                .validationFieldErrors(validationFieldErrors)
                .build();
    }

    public String getMessage(Set<ConstraintViolation<T>> violations){
        return violations.stream().findFirst().map(ConstraintViolation::getMessage).orElse("The request is not valid.");
    }

    public List<ValidationFieldError> getValidationFieldErrors(Set<ConstraintViolation<T>> violations){
        return violations.stream().map(violation -> ValidationFieldError.builder()
                .defaultMessage(violation.getMessage())
                .code(violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName())
                .field(violation.getPropertyPath().toString())
                .build()
        ).toList();
    }

}
