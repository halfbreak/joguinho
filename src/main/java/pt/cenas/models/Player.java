package pt.cenas.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private UUID uuid = UUID.randomUUID();

    @NotNull
    private String name;

    @NotNull
    private String password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "player", cascade = CascadeType.ALL)
    private List<Character> characters = new ArrayList<>();
}
