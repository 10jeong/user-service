package com.yeoljeong.tripmate.presentation.dto.request;

import com.yeoljeong.tripmate.application.dto.command.UserCreateCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record UserCreateRequest(
    @Email
    @NotNull
    String email,
    @NotNull
    String name,
    @NotNull
    String password,
    @NotNull
    @Pattern(regexp = "MALE|FEMALE", message = "MALE 또는 FEMALE만 가능합니다.")
    String gender,
    @NotNull
    LocalDate birthDate,
    @NotNull
    @Pattern(regexp = "USER|SELLER", message = "USER 또는 SELLER만 가능합니다.")
    String role
) {
    public UserCreateCommand toCommand() {
        return new UserCreateCommand(email, name, password, gender, birthDate, role);
    }
}
