package com.yeoljeong.tripmate.presentation.controller.external;

import com.yeoljeong.tripmate.application.dto.result.UserCreateResult;
import com.yeoljeong.tripmate.application.service.command.UserCommandService;
import com.yeoljeong.tripmate.presentation.dto.request.UserCreateRequest;
import com.yeoljeong.tripmate.presentation.dto.response.UserInfoResponse;
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
@RequestMapping("/users")
public class UserController {

    private final UserCommandService userCommandService;

    @PostMapping
    public ApiResponse<UserInfoResponse> createMember(
        @Valid @RequestBody UserCreateRequest request) {
        UserCreateResult result = userCommandService.registerUser(request.toCommand());
        return ApiResponse.success(CommonSuccessCode.CREATE, UserInfoResponse.from(result));
    }
}
