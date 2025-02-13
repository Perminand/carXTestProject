package ru.perminov.game.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.perminov.game.dto.activity.UserActivityDto;
import ru.perminov.game.dto.data.UserDataDto;
import ru.perminov.game.dto.data.UserDataDtoIn;
import ru.perminov.game.exception.error.EntityNotFoundException;
import ru.perminov.game.exception.error.ValidationException;
import ru.perminov.game.mapper.UserActivityMapper;
import ru.perminov.game.mapper.UserDataMapper;
import ru.perminov.game.model.UserActivity;
import ru.perminov.game.model.UserData;
import ru.perminov.game.repository.UserActivityRepository;
import ru.perminov.game.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DataServiceImpl implements DataService {

    private final UserRepository userRepository;
    private final UserDataMapper userDataMapper;
    private final UserActivityRepository userActivityRepository;
    private final UserActivityMapper userActivityMapper;

    @Override
    public void createSynh(UUID uuid, @Valid UserDataDtoIn userDataDto) {
        UserData userData = validateUserData(uuid);
        if (!userDataDto.country().equals(userData.getCountry())) {
            throw new ValidationException("The country field should not change");
        }
        UserData newData = userDataMapper.toUser(userDataDto, LocalDateTime.now());
        newData = userRepository.save(userData);
        log.info("userData save uuid: {}", newData.getId());
    }

    @Override
    public UserActivityDto createActivity(UUID uuid, UserActivityDto userActivityDto) {
        validateUserData(uuid);
        UserActivity userActivity = userActivityMapper.toUserActivity(userActivityDto, LocalDateTime.now(), uuid);
        userActivity = userActivityRepository.save(userActivity);
        log.info("Activity save, uuid: {}", uuid);
        return userActivityMapper.toDto(userActivity);
    }

    @Override
    public UserDataDto getData(UUID uuid) {
        UserData userData = validateUserData(uuid);
        return userDataMapper.toUserDto(userData);
    }

    private UserData validateUserData(UUID uuid) {
        return userRepository.findById(uuid.toString())
                .orElseThrow(() -> new EntityNotFoundException("There is no User with uuid: " + uuid));

    }
}
