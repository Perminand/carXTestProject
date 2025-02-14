package ru.perminov.game.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.perminov.game.dto.data.UserSyncDataDto;
import ru.perminov.game.dto.data.UserSyncDataDtoIn;
import ru.perminov.game.exception.error.EntityNotFoundException;
import ru.perminov.game.exception.error.ValidationException;
import ru.perminov.game.mapper.UserSyncDataMapper;
import ru.perminov.game.model.UserSyncData;
import ru.perminov.game.repository.UserSyncRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SyncServiceImpl implements SyncService {

    private final UserSyncRepository userSyncRepository;
    private final UserSyncDataMapper userSyncDataMapper;


    @Override
    public void createSynh(UUID uuid, UserSyncDataDtoIn userDataDto) {
        UserSyncData userSyncData = validateUserData(uuid);
        if (!userDataDto.country().equals(userSyncData.getCountry())) {
            throw new ValidationException("The country field should not change");
        }
        UserSyncData newData = userSyncDataMapper.toUser(userDataDto, LocalDateTime.now());
        newData = userSyncRepository.save(userSyncData);
        log.info("userData save uuid: {}", newData.getId());
    }

    @Override
    public UserSyncDataDto getData(UUID uuid) {
        UserSyncData userSyncData = validateUserData(uuid);
        return userSyncDataMapper.toUserDto(userSyncData);
    }

    private UserSyncData validateUserData(UUID uuid) {
        return userSyncRepository.findById(uuid.toString())
                .orElseThrow(() -> new EntityNotFoundException("There is no User with uuid: " + uuid));

    }
}
