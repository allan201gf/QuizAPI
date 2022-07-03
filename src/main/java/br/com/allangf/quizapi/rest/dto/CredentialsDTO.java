package br.com.allangf.quizapi.rest.dto;

import br.com.allangf.quizapi.damain.exception.Errors;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CredentialsDTO {

    @NotNull(message = Errors.EMAIL_IS_REQUIRED)
    @Email
    private String email;
    @NotNull(message = Errors.PASSWORD_IS_REQUIRED)
    private String password;

}
