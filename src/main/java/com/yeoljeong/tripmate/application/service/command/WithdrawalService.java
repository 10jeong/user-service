package com.yeoljeong.tripmate.application.service.command;

import java.util.UUID;

public interface WithdrawalService {

    void withdraw(UUID userId, String role);
}
