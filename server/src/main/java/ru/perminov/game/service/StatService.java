package ru.perminov.game.service;

import ru.perminov.game.dto.statistic.UserActivityStatDto;
import ru.perminov.game.model.UserSyncData;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface StatService {
    Map<String, Long> countNewUsersByCountry(LocalDate start, LocalDate end);

    List<UserActivityStatDto> findActivityByUserAndPeriod(UUID userCredential, LocalDate start, LocalDate end);

    List<UserSyncData> getTopUsersByCountry(String country, int count);
}
