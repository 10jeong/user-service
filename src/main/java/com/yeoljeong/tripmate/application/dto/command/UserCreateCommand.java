package com.yeoljeong.tripmate.application.dto.command;

import java.time.LocalDate;

public record UserCreateCommand(String email, String name, String password, String gender, LocalDate birthDate, String role) {

}

