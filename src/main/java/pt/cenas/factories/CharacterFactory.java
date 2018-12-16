package pt.cenas.factories;

import org.springframework.stereotype.Component;
import pt.cenas.models.Character;
import pt.cenas.models.Detective;
import pt.cenas.models.Professor;
import pt.cenas.requests.CharacterCreationRequest;
import reactor.core.publisher.Mono;

@Component
public class CharacterFactory {

    public Mono<Character> get(final CharacterCreationRequest req) {
        switch (req.getType()) {
            case DETECTIVE:
                return Mono.just(createDetective(req));
            case PROFESSOR:
                return Mono.just(createProfessor(req));
            default:
                return Mono.empty();
        }
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
