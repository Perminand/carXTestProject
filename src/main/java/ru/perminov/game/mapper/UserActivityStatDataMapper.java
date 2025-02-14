package ru.perminov.game.mapper;

import org.mapstruct.Mapper;
import ru.perminov.game.dto.statistic.UserActivityStatDto;
import ru.perminov.game.model.UserActivityData;

@Mapper(componentModel = "spring")
public interface UserActivityStatDataMapper {

    UserActivityStatDto toUserDto(UserActivityData userSyncData);



}
