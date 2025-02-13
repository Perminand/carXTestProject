package ru.perminov.game.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
@Cacheable("SynhData")
public class SyncServiceImpl implements SyncService {

    private final UserSyncRepository userSyncRepository;
    private final UserSyncDataMapper userSyncDataMapper;


    @Override
    @CachePut(value = "SynhData", key = "#result.uuid")
    public UserSyncData createSynh(UUID uuid, UserSyncDataDtoIn userDataDto) {
        UserSyncData userSyncData = userSyncRepository.findById(uuid.toString())
                .orElse(UserSyncData.builder().uuid(uuid).money(userDataDto.money()).country(userDataDto.country()).build());
        if (!userDataDto.country().equals(userSyncData.getCountry())) {
            throw new ValidationException("The country field should not change");
        }
        UserSyncData newData = userSyncDataMapper.toUser(userDataDto, LocalDateTime.now());
        newData = userSyncRepository.save(userSyncData);
        log.info("userData saved uuid: {}", newData.getUuid());
        return newData;
    }

    @Override
    public UserSyncDataDto getData(String uuid) {
        UserSyncData userSyncData = userSyncRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("no uuid for request"));
        System.out.println("123");
        return userSyncDataMapper.toUserDto(userSyncData);
    }

}
