package ru.perminov.game.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.perminov.game.dto.data.UserSyncDataDto;
import ru.perminov.game.dto.data.UserSyncDataDtoIn;
import ru.perminov.game.service.SyncService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/sync")
@RequiredArgsConstructor
public class SyncController {

    private final SyncService syncService;

    @PostMapping("/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSyncData(@PathVariable String uuid, @RequestBody UserSyncDataDtoIn userDataDto) {
        log.info("POST request uuid: {}, data: {}", uuid, userDataDto);
        syncService.createSynh(UUID.fromString(uuid), userDataDto);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserSyncDataDto getData(@PathVariable String uuid) {
        log.info("Get request uuid: {}", uuid);
        return syncService.getData(uuid);
    }

}
