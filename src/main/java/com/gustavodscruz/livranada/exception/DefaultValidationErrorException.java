package com.gustavodscruz.livranada.exception;


import lombok.Getter;

import java.util.List;

@Getter
public abstract class DefaultValidationErrorException extends RuntimeException {

    final List<ValidationFieldError> validationFieldErrors;

    protected DefaultValidationErrorException(List<ValidationFieldError> validationFieldErrors, String message){
        this.validationFieldErrors = validationFieldErrors;
        super(message);
    }
}
