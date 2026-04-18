package com.gustavodscruz.livranada.utils;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("test")
public class SecurityUtilsTest {

    /**
     * Testa se o método hashString retorna um hash diferente do valor original e não é vazio.
     */
    @Test
    void quandoHashStringReceberUmTextoDeveDevolverUmHash() {
        String value = "password123";
        String hash = SecurityUtils.hashString(value);
        log.debug("Valor original: {}, Hash gerado: {}", value, hash);
        assert !hash.isEmpty() && !hash.equals(value);
    }

}
