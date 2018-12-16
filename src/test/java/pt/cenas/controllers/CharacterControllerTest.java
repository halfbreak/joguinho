package pt.cenas.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import pt.cenas.models.Character;
import pt.cenas.models.Detective;
import pt.cenas.requests.CharacterCreationRequest;
import pt.cenas.requests.CharacterType;
import pt.cenas.services.CharacterService;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(CharacterController.class)
@ContextConfiguration(classes = CharacterController.class)
public class CharacterControllerTest {

    @Autowired
    private WebTestClient web;

    @MockBean
    private CharacterService characterService;

    @Test
    public void shouldCreateDetectiveForFoundPlayer() {
        assertThat(web).isNotNull();

        UUID storedPlayerUuid = UUID.randomUUID();
        CharacterCreationRequest req = new CharacterCreationRequest("name", CharacterType.DETECTIVE);

        Character character = new Detective();
        character.setName("name");

        when(characterService.createNewCharacter(storedPlayerUuid, req)).thenReturn(Mono.just(character));

        web.post().uri("player/" + storedPlayerUuid + "/character").syncBody(req).exchange()
                .expectStatus()
                .isCreated()
                .expectBody();
    }

    @Test
    public void shouldFindDetectiveForFoundPlayer() {
        assertThat(web).isNotNull();

        UUID storedPlayerUuid = UUID.randomUUID();
        UUID storedCharacterUuid = UUID.randomUUID();

        Character character = new Detective();
        character.setName("name");
        character.setUuid(storedCharacterUuid);

        when(characterService.findCharacterByPlayerUuidAndCharacterUuid(storedPlayerUuid, storedCharacterUuid)).thenReturn(Mono.just(character));

        web.get().uri("player/" + storedPlayerUuid + "/character/" + storedCharacterUuid).exchange()
                .expectStatus()
                .isOk()
                .expectBody();
    }

    @Test
    public void shouldNotCreateDetectiveForNonExistentPlayer() {
        assertThat(web).isNotNull();

        UUID storedPlayerUuid = UUID.randomUUID();
        CharacterCreationRequest req = new CharacterCreationRequest("name", CharacterType.DETECTIVE);

        when(characterService.createNewCharacter(storedPlayerUuid, req)).thenReturn(Mono.empty());

        web.post().uri("player/" + storedPlayerUuid + "/character").syncBody(req).exchange()
                .expectStatus()
                .isNotFound();
    }
}