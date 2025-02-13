package ru.perminov.game.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.perminov.game.dto.statistic.UserActivityStatDto;
import ru.perminov.game.model.UserData;
import ru.perminov.game.repository.UserActivityRepository;
import ru.perminov.game.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final UserRepository userRepository;
    private final UserActivityRepository userActivityRepository;

    @Override
    public Map<String, List<UserData>> getMoneyData() {
        List<UserData> userDataList = new ArrayList<>(userRepository.findAll());
        userDataList.sort(Comparator.comparing(UserData::getMoney));

        return userDataList
                .stream()
                .collect(Collectors.groupingBy(UserData::getCountry));
    }

    @Override
    public Map<String, Long> countNewUsersByCountry(LocalDateTime start, LocalDateTime end) {
        return userActivityRepository.countNewUsersByCountry(start, end);
    }

    public List<Map<String, UserActivityStatDto>> getActivityForUser(UUID userCredential, LocalDateTime start, LocalDateTime end) {
        return userActivityRepository.findActivityByUserCredentialAndPeriod(userCredential, start, end);
    }
}
