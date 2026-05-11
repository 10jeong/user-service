package com.yeoljeong.tripmate.application.port;

public interface PasswordEncoderPort {

    boolean matches(String rawPassword, String encodedPassword);

    String encode(String rawPassword);

}
