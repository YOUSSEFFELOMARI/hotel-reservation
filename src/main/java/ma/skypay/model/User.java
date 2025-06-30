package ma.skypay.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    @NotNull
    private final int id;
    @NotNull
    private final String fullName;
    @NotNull
    private int balance;
}