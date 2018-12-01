package pt.cenas.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pt.cenas.models.Player;
import pt.cenas.repositories.PlayerRepository;
import pt.cenas.requests.PlayerCreationRequest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public PlayerService(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<Player> registerNewPlayer(PlayerCreationRequest playerCreationRequest) {

        return Mono.just(playerCreationRequest)
                .subscribeOn(Schedulers.newSingle("sign-up-users"))
                .map(req -> {
                    Player player = new Player();
                    player.setName(req.getName());
                    player.setPassword(passwordEncoder.encode(req.getPassword()));
                    return player;
                })
                .map(playerRepository::save);
    }
}
