package ru.perminov.game.mapper;

import org.mapstruct.Mapper;
import ru.perminov.game.dto.activity.UserActivityDataDto;
import ru.perminov.game.model.UserActivityData;

@Mapper(componentModel = "spring")
public interface UserActivityDataMapper {


    UserActivityDataDto toDto(UserActivityData userActivityData);
}
