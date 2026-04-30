package com.yeoljeong.tripmate.application.service.auth;

import com.yeoljeong.tripmate.application.dto.command.RefreshTokenCommand;
import com.yeoljeong.tripmate.application.dto.result.LoginCommand;
import com.yeoljeong.tripmate.application.dto.result.LoginResult;

public interface AuthService {

    LoginResult login(LoginCommand command);
    LoginResult refreshToken(RefreshTokenCommand command);

}
