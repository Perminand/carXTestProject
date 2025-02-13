package ru.perminov.game.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.perminov.game.dto.activity.UserActivityDto;
import ru.perminov.game.model.UserActivity;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper
public interface UserActivityMapper {

    @Mapping(target = "createActivity", source = "dateInput")
    @Mapping(target = "userCredential", source = "uuid")
    UserActivity toUserActivity(UserActivityDto userActivityDto, LocalDateTime dateInput, UUID uuid);

    UserActivityDto toDto(UserActivity userActivity);
}
