package com.gustavodscruz.livranada.exception;

import lombok.*;

import java.io.Serializable;


@Builder
@Getter
@ToString
public class ValidationFieldError implements Serializable {
    String field;
    String defaultMessage;
    String code;
}
