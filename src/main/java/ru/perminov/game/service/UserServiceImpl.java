package ru.perminov.game.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.perminov.game.dto.UserDto;
import ru.perminov.game.mapper.UserMapper;
import ru.perminov.game.model.UserData;
import ru.perminov.game.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void create(UserDto userDto) {
        UserData userData = userMapper.toUser(userDto);
        userData = userRepository.save(userData);
        log.info("users save {}", userMapper.toUserDto(userData));
    }
}
