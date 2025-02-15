package ru.perminov.game.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class UserActivityData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private UUID userUuid;
    private LocalDateTime createActivity;
    @Column(nullable = false)
    private Long activity;

    public UserActivityData() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserActivityData that = (UserActivityData) o;
        return Objects.equals(userUuid, that.userUuid) && Objects.equals(createActivity, that.createActivity) && Objects.equals(activity, that.activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userUuid, createActivity, activity);
    }
}
