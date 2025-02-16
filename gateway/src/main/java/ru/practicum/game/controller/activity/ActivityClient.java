package ru.practicum.game.controller.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.game.client.BaseClient;

import java.util.UUID;

@Service
public class ActivityClient extends BaseClient {
    private static final String API_PREFIX = "/api/v1/users";

    @Autowired
    public ActivityClient(@Value("${game.server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build());
    }


    public ResponseEntity<Object> createActivity(UUID uuid, Long activity) {
        return post("/" + uuid + "/activity/" + activity, null);
    }
}
