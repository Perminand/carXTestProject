package ru.perminov.game.dto.data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserSyncDataDto(

        @Positive(message = "The data must not be less than 0")
        Integer money,

        @NotNull(message = "The field must not be null")
        @Size(min = 2, max = 10, message = "The number of characters must be at least 2 and no more than 10")
        String country
) {
}
