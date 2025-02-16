package ru.perminov.game.service;

import jakarta.transaction.Transactional;
import ru.perminov.game.dto.activity.UserActivityDataDto;

import java.util.UUID;

public interface ActivityService {

    @Transactional
    UserActivityDataDto createActivity(UUID uuid, Long activity);

}
