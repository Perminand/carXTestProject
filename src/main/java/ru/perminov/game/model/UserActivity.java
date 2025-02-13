package ru.perminov.game.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private LocalDateTime createActivity;

    @Column(nullable = false, unique = true)
    private UUID userCredential;

    @Column(nullable = false)
    private Long activity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserActivity that = (UserActivity) o;
        return uuid != null && uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return getUuid() != null ? getUuid().hashCode() : 0;
    }
}
