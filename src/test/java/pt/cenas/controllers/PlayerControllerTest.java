package pt.cenas.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import pt.cenas.models.Player;
import pt.cenas.requests.PlayerCreationRequest;
import pt.cenas.services.PlayerService;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(PlayerController.class)
@ContextConfiguration(classes = PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private WebTestClient web;

    @MockBean
    private PlayerService playerService;

    @Test
    public void shouldSignUpNewPlayer() {
        assertThat(web).isNotNull();

        PlayerCreationRequest req = new PlayerCreationRequest("name", "password");
        Player player = new Player();
        player.setName("name");
        player.setPassword("encrypted_pass");

        when(playerService.registerNewPlayer(req)).thenReturn(Mono.just(player));

        web.post().uri("sign-up").syncBody(req).exchange()
                .expectStatus()
                .isCreated()
                .expectBody();
    }

    @Test
    public void shouldFailToSignUpIfNoPasswordGiven() {
        assertThat(web).isNotNull();

        PlayerCreationRequest req = new PlayerCreationRequest("name", null);

        web.post().uri("sign-up").syncBody(req).exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void shouldFailToSignUpIfNoNameGiven() {
        assertThat(web).isNotNull();

        PlayerCreationRequest req = new PlayerCreationRequest(null, "password");

        web.post().uri("sign-up").syncBody(req).exchange()
                .expectStatus()
                .isBadRequest();
    }
}