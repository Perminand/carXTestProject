package ru.perminov.game.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.perminov.game.mapper.UserActivityStatDataMapper;
import ru.perminov.game.model.UserSyncData;
import ru.perminov.game.repository.UserActivityRepository;
import ru.perminov.game.repository.UserSyncRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class StatServiceImplTest {

    UUID uuid1;
    UUID uuid2;
    UUID uuid3;

    @Mock
    private UserActivityRepository userActivityRepository;

    @Mock
    private UserSyncRepository userSyncRepository;
    @Mock
    private UserActivityStatDataMapper userActivityStatDataMapper;
    @InjectMocks
    private StatServiceImpl statServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        uuid1 = UUID.randomUUID();
        uuid2 = UUID.randomUUID();
        uuid3 = UUID.randomUUID();
    }

    @Test
    void testCountNewUsersByCountry() {
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2023, 1, 31);
        List<UUID> userIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        List<UserSyncData> users = Arrays.asList(
                UserSyncData.builder().id(uuid1).country("CountryA").money(100).build(),
                UserSyncData.builder().id(uuid2).country("CountryB").money(100).build()
        );

        when(userActivityRepository.findUserIdsWithActivityInPeriod(start, end)).thenReturn(userIds);
        when(userSyncRepository.findAllByIdIn(userIds)).thenReturn(users);

        Map<String, Long> result = statServiceImpl.countNewUsersByCountry(start, end);

        assertEquals(1L, result.get("CountryA"));
        assertEquals(1L, result.get("CountryB"));
    }

    @Test
    void testGetTopUsersByCountry() {
        String country = "CountryA";

        int count = 2;
        List<UserSyncData> allUsers = Arrays.asList(
                UserSyncData.builder().id(uuid1).country("CountryA").money(100).build(),
                UserSyncData.builder().id(uuid2).country("CountryB").money(500).build(),
                UserSyncData.builder().id(uuid3).country("CountryA").money(50).build()
        );

        when(userSyncRepository.findAll()).thenReturn(allUsers);

        List<UserSyncData> result = statServiceImpl.getTopUsersByCountry(country, count);

        assertEquals(2, result.size());
        assertEquals(uuid1, result.get(0).getId());
        assertEquals(uuid3, result.get(1).getId());
    }
}