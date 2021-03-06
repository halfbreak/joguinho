package pt.cenas.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pt.cenas.models.Player;
import pt.cenas.requests.PlayerCreationRequest;
import pt.cenas.services.PlayerService;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping(path = "/sign-up")
    public Mono<ResponseEntity<Player>> uploadPolls(@Valid @RequestBody PlayerCreationRequest playerCreationRequest) {
        return playerService.registerNewPlayer(playerCreationRequest)
                .map(p -> ResponseEntity.created(URI.create("/player/" + p.getUuid())).body(p))
                .doOnSuccess(p -> log.info("Created {}", p));
    }
}
