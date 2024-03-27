package telran.accounts.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AccountDto(@NotEmpty(message = "Missing e-mail" ) String email,@NotEmpty(message = "Missing password") String password,@NotNull String[]roles) {

}
