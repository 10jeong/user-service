package com.yeoljeong.tripmate.presentation.controller.external;

import com.yeoljeong.tripmate.application.dto.result.UserCreateResult;
import com.yeoljeong.tripmate.application.dto.result.UserDetailsResult;
import com.yeoljeong.tripmate.application.service.command.UserCommandService;
import com.yeoljeong.tripmate.application.service.command.WithdrawalService;
import com.yeoljeong.tripmate.application.service.query.UserQueryService;
import com.yeoljeong.tripmate.auth.annotation.LoginUser;
import com.yeoljeong.tripmate.auth.context.UserContext;
import com.yeoljeong.tripmate.presentation.dto.request.UserCreateRequest;
import com.yeoljeong.tripmate.presentation.dto.response.UserDetailsResponse;
import com.yeoljeong.tripmate.presentation.dto.response.UserInfoResponse;
import com.yeoljeong.tripmate.response.ApiResponse;
import com.yeoljeong.tripmate.response.constants.CommonSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final WithdrawalService withdrawalService;

    @PostMapping("/signup")
    public ApiResponse<UserInfoResponse> createUser(
        @Valid @RequestBody UserCreateRequest request) {
        UserCreateResult result = userCommandService.registerUser(request.toCommand());
        return ApiResponse.success(CommonSuccessCode.CREATE, UserInfoResponse.from(result));
    }

    @GetMapping("/me")
    public ApiResponse<UserDetailsResponse> getUserDetails(@LoginUser UserContext userContext) {
        UserDetailsResult result = userQueryService.getUserDetails(userContext.userId());
        return ApiResponse.success(CommonSuccessCode.OK, UserDetailsResponse.from(result));
    }

    @DeleteMapping("/me")
    public ApiResponse<Void> withdrawUser(@LoginUser UserContext userContext) {
        withdrawalService.withdraw(userContext.userId());
        return ApiResponse.success(CommonSuccessCode.OK, null);
    }
}
