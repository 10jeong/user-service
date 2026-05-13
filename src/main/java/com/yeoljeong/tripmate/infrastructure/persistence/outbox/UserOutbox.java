package com.yeoljeong.tripmate.infrastructure.persistence.outbox;

import com.yeoljeong.tripmate.domain.Outbox;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_outbox")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOutbox extends Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    public static UserOutbox create(String topic, String payload) {
        UserOutbox outbox = new UserOutbox();
        Outbox.init(outbox, topic, payload);
        return outbox;
    }
}
