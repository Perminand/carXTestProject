package ru.practicum.game.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.game.controller.model.UserSyncData;
import ru.practicum.game.controller.stat.StatClient;
import ru.practicum.game.controller.stat.StatController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class StatControllerTest {

    @Mock
    private StatClient statClient;

    @InjectMocks
    private StatController statController;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(statController).build();
    }

    @Test
    void testGetTopUsersByCountry_Success() {

        String country = "CountryA";
        int count = 2;

        List<UserSyncData> topUsers = Arrays.asList(
                UserSyncData.builder().id(UUID.randomUUID()).country(country).money(100).build(),
                UserSyncData.builder().id(UUID.randomUUID()).country(country).money(200).build());

        when(statClient.getTopUsersByCountry(anyString(), anyInt())).thenReturn(ResponseEntity.ok(topUsers));
        Assertions.assertEquals(statClient.getTopUsersByCountry("RU", count), ResponseEntity.ok(topUsers));
    }

    @Test
    void testGetTopUsersByCountry_InvalidCountry() throws Exception {
        String country = "C";
        int count = 2;

        when(statClient.getTopUsersByCountry(anyString(), anyInt()))
                .thenReturn(ResponseEntity.badRequest().build());

        mockMvc.perform(get("/api/v1/stat/top/{country}/{count}", country, count)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetTopUsersByCountry_InvalidCount() throws Exception {
        String country = "CountryA";
        int count = -1;

        mockMvc.perform(get("/api/v1/stat/top/{country}/{count}", country, count)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}