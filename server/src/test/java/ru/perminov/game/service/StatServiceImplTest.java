package ru.perminov.game.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.perminov.game.dto.statistic.UserActivityStatDto;
import ru.perminov.game.mapper.UserActivityStatDataMapper;
import ru.perminov.game.model.UserActivityData;
import ru.perminov.game.model.UserSyncData;
import ru.perminov.game.repository.UserActivityRepository;
import ru.perminov.game.repository.UserSyncRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void testCountNewUsersByCountry_WithData() {
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2023, 1, 31);

        when(userActivityRepository.findUserIdsWithActivityInPeriod(start, end))
                .thenReturn(Arrays.asList(uuid1, uuid2));

        UserSyncData user1 = UserSyncData.builder().id(uuid1).country("CountryA").build();
        UserSyncData user2 = UserSyncData.builder().id(uuid2).country("CountryB").build();

        when(userSyncRepository.findById(uuid1.toString())).thenReturn(Optional.of(user1));
        when(userSyncRepository.findById(uuid2.toString())).thenReturn(Optional.of(user2));

        Map<String, Long> result = statServiceImpl.countNewUsersByCountry(start, end);

        assertEquals(2, result.size());
        assertEquals(1L, result.get("CountryA"));
        assertEquals(1L, result.get("CountryB"));
    }

    @Test
    void testCountNewUsersByCountry_NoData() {
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2023, 1, 31);

        when(userActivityRepository.findUserIdsWithActivityInPeriod(start, end))
                .thenReturn(List.of());

        Map<String, Long> result = statServiceImpl.countNewUsersByCountry(start, end);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindActivityByUserAndPeriod_WithData() {
        LocalDate start = LocalDate.of(2025, 2, 1);
        LocalDate end = LocalDate.of(2025, 2, 28);

        List<UserActivityData> userActivityDataList = Arrays.asList(
                new UserActivityData(1L, uuid1, LocalDateTime.now(), 100L),
                new UserActivityData(2L, uuid1, LocalDateTime.now(), 200L)
        );
        when(userActivityRepository.findActivityByUserAndPeriod(uuid1, start, end))
                .thenReturn(userActivityDataList);

        UserActivityStatDto dto1 = UserActivityStatDto.builder().activity(100L).createActivity(LocalDateTime.now()).build();
        UserActivityStatDto dto2 = UserActivityStatDto.builder().activity(200L).createActivity(LocalDateTime.now()).build();
        when(userActivityStatDataMapper.toUserDto(userActivityDataList.get(0))).thenReturn(dto1);
        when(userActivityStatDataMapper.toUserDto(userActivityDataList.get(1))).thenReturn(dto2);

        List<UserActivityStatDto> result = statServiceImpl.findActivityByUserAndPeriod(uuid1, start, end);

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
    }

    @Test
    void testFindActivityByUserAndPeriod_NoData() {
        LocalDate start = LocalDate.of(2025, 2, 1);
        LocalDate end = LocalDate.of(2025, 2, 28);

        when(userActivityRepository.findActivityByUserAndPeriod(uuid1, start, end))
                .thenReturn(List.of());

        List<UserActivityStatDto> result = statServiceImpl.findActivityByUserAndPeriod(uuid1, start, end);

        assertTrue(result.isEmpty());
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