package ru.perminov.game.dto.statistic;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserActivityStatDto(

        Long activity,

        LocalDateTime createActivity
) {
}
