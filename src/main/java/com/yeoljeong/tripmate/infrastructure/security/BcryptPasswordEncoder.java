package com.yeoljeong.tripmate.infrastructure.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.yeoljeong.tripmate.application.port.PasswordEncoderPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BcryptPasswordEncoder implements PasswordEncoderPort {

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.verifyer()
            .verify(rawPassword.toCharArray(), encodedPassword)
            .verified;
    }

    @Override
    public String encode(String rawPassword) {
        return BCrypt.withDefaults()
            .hashToString(12, rawPassword.toCharArray());
    }
}
