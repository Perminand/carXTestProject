package ru.perminov.game.service;

import ru.perminov.game.dto.data.UserSyncDataDto;
import ru.perminov.game.dto.data.UserSyncDataDtoIn;

import java.util.UUID;

public interface SyncService {
    void createSynh(UUID uuid, UserSyncDataDtoIn userDataDto);

    UserSyncDataDto getData(UUID uuid);
}
