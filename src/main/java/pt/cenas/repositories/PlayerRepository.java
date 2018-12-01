package pt.cenas.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.cenas.models.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    Player findByName(String name);
}
