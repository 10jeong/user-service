package com.yeoljeong.tripmate.presentation.dto.request;

import com.yeoljeong.tripmate.application.dto.result.LoginCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @Email
    String email,
    @NotBlank
    String password
) {

    public LoginCommand toCommand() {
        return new LoginCommand(email, password);
    }
}
