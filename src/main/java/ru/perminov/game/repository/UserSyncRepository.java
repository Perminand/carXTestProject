package ru.perminov.game.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.perminov.game.model.UserSyncData;

import java.util.List;
import java.util.UUID;


public interface UserSyncRepository extends KeyValueRepository<UserSyncData, String> {
    List<UserSyncData> findAllByIdIn(List<UUID> listUuid);
}
