package ru.perminov.game.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.perminov.game.dto.statistic.UserActivityStatDto;
import ru.perminov.game.model.UserData;
import ru.perminov.game.service.StatService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StatControllerTest {

    ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    List<UserData> userDataList;
    UserData userData1 = UserData.builder().id(UUID.randomUUID()).money(1000).country("RU").build();
    UserData userData2 = UserData.builder().id(UUID.randomUUID()).money(500).country("EN").build();
    UserData userData3 = UserData.builder().id(UUID.randomUUID()).money(2000).country("RU").build();
    UserData userData4 = UserData.builder().id(UUID.randomUUID()).money(200).country("EN").build();

    @Mock
    private StatService statService;
    @InjectMocks
    private StatController statController;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(statController).build();
    }

    @Test
    void testGetUserMoneyOk() throws Exception {
        userDataList = List.of(userData1, userData2, userData3, userData4);
        Map<String, List<UserData>> expectedData = new HashMap<>(userDataList
                .stream()
                .collect(Collectors.groupingBy(UserData::getCountry)));

        when(statService.getMoneyData()).thenReturn(expectedData);
        Map<String, List<UserData>> result = statController.getUserMoney();
        assertEquals(expectedData, result);
        verify(statService).getMoneyData();

        mvc.perform(get("/stat/money")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void getNewUser() throws Exception {
        Mockito.when(statService.countNewUsersByCountry(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Map.of("RU", 1000L));

        mvc.perform(get("/stat/new-user")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("start", LocalDateTime.now().minusMonths(1).toString())
                        .param("end", LocalDateTime.now().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getActivityForUser() throws Exception {
        Map<String, UserActivityStatDto> userActivitiList = Map.of(UUID.randomUUID().toString(), UserActivityStatDto.builder().activity(1000L).createActivity(LocalDateTime.now()).build());
        Mockito.when(statService.getActivityForUser(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(userActivitiList));

        mvc.perform(get("/stat/" + UUID.randomUUID() + "/activity")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("start", LocalDateTime.now().minusMonths(1).toString())
                        .param("end", LocalDateTime.now().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}