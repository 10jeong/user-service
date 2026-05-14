package com.yeoljeong.tripmate.presentation.controller.internal;


import com.yeoljeong.tripmate.application.dto.result.UserSummaryResult;
import com.yeoljeong.tripmate.application.service.query.UserQueryService;
import com.yeoljeong.tripmate.presentation.dto.request.UserSummaryRequest;
import com.yeoljeong.tripmate.presentation.dto.response.UserSummaryResponse;
import com.yeoljeong.tripmate.response.ApiResponse;
import com.yeoljeong.tripmate.response.constants.CommonSuccessCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class UserInternalController {

    private final UserQueryService userQueryService;

    @PostMapping
    public ApiResponse<List<UserSummaryResponse>> getUsersByIds(@RequestBody UserSummaryRequest request) {
        List<UserSummaryResult> results = userQueryService.getUsersByIds(request.toCommand());
        return ApiResponse.success(CommonSuccessCode.OK, UserSummaryResponse.from(results));
    }

}
