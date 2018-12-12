package pt.cenas.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.cenas.models.Character;
import pt.cenas.requests.CharacterCreationRequest;
import pt.cenas.services.CharacterService;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.UUID;

@Slf4j
@RestController
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping(path = "player/{playerUuid}/character")
    public Mono<ResponseEntity<Character>> createCharacter(@PathVariable(value = "playerUuid") @NotNull UUID playerUuid,
                                                           @RequestBody @Valid CharacterCreationRequest request) {
        log.info("Handling character creation for {}", playerUuid);
        return characterService.createNewCharacter(playerUuid, request)
                .map(c -> ResponseEntity.created(URI.create("player/" + playerUuid + "/character/" + c.getUuid())).body(c))
                .doOnSuccess(c -> log.info("Created {}", c))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping(path = "player/{playerUuid}/character/{characterUuid}")
    public Mono<ResponseEntity<Character>> getCharacter(@PathVariable(value = "playerUuid") @NotNull UUID playerUuid,
                                                        @PathVariable(value = "characterUuid") @NotNull UUID characterUuid) {
        log.info("Finding chaaracter for {} with uuid {}", playerUuid, characterUuid);
        return characterService.findCharacter(playerUuid, characterUuid)
                .map(ResponseEntity::ok)
                .doOnSuccess(c -> log.info("Found {}", c))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

}
