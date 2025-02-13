package ru.perminov.game.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.perminov.game.model.UserActivity;
import ru.perminov.game.model.UserData;
import ru.perminov.game.repository.UserActivityRepository;
import ru.perminov.game.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@Slf4j
@ExtendWith(MockitoExtension.class)
class StatServiceImplTest {

    List<UserData> userDataList;
    UserData userData1 = UserData.builder().id(UUID.randomUUID()).money(1000).country("RU").build();
    UserData userData2 = UserData.builder().id(UUID.randomUUID()).money(500).country("EN").build();
    UserData userData3 = UserData.builder().id(UUID.randomUUID()).money(2000).country("RU").build();
    UserData userData4 = UserData.builder().id(UUID.randomUUID()).money(200).country("EN").build();


    List<UserActivity> userActivityList;
    UserActivity userActivity1 = UserActivity
            .builder().uuid(userData1.getId()).createActivity(LocalDateTime.now()).activity(1000L).build();
    UserActivity userActivity2 = UserActivity
            .builder().uuid(userData2.getId()).createActivity(LocalDateTime.now()).activity(1000L).build();
    UserActivity userActivity3 = UserActivity
            .builder().uuid(userData3.getId()).createActivity(LocalDateTime.now().minusMonths(2)).activity(1000L).build();
    UserActivity userActivity4 = UserActivity
            .builder().uuid(userData4.getId()).createActivity(LocalDateTime.now()).activity(1000L).build();


    @Mock
    private UserRepository userRepository;

    @Mock
    private UserActivityRepository userActivityRepository;

    @Autowired
    @InjectMocks
    private StatServiceImpl statService;

    @BeforeEach
    void setUp() {

        userDataList = List.of(userData1, userData2, userData3, userData4);

        MockMvc mvc = MockMvcBuilders
                .standaloneSetup(userRepository)
                .build();
    }

    @Test
    void getMoneyData() {
        Mockito.when(userRepository.findAll()).thenReturn(userDataList);
        Map<String, List<UserData>> listResult = statService.getMoneyData();
        Assertions.assertFalse(listResult.isEmpty());
        log.info("List is not empty");
        Assertions.assertEquals(2, listResult.size());
        log.info("List size 2");
        Assertions.assertEquals(Arrays.asList(userData4, userData2), listResult.get("EN"));
        Assertions.assertEquals(Arrays.asList(userData1, userData3), listResult.get("RU"));
        log.info("List sort equals");
    }

    @Test
    void countNewUsersByCountry() {
        Mockito.when(userActivityRepository.countNewUsersByCountry(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Map.of(
                        UUID.randomUUID().toString(), 100L,
                        UUID.randomUUID().toString(), 50L)
                );

        Map<String, Long> newUserMap = statService.countNewUsersByCountry(
                LocalDateTime.now().minusMonths(1),
                LocalDateTime.now()
        );
        System.out.println(newUserMap);
        Assertions.assertFalse(newUserMap.isEmpty());


    }

    @Test
    void getActivityForUser() {
    }
}