package com.yeoljeong.tripmate.application.port;

import com.yeoljeong.tripmate.application.event.UserCreatedEvent;

public interface UserEventPublisher {

    void publish(UserCreatedEvent event);

}
