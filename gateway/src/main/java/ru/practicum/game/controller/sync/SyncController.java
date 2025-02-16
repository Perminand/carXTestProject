package ru.practicum.game.controller.sync;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.game.controller.sync.dto.UserSyncDataDtoIn;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/sync")
@RequiredArgsConstructor
public class SyncController {

    private final SyncClient syncClient;


    @PostMapping("/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSyncData(@PathVariable String uuid, @Valid @RequestBody UserSyncDataDtoIn userDataDto) {
        log.info("POST request uuid: {}, data: {}", uuid, userDataDto);
        syncClient.createSynh(UUID.fromString(uuid), userDataDto);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getData(@PathVariable String uuid) {
        log.info("Get request uuid: {}", uuid);
        return syncClient.getData(uuid);
    }

}
