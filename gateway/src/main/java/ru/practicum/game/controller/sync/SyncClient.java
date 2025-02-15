package ru.practicum.game.controller.sync;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.game.client.BaseClient;
import ru.practicum.game.controller.sync.dto.UserSyncDataDtoIn;

import java.util.UUID;

@Service
public class SyncClient extends BaseClient {
    private static final String API_PREFIX = "/sync";

    @Autowired
    public SyncClient(@Value("${game-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build());
    }

    public void createSynh(UUID uuid, @Valid UserSyncDataDtoIn userDataDto) {
        post("/" + uuid, userDataDto);
    }

    public ResponseEntity<Object> getData(String uuid) {
        return get("/" + uuid);
    }
}
