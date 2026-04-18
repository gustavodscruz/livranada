package com.gustavodscruz.livranada.utils;

import com.gustavodscruz.livranada.exception.ValidationFieldError;
import com.gustavodscruz.livranada.model.User;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;



@ExtendWith(MockitoExtension.class)
@Slf4j
@ActiveProfiles("test")
public class ValidationUtilsTest {

   private Validator validator;
   private ValidationUtils<User> validationUtils;

   @BeforeEach
    void setUp(){
       validator = Validation.buildDefaultValidatorFactory().getValidator();
       validationUtils = new ValidationUtils<>();
   }

   @Test
   void quandoGetMessageReceberSetConstraintEntaoDeveResponderAMensagemDeErroDoCampoComErro(){
       User user = User.builder()
               .username("usuario")
               .email("email-invalido")
               .password("senha123")
               .birthDate(LocalDate.of(2000, 1, 1))
               .country("US")
               .build();

       var violations = validator.validate(user);
       String message = validationUtils.getMessage(violations);
       log.debug("Mensagem recebida do validation utils: {}", message);
       assertEquals("User must have a valid email.", message);
   }

   @Test
   void quandoGetValidationFieldErrorsReceberSetConstraintEntaoDeveResponderUmaListaDeValidationFieldError(){
       User user = User.builder()
               .username("usuario")
               .email("email-invalido")
               .password("senha123")
               .birthDate(LocalDate.of(2000, 1, 1))
               .country("US")
               .build();

       var violations = validator.validate(user);
       var fieldErrors = validationUtils.getValidationFieldErrors(violations);

       assertEquals(1, fieldErrors.size());
       assertInstanceOf(ValidationFieldError.class, fieldErrors.get(0));

       assertEquals("User must have a valid email.", fieldErrors.get(0).getDefaultMessage());
       assertEquals("email", fieldErrors.get(0).getField());
       assertEquals("Email", fieldErrors.get(0).getCode());

       log.debug("ValidationFieldError recebido do validation utils: {}", fieldErrors.get(0));
    }

}
