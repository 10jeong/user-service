package com.yeoljeong.tripmate.domain.exception;

import com.yeoljeong.tripmate.exception.constants.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "올바르지 않은 이메일 형식입니다."),
    ALREADY_EXIST_EMAIL(HttpStatus.CONFLICT, "이미 가입되어 있는 이메일입니다."),
    NOT_FOUND_USER(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token입니다."),
    HAS_ACTIVE_DATA(HttpStatus.CONFLICT, "진행 중인 거래가 있어 탈퇴할 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public int getCode() {
        return this.status.value();
    }
}
