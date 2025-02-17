package ru.perminov.game.service;

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

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncServiceImpl implements SyncService {

    private final UserSyncRepository userSyncRepository;

    private final UserSyncDataMapper userSyncDataMapper;

    @Override
    public void createSynh(UUID uuid, UserSyncDataDtoIn userDataDto) {
        UserSyncData userSyncData = userSyncRepository.findById(uuid.toString())
                .orElse(UserSyncData.builder().id(uuid).money(userDataDto.money()).country(userDataDto.country()).build());
        if (!userDataDto.country().equals(userSyncData.getCountry())) {
            throw new ValidationException("The country field should not change");
        }
        UserSyncData newData;
        newData = userSyncRepository.save(userSyncData);
        log.info("userData saved uuid: {}", newData.getId());
    }

    @Override
    public UserSyncDataDto getData(String uuid) {
        UserSyncData userSyncData = userSyncRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("no uuid for request"));
        return userSyncDataMapper.toUserDto(userSyncData);
    }

}
