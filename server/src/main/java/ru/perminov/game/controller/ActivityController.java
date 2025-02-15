package ru.perminov.game.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.perminov.game.dto.activity.UserActivityDataDto;
import ru.perminov.game.service.ActivityService;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/data")
public class ActivityController {

    private final ActivityService activityService;


    @PostMapping("/activity/{uuid}/{activity}")
    @ResponseStatus(HttpStatus.OK)
    public UserActivityDataDto createActivityData(@PathVariable String uuid, @PathVariable Long activity) {
        log.info("PATCH request uuid: {}, activity: {}", uuid, activity);
        return activityService.createActivity(UUID.fromString(uuid), activity);
    }


}
