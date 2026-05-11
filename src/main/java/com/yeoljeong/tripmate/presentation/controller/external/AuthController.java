package com.yeoljeong.tripmate.presentation.controller.external;

import com.yeoljeong.tripmate.application.dto.result.LoginResult;
import com.yeoljeong.tripmate.application.service.auth.AuthService;
import com.yeoljeong.tripmate.presentation.dto.request.LoginRequest;
import com.yeoljeong.tripmate.presentation.dto.request.RefreshTokenRequest;
import com.yeoljeong.tripmate.response.ApiResponse;
import com.yeoljeong.tripmate.response.constants.CommonSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResult> login(
        @Valid @RequestBody LoginRequest request) {
        LoginResult result = authService.login(request.toCommand());
        return ApiResponse.success(CommonSuccessCode.OK, result);
    }

    @PostMapping("/refresh")
    public ApiResponse<LoginResult> refresh(
        @Valid @RequestBody RefreshTokenRequest request) {
        LoginResult result = authService.refreshToken(request.toCommand());
        return ApiResponse.success(CommonSuccessCode.OK, result);
    }
}
