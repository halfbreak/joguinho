package pt.cenas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pt.cenas.models.Player;
import pt.cenas.repositories.PlayerRepository;
import reactor.core.publisher.Flux;

@Controller
public class PlayerController {

    private final PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @PostMapping(path = "/player")
    public Flux<Player> uploadPolls(@RequestBody String name) {

        Player player = new Player();
        player.setName(name);
        return Flux.just(playerRepository.save(player));
    }
}
