package ru.perminov.game.mapper;

import org.mapstruct.Mapper;
import ru.perminov.game.dto.UserDto;
import ru.perminov.game.model.UserData;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(UserData userData);

    UserData toUser(UserDto userDto);
}
