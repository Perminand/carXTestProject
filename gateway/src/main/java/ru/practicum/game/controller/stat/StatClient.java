package ru.practicum.game.controller.stat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.game.client.BaseClient;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class StatClient extends BaseClient {
    private static final String API_PREFIX = "/api/v1/stat";

    @Autowired
    public StatClient(@Value("${game.server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build());
    }


    public ResponseEntity<Object> getTopUsersByCountry(String country, int count) {
        return get("/top/" + country + "/" + count);
    }

    public ResponseEntity<Object> countNewUsersByCountry(LocalDate start, LocalDate end) {
        return get("/new-user/" + start + "/" + end);
    }

    public ResponseEntity<Object> findActivityByUserAndPeriod(UUID userCredential, LocalDate start, LocalDate end) {
        return get("/activity/" + userCredential + "/" + start + "/" + end);
    }
}
