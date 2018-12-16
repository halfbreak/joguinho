package pt.cenas.services;

import org.springframework.stereotype.Service;
import pt.cenas.factories.CharacterFactory;
import pt.cenas.models.Character;
import pt.cenas.models.Player;
import pt.cenas.requests.CharacterCreationRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.UUID;

@Service
public class CharacterService {

    private final PlayerService playerService;
    private final CharacterFactory characterFactory;

    public CharacterService(PlayerService playerService, CharacterFactory characterFactory) {
        this.playerService = playerService;
        this.characterFactory = characterFactory;
    }

    public Mono<Character> findCharacterByPlayerUuidAndCharacterUuid(final UUID playerUuid, final UUID characterUuid) {
        return playerService.findPlayerByUuid(playerUuid)
                .map(Player::getCharacters)
                .flatMap(l -> Flux.fromIterable(l).filter(c -> c.getUuid().equals(characterUuid)).next());
    }

    public Flux<Character> findAllCharacters(final UUID playerUuid) {
        return playerService.findPlayerByUuid(playerUuid)
                .map(Player::getCharacters)
                .flatMapMany(Flux::fromIterable);
    }

    public Mono<Character> createNewCharacter(final UUID playerUuid, final CharacterCreationRequest characterCreationRequest) {
        return Mono.just(characterCreationRequest)
                .flatMap(characterFactory::get)
                .zipWith(playerService.findPlayerByUuid(playerUuid))
                .map(tuple2 -> {
                    tuple2.getT1().setPlayer(tuple2.getT2());
                    tuple2.getT2().getCharacters().add(tuple2.getT1());
                    return tuple2;
                })
                .flatMap(p -> {
                    Mono<Player> playerMono = playerService.updatePlayer(p.getT2());
                    return Mono.just(p.getT1()).zipWith(playerMono);
                }).map(Tuple2::getT1);
    }
}
