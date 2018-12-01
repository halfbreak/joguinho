package pt.cenas.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private UUID uuid = UUID.randomUUID();

    @NotNull
    private String name;

    @NotNull
    private String password;

    @OneToOne
    @JoinColumn(name = "character_id")
    private Character character;
}
