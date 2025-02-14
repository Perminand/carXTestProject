package ru.perminov.game.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
@Setter
@RedisHash("users")
@ToString
public class UserSyncData {

    @Id
    private UUID id;

    private Integer money;

    private String country;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserSyncData that = (UserSyncData) o;
        return Objects.equals(money, that.money) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(money, country);
    }
}
