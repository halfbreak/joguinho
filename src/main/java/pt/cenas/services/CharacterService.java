package pt.cenas.services;

import org.springframework.stereotype.Service;
import pt.cenas.models.Character;
import pt.cenas.models.Detective;
import pt.cenas.models.Player;
import pt.cenas.models.Professor;
import pt.cenas.requests.CharacterCreationRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.UUID;

@Service
public class CharacterService {

    private final PlayerService playerService;

    public CharacterService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public  Mono<Character> findCharacter(final UUID playerUuid, final UUID characterUuid) {
        return playerService.findPlayerByUuid(playerUuid)
                .map(Player::getCharacters)
                .flatMap(l -> Flux.fromIterable(l).filter( c -> c.getUuid().equals(characterUuid)).next());

    }

    public Mono<Character> createNewCharacter(final UUID playerUuid, final CharacterCreationRequest characterCreationRequest) {
        return Mono.just(characterCreationRequest)
                .flatMap(req -> {
                    switch (req.getType()) {
                        case DETECTIVE:
                            return Mono.just(createDetective(req));
                        case PROFESSOR:
                            return Mono.just(createProfessor(req));
                        default:
                            return Mono.empty();
                    }
                })
                .zipWith(playerService.findPlayerByUuid(playerUuid))
                .map(tuple2 -> {
                    tuple2.getT2().getCharacters().add(tuple2.getT1());
                    return tuple2;
                })
                .flatMap(p -> {
                    Mono<Player> playerMono = playerService.updatePlayer(p.getT2());
                    return Mono.just(p.getT1()).zipWith(playerMono);
                }).map(Tuple2::getT1);

    }

    private Detective createDetective(CharacterCreationRequest req) {
        Detective detective = new Detective();
        detective.setName(req.getName());
        return detective;
    }

    private Professor createProfessor(CharacterCreationRequest req) {
        Professor professor = new Professor();
        professor.setName(req.getName());
        return professor;
    }
}
