package ru.perminov.game.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.perminov.game.model.UserData;


public interface UserRepository extends KeyValueRepository<UserData, String> {
}
