package com.rpgsystem.zero;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class ZeroApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoadsAndConfiguresBcryptPasswordEncoder() {
        assertNotNull(passwordEncoder);
        String raw = "SenhaForte#2026";
        String encoded = passwordEncoder.encode(raw);

        assertNotEquals(raw, encoded);
        assertTrue(encoded.startsWith("$2"));
        assertTrue(passwordEncoder.matches(raw, encoded));
        assertFalse(passwordEncoder.matches("senha-errada", encoded));
    }
}
