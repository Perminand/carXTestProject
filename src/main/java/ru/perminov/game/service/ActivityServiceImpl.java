package ru.perminov.game.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.perminov.game.dto.activity.UserActivityDataDto;
import ru.perminov.game.exception.error.EntityNotFoundException;
import ru.perminov.game.mapper.UserActivityDataMapper;
import ru.perminov.game.model.UserActivityData;
import ru.perminov.game.repository.UserActivityRepository;
import ru.perminov.game.repository.UserSyncRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ActivityServiceImpl implements ActivityService {

    private final UserActivityRepository userActivityRepository;
    private final UserActivityDataMapper userActivityDataMapper;
    private final UserSyncRepository userSyncRepository;


    @Override
    public UserActivityDataDto createActivity(UUID uuid, UserActivityDataDto userActivityDataDto) {
        validateUserData(uuid);
        UserActivityData userActivityData = userActivityDataMapper.toUserActivity(userActivityDataDto, LocalDateTime.now());
        userActivityData = userActivityRepository.save(userActivityData);
        log.info("Activity save, uuid: {}", uuid);
        return userActivityDataMapper.toDto(userActivityData);
    }

    private void validateUserData(UUID uuid) {
        userSyncRepository.findById(uuid.toString())
                .orElseThrow(() -> new EntityNotFoundException("There is no User with uuid: " + uuid));

    }


}
