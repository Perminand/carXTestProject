package ru.perminov.game.service;

import ru.perminov.game.model.UserData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StatService {
    Map<String, List<UserData>> getMoneyData();

    Map<String, Long> countNewUsersByCountry(LocalDateTime start, LocalDateTime end);
}
