package ru.perminov.game.mapper;

import org.mapstruct.Mapper;
import ru.perminov.game.dto.data.UserSyncDataDto;
import ru.perminov.game.dto.data.UserSyncDataDtoIn;
import ru.perminov.game.model.UserSyncData;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserSyncDataMapper {

    UserSyncDataDto toUserDto(UserSyncData userSyncData);

    UserSyncData toUser(UserSyncDataDtoIn userDataDto, LocalDateTime dateTimeInput);
}
