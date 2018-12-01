package pt.cenas.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pt.cenas.models.Player;
import pt.cenas.repositories.PlayerRepository;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class PlayerController {

    private final PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @PostMapping(path = "/player", produces = "application/json")
    public Mono<ResponseEntity<Player>> uploadPolls(@RequestBody String name) {
        log.info("Creating new player");
        Player player = new Player();
        player.setName(name);

        return Mono.just(playerRepository.save(player))
                .map(p -> ResponseEntity.ok().body(p))
                .doOnSuccess(p -> log.info("Created {}", p));
    }
}
