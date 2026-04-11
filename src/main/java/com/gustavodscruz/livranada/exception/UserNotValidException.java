package com.gustavodscruz.livranada.exception;

import com.gustavodscruz.livranada.model.User;
import com.gustavodscruz.livranada.utils.ValidationUtils;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Set;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
@Getter
public class UserNotValidException extends DefaultValidationErrorException {
    public UserNotValidException(Set<ConstraintViolation<User>> violations){
        super(getValidationFieldErrors(violations), getViolationMessage(violations));
    }

    static String getViolationMessage(Set<ConstraintViolation<User>> violations){
        final ValidationUtils<User> validationUtils = new ValidationUtils<>();
        return validationUtils.getMessage(violations);
    }

    static List<ValidationFieldError> getValidationFieldErrors(Set<ConstraintViolation<User>> violations) {
        final ValidationUtils<User> validationUtils = new ValidationUtils<>();
        return validationUtils.getValidationFieldErrors(violations);
    }
}
