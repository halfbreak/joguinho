package pt.cenas.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CharacterCreationRequest {
    @NotNull
    private String name;
    @NotNull
    private CharacterType type;
}
