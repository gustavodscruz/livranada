package com.gustavodscruz.livranada.service;

import com.gustavodscruz.livranada.exception.UserNotFoundException;
import com.gustavodscruz.livranada.exception.UserNotValidException;
import com.gustavodscruz.livranada.model.User;
import com.gustavodscruz.livranada.repository.UserRepository;
import com.gustavodscruz.livranada.utils.SecurityUtils;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.testcontainers.junit.jupiter.Container;

import java.time.LocalDate;
import java.util.List;

import static com.gustavodscruz.livranada.utils.SecurityUtils.hashString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    @MockitoSpyBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    private Validator validator;

    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
            .withReuse(true);


    @BeforeAll
    static void startContainer() {
        mysql.start();
    }


    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void quandoCreateReceberUmUsuarioValidoEntaoDeveSalvarNoBanco() {
        var userToCreate = instanciaUser();
        assertTrue(validator.validate(userToCreate).isEmpty());

        User user;
        try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class);) {
            mocked.when(() -> hashString(anyString())).thenReturn("hashedPassword123");
            user = userService.create(userToCreate);
            mocked.verify(() -> hashString(userToCreate.getPassword()), times(1));
        }

        assertThat(user.getId()).isGreaterThan(0);
        verify(userRepository, times(1)).save(user);

        userRepository.findById(user.getId()).ifPresentOrElse(
                foundUser -> {
                    assertEquals(user.getUsername(), foundUser.getUsername());
                    assertThat(user.getId()).isGreaterThan(0);
                },
                () -> fail("User not found in the database")
        );
    }

    @Test
    void quandoCreateReceberUmUsuarioComCampoInvalidoEntaoDeveLancarUserNotValidException() {
        var userToCreate = criaUserInvalido();
        UserNotValidException userNotValidException = assertThrows(UserNotValidException.class, () -> userService.create(userToCreate));
        verify(userRepository, times(0)).save(userToCreate);
        assertTrue(userNotValidException.getValidationFieldErrors().stream().anyMatch(
                fieldError -> fieldError.getField().equals("email") &&
                        fieldError.getDefaultMessage().equals("User must have a valid email.")
        ));
    }

    @Test
    void quandoFindByIdReceberUmIdValidoEntaoDeveRetornarUser() {
        User user = userService.create(instanciaUser());
        User userFound = userService.findById(user.getId());
        assertNotNull(userFound);
        assertEquals(userFound.getId(), user.getId());
        assertEquals(userFound.getEmail(), user.getEmail());
    }

    @Test
    void quandoFindByIdReceberUmIdNaoExistenteEntaoDeveLancarUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> userService.findById(999L));
    }

    @Test
    void quandoUpdateReceberUmIdNaoExistenteEntaoDeveLancarUserNotFoundException() {
        User user = instanciaUser();
        assertThrows(UserNotFoundException.class, () -> userService.update(999L, user));
    }

    @Test
    void quandoUpdateReceberUmUserInvalidoEntaoDeveLancarUserNotValidException() {
        User user = userService.create(instanciaUser());
        UserNotValidException userNotValidException = assertThrowsExactly(UserNotValidException.class, () -> userService.update(user.getId(), criaUserInvalido()));
        assertThatList(userNotValidException.getValidationFieldErrors()).isNotEmpty();
        assertThatList(userNotValidException.getValidationFieldErrors()).anyMatch(
                fieldError -> fieldError.getField().equals("email") &&
                        fieldError.getDefaultMessage().equals("User must have a valid email.")
        );
    }


    @Test
    void quandoUpdateReceberDadosValidosDeveAtualizarComSucesso() {
        User userDTO = instanciaUser();
        User user = userService.create(userDTO);
        user.setEmail("gustavin.gameplays@gmail.com");
        User userUpdated = userService.update(user.getId(), user);

        assertNotNull(userUpdated);
        assertEquals(userUpdated.getId(), user.getId());
        assertEquals(userUpdated.getEmail(), user.getEmail());
    }

    @Test
    void quandoFindAllForExecutadoDeveRetornarUmaListaDeUsuarios() {
        userService.create(instanciaUser());
        List<User> users = userService.findAll();
        assertThatList(users).isNotEmpty();
        users.stream().findFirst().ifPresent(user -> {
            assertThat(user.getId()).isGreaterThan(0);
            assertThat(user.getEmail()).isNotEmpty();
        });
    }

    @Test
    void quandoDeleteReceberUmIdValidoEntaoUserDeveEstarRemovido() {
        User dto = instanciaUser();
        User user = userService.create(dto);
        userService.delete(user.getId());
        assertThrowsExactly(UserNotFoundException.class, () -> userService.findById(user.getId()));
    }

    @Test
    void quandoDeleteReceberUmIdInvalidoEntaoDeveLancarUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> userService.delete(999L));
    }

    @Test
    void quandoUserCriadoComIdadeInvalidaParaPaisEntaoDeveLancarExcecaoUserNotValid() {
        User user = instanciaUser();
        user.setCountry("BR");
        user.setBirthDate(LocalDate.now().minusYears(10));
        UserNotValidException userNotValidException = assertThrows(UserNotValidException.class, () -> userService.create(user));
        assertTrue(userNotValidException.getValidationFieldErrors().stream().anyMatch(e -> e.getField().equals("birthDate")));
    }


    private User instanciaUser() {
        String email = "usuario@gmail.com";
        String username = "usuario";
        LocalDate birthdate = LocalDate.of(2005, 10, 7);
        String password = "senha123";
        String country = "BR";

        return User.builder()
                .email(email)
                .birthDate(birthdate)
                .username(username)
                .country(country)
                .password(password)
                .build();
    }

    private User criaUserInvalido() {
        String email = "usuario.gmail";
        String username = "usuario";
        LocalDate birthdate = LocalDate.of(2005, 10, 7);
        String password = "senha123";

        return User.builder()
                .email(email)
                .birthDate(birthdate)
                .username(username)
                .password(password)
                .build();
    }

}
