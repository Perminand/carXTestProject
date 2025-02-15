package ru.perminov.game.service;

import ru.perminov.game.dto.data.UserSyncDataDtoIn;
import ru.perminov.game.model.UserSyncData;

import java.util.UUID;

public interface SyncService {
    void createSynh(UUID uuid, UserSyncDataDtoIn userDataDto);

    UserSyncData getData(String uuid);
}
