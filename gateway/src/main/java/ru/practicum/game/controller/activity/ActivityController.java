package ru.practicum.game.controller.activity;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class ActivityController {

    private final ActivityClient activityClient;

    @PostMapping("/{uuid}/activity/{activity}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> createActivityData(@PathVariable String uuid, @PathVariable @Positive Long activity) {
        log.info("PATCH request uuid: {}, activity: {}", uuid, activity);
        return activityClient.createActivity(UUID.fromString(uuid), activity);
    }


}
