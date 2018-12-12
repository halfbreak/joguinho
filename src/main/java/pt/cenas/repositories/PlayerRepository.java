package pt.cenas.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.cenas.models.Player;

import java.util.UUID;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    Player findByName(String name);
    Player findByUuid(UUID uuid);
}
