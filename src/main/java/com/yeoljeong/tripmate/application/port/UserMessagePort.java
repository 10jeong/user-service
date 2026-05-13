package com.yeoljeong.tripmate.application.port;

import com.yeoljeong.tripmate.event.UserLogoutEvent;

public interface UserMessagePort {

    void publish(UserLogoutEvent event);

}
