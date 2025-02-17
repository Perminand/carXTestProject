package ru.perminov.game.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.perminov.game.dto.data.UserSyncDataDto;
import ru.perminov.game.model.UserSyncData;

@Mapper(componentModel = "spring")
public interface UserSyncDataMapper {

    @Mapping(target = "id", source = "id")
    UserSyncDataDto toUserDto(UserSyncData userSyncData);

}
