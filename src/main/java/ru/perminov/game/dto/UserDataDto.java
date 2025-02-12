package ru.perminov.game.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserDataDto(
        @Positive
        Integer money,

        @NotNull
        @Size(min = 2, max = 10)
        String country
) {
}
