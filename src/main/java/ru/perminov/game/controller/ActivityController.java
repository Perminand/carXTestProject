package ru.perminov.game.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.perminov.game.dto.activity.UserActivityDataDto;
import ru.perminov.game.service.ActivityService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @Validated
    @PostMapping("activity/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserActivityDataDto createActivityData(@PathVariable UUID uuid, @Valid @RequestBody UserActivityDataDto userActivityDataDto) {
        log.info("PATCH request uuid: {}, data: {}", uuid, userActivityDataDto);
        return activityService.createActivity(uuid, userActivityDataDto);
    }


}
