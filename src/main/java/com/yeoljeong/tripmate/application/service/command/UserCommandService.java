package com.yeoljeong.tripmate.application.service.command;

import com.yeoljeong.tripmate.application.dto.command.UserCreateCommand;
import com.yeoljeong.tripmate.application.dto.result.UserCreateResult;


public interface UserCommandService {

    UserCreateResult registerUser(UserCreateCommand command);
}
