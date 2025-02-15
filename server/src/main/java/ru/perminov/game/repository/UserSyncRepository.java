package ru.perminov.game.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.perminov.game.model.UserSyncData;


public interface UserSyncRepository extends KeyValueRepository<UserSyncData, String> {
}
