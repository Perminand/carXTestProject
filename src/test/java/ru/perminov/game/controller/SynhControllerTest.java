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
import ru.perminov.game.dto.data.UserDataDto;
import ru.perminov.game.dto.data.UserDataDtoIn;
import ru.perminov.game.service.DataService;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SynhControllerTest {

    ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    UUID uuid;
    @Mock
    private DataService dataService;
    @InjectMocks
    private DataController dataController;
    private MockMvc mvc;
    private UserDataDto userDataDto;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(dataController).build();
        uuid = UUID.randomUUID();

    }


    @Test
    void createSyncOk() throws Exception {
        userDataDto = UserDataDto.builder().money(100).country("RU").build();
        Mockito.doNothing().when(dataService).createSynh(Mockito.any(UUID.class), Mockito.any(UserDataDtoIn.class));

        mvc.perform(post("/data/synh/" + uuid)
                        .content(mapper.writeValueAsString(userDataDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createSyncCountryFail() throws Exception {
        userDataDto = UserDataDto.builder().money(100).build();

        mvc.perform(post("/data/sync" + uuid)
                        .content(mapper.writeValueAsString(userDataDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createSyncCountrySizeMax() throws Exception {
        userDataDto = UserDataDto.builder().money(100).country("ASDFGHJKLQW").build();

        mvc.perform(post("/data/sync" + uuid)
                        .content(mapper.writeValueAsString(userDataDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createSyncCountrySizeMin() throws Exception {
        userDataDto = UserDataDto.builder().money(100).country("A").build();

        mvc.perform(post("/data/sync" + uuid)
                        .content(mapper.writeValueAsString(userDataDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createSyncMoneyNegative() throws Exception {
        userDataDto = UserDataDto.builder().money(-100).country("ASD").build();

        mvc.perform(post("/data/sync" + uuid)
                        .content(mapper.writeValueAsString(userDataDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}