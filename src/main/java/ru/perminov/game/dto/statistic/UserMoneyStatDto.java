package ru.perminov.game.dto.statistic;

import java.util.UUID;

public record UserMoneyStatDto(
        UUID uuid,
        Integer money
) {
}
