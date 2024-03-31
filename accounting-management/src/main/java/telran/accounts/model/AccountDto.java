package telran.accounts.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AccountDto(@NotEmpty(message = "Missing e-mail" )@Email String email,@NotNull(message = "Missing password")@Size(min=8) String password,@NotEmpty String[]roles) {

}
