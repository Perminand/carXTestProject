package ru.perminov.game.service;

import jakarta.transaction.Transactional;
import ru.perminov.game.dto.activity.UserActivityDto;
import ru.perminov.game.dto.data.UserDataDto;
import ru.perminov.game.dto.data.UserDataDtoIn;

import java.util.UUID;

public interface DataService {
    void createSynh(UUID uuid, UserDataDtoIn userDataDto);

    @Transactional
    UserActivityDto createActivity(UUID uuid, UserActivityDto userActivityDto);

    UserDataDto getData(UUID uuid);

}
