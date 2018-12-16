package pt.cenas.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.cenas.factories.CharacterFactory;
import pt.cenas.models.Character;
import pt.cenas.models.Detective;
import pt.cenas.models.Player;
import pt.cenas.models.Professor;
import pt.cenas.requests.CharacterCreationRequest;
import pt.cenas.requests.CharacterType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {

    @Mock
    private PlayerService playerService;

    @Mock
    private CharacterFactory characterFactory;

    @Test
    void shouldFindCharacterWithSameUuid() {
        Player player = new Player();
        Character character = new Detective();
        player.getCharacters().add(character);

        when(playerService.findPlayerByUuid(player.getUuid())).thenReturn(Mono.just(player));

        CharacterService service = new CharacterService(playerService, characterFactory);
        Mono<Character> characterMono = service.findCharacterByPlayerUuidAndCharacterUuid(player.getUuid(), character.getUuid());

        StepVerifier.create(characterMono)
                .expectSubscription()
                .consumeNextWith(c -> assertThat(c.getUuid()).isEqualByComparingTo(character.getUuid()))
                .verifyComplete();
    }

    @Test
    void shouldFindCharacterIfDifferentUuid() {
        Player player = new Player();
        Character character = new Detective();
        player.getCharacters().add(character);

        when(playerService.findPlayerByUuid(player.getUuid())).thenReturn(Mono.just(player));

        CharacterService service = new CharacterService(playerService, characterFactory);
        Mono<Character> characterMono = service.findCharacterByPlayerUuidAndCharacterUuid(player.getUuid(), UUID.randomUUID());

        StepVerifier.create(characterMono)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    void shouldFindCharacterIfNoCharacters() {
        Player player = new Player();

        when(playerService.findPlayerByUuid(player.getUuid())).thenReturn(Mono.just(player));

        CharacterService service = new CharacterService(playerService, characterFactory);
        Mono<Character> characterMono = service.findCharacterByPlayerUuidAndCharacterUuid(player.getUuid(), UUID.randomUUID());

        StepVerifier.create(characterMono)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    void shouldFindMultipleCharacters() {
        Player player = new Player();
        Character detective = new Detective();
        Character professor = new Professor();
        player.getCharacters().add(detective);
        player.getCharacters().add(professor);

        when(playerService.findPlayerByUuid(player.getUuid())).thenReturn(Mono.just(player));

        CharacterService service = new CharacterService(playerService, characterFactory);
        Flux<Character> characterFlux = service.findAllCharacters(player.getUuid());

        StepVerifier.create(characterFlux)
                .expectSubscription()
                .consumeNextWith(c -> assertThat(c.getUuid()).isEqualByComparingTo(detective.getUuid()))
                .consumeNextWith(c -> assertThat(c.getUuid()).isEqualByComparingTo(professor.getUuid()))
                .verifyComplete();
    }

    @Test
    void shouldFindNoCharacters() {
        Player player = new Player();

        when(playerService.findPlayerByUuid(player.getUuid())).thenReturn(Mono.just(player));

        CharacterService service = new CharacterService(playerService, characterFactory);
        Flux<Character> characterFlux = service.findAllCharacters(player.getUuid());

        StepVerifier.create(characterFlux)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    void shouldCreateCharacter() {
        Player player = new Player();
        CharacterCreationRequest req = new CharacterCreationRequest();
        req.setName("name");
        req.setType(CharacterType.DETECTIVE);
        Character character = new Detective();

        when(playerService.findPlayerByUuid(player.getUuid())).thenReturn(Mono.just(player));
        when(playerService.updatePlayer(player)).thenReturn(Mono.just(player));
        when(characterFactory.get(req)).thenReturn(Mono.just(character));

        CharacterService service = new CharacterService(playerService, characterFactory);
        Mono<Character> characterFlux = service.createNewCharacter(player.getUuid(), req);

        StepVerifier.create(characterFlux)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }
}