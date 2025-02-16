package ru.perminov.game.dto.data;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserSyncDataDto(

        UUID id,

        Integer money,

        String country
) {
}
