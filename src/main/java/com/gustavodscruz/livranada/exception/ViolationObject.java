package com.gustavodscruz.livranada.exception;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViolationObject{
    private String message;
    private List<ValidationFieldError> validationFieldErrors;
}
