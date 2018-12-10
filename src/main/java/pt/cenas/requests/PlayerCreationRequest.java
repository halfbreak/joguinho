package pt.cenas.requests;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PlayerCreationRequest {
    @NotNull
    private String name;
    @NotNull
    private String password;
}
