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
import ru.perminov.game.dto.UserDataDto;
import ru.perminov.game.service.UserService;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;
    private MockMvc mvc;

    private UserDataDto userDataDto;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(userController).build();


    }


    @Test
    void createSyncOk() throws Exception {
        userDataDto = UserDataDto.builder().money(100).country("ru").build();
        Mockito.doNothing().when(userService).create(Mockito.any(UserDataDto.class));

        mvc.perform(post("/sync")
                        .content(mapper.writeValueAsString(userDataDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createSyncCountryFail() throws Exception {
        userDataDto = UserDataDto.builder().money(100).build();

        mvc.perform(post("/sync")
                        .content(mapper.writeValueAsString(userDataDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createSyncCountrySizeMax() throws Exception {
        userDataDto = UserDataDto.builder().money(100).country("ASDFGHJKLQW").build();

        mvc.perform(post("/sync")
                        .content(mapper.writeValueAsString(userDataDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createSyncCountrySizeMin() throws Exception {
        userDataDto = UserDataDto.builder().money(100).country("A").build();

        mvc.perform(post("/sync")
                        .content(mapper.writeValueAsString(userDataDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createSyncMoneyNegative() throws Exception {
        userDataDto = UserDataDto.builder().money(-100).country("ASD").build();

        mvc.perform(post("/sync")
                        .content(mapper.writeValueAsString(userDataDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}