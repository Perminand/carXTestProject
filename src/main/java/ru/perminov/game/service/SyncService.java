package ru.perminov.game.service;

import ru.perminov.game.dto.data.UserSyncDataDto;
import ru.perminov.game.dto.data.UserSyncDataDtoIn;
import ru.perminov.game.model.UserSyncData;

import java.util.UUID;

public interface SyncService {
    UserSyncData createSynh(UUID uuid, UserSyncDataDtoIn userDataDto);

    UserSyncDataDto getData(String uuid);
}
