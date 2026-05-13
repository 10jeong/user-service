package com.yeoljeong.tripmate.application.port;


import com.yeoljeong.tripmate.event.UserCreatedEvent;
import com.yeoljeong.tripmate.event.UserWithdrawalEvent;

public interface UserEventPublisher {

    void publish(UserCreatedEvent event);

    void publish(UserWithdrawalEvent event);
}
