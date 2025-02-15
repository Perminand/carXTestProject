package ru.perminov.game.dto.activity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserActivityDataDto(

        Long id,

        UUID userUuid,

        LocalDateTime createActivity,

        @NotNull(message = "The field must not be null")
        @Positive(message = "The data must not be less than 0")
        Long activity) {
}
