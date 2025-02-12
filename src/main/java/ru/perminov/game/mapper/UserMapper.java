package ru.perminov.game.mapper;

import org.mapstruct.Mapper;
import ru.perminov.game.dto.UserDataDto;
import ru.perminov.game.model.UserData;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDataDto toUserDto(UserData userData);

    UserData toUser(UserDataDto userDataDto);
}
