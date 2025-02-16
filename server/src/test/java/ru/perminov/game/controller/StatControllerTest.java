package ru.perminov.game.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.perminov.game.model.UserSyncData;
import ru.perminov.game.service.StatService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StatControllerTest {

    UserSyncData userSyncData1 = UserSyncData.builder().id(UUID.randomUUID()).money(1000).country("RU").build();
    UserSyncData userSyncData2 = UserSyncData.builder().id(UUID.randomUUID()).money(500).country("EN").build();
    UserSyncData userSyncData3 = UserSyncData.builder().id(UUID.randomUUID()).money(2000).country("RU").build();
    UserSyncData userSyncData4 = UserSyncData.builder().id(UUID.randomUUID()).money(200).country("EN").build();

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
    public void testGetTopUsersByCountry() throws Exception {
        // Given
        String country = "USA";
        int count = 10;
        List<UserSyncData> expectedUsers = Arrays.asList(userSyncData1, userSyncData2, userSyncData3, userSyncData4);

        when(statService.getTopUsersByCountry(country, count)).thenReturn(expectedUsers);

        mvc.perform(get("/api/v1/stat/top/{country}/{count}", country, count)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(statService).getTopUsersByCountry(country, count);
    }

    @Test
    public void testGetTopUsersByCountry_ServiceReturnsEmpty() throws Exception {
        String country = "USA";
        int count = 10;
        List<UserSyncData> expectedUsers = List.of();

        when(statService.getTopUsersByCountry(country, count)).thenReturn(expectedUsers);

        mvc.perform(get("/api/v1/stat/top/{country}/{count}", country, count)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(statService).getTopUsersByCountry(country, count);
    }
}