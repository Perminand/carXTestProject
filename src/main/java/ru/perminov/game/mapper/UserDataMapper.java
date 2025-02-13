package ru.perminov.game.mapper;

import org.mapstruct.Mapper;
import ru.perminov.game.dto.data.UserDataDto;
import ru.perminov.game.dto.data.UserDataDtoIn;
import ru.perminov.game.model.UserData;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserDataMapper {

    UserDataDto toUserDto(UserData userData);

    UserData toUser(UserDataDtoIn userDataDto, LocalDateTime dateTimeInput);
}
