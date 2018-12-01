package pt.cenas.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.cenas.models.Character;
import reactor.core.publisher.Mono;

@RestController
public class CharacterController {

    @PostMapping(path = "character")
    public Mono<Character> createCharacter() {
        return Mono.empty();
    }

}
