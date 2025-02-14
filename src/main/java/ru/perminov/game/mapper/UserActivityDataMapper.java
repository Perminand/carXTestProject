package ru.perminov.game.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.perminov.game.dto.activity.UserActivityDataDto;
import ru.perminov.game.model.UserActivityData;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserActivityDataMapper {

    @Mapping(target = "createActivity", source = "dateInput")
    UserActivityData toUserActivity(UserActivityDataDto userActivityDataDto, LocalDateTime dateInput);

    UserActivityDataDto toDto(UserActivityData userActivityData);
}
