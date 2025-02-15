package ru.perminov.game.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.perminov.game.dto.statistic.UserActivityStatDto;
import ru.perminov.game.mapper.UserActivityStatDataMapper;
import ru.perminov.game.model.UserSyncData;
import ru.perminov.game.repository.UserActivityRepository;
import ru.perminov.game.repository.UserSyncRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final UserSyncRepository userSyncRepository;
    private final UserActivityRepository userActivityRepository;
    private final UserActivityStatDataMapper userActivityStatDataMapper;


    @Override
    public Map<String, Long> countNewUsersByCountry(LocalDate start, LocalDate end) {
        List<UUID> listUuid = userActivityRepository.findUserIdsWithActivityInPeriod(start, end);


        return listUuid.stream()
                .map(id -> userSyncRepository.findById(id.toString()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(UserSyncData::getCountry, Collectors.counting()));
    }

    @Override
    public List<UserActivityStatDto> findActivityByUserAndPeriod(UUID userCredential, LocalDate start, LocalDate end) {
        return userActivityRepository.findActivityByUserAndPeriod(userCredential, start, end).stream()
                .map(userActivityStatDataMapper::toUserDto).toList();
    }

    @Override
    public List<UserSyncData> getTopUsersByCountry(String country, int count) {
        Iterable<UserSyncData> allUsers = userSyncRepository.findAll();
        List<UserSyncData> usersInCountry = new ArrayList<>();

        for (UserSyncData user : allUsers) {
            if (country.equals(user.getCountry())) {
                usersInCountry.add(user);
            }
        }

        usersInCountry.sort((u1, u2) -> u2.getMoney().compareTo(u1.getMoney()));

        return usersInCountry.stream().limit(count).collect(Collectors.toList());
    }
}

