package ru.perminov.game.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.perminov.game.dto.activity.UserActivityDto;
import ru.perminov.game.dto.data.UserDataDto;
import ru.perminov.game.dto.data.UserDataDtoIn;
import ru.perminov.game.service.DataService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @Validated
    @PostMapping("activity/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserActivityDto createActivityData(@PathVariable UUID uuid, @Valid @RequestBody UserActivityDto userActivityDto) {
        log.info("PATCH request uuid: {}, data: {}", uuid, userActivityDto);
        return dataService.createActivity(uuid, userActivityDto);
    }

    @Validated
    @PostMapping("synh/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSyncData(@PathVariable UUID uuid, @Valid @RequestBody UserDataDtoIn userDataDto) {
        log.info("POST request uuid: {}, data: {}", uuid, userDataDto);
        dataService.createSynh(uuid, userDataDto);
    }

    @Validated
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserDataDto getData(@PathVariable UUID uuid) {
        log.info("Get request uuid: {}", uuid);
        return dataService.getData(uuid);
    }

}
