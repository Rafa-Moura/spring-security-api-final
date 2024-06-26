package br.com.rafaelmoura.spring_security_api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "LoginRequestDTO", description = "classe utilizada para fazer o login")
public class LoginRequestDTO {

    @Schema(name = "email", description = "campo de email do usuario")
    private String email;
    @Schema(name = "password", description = "campo de senha do usuario")
    private String password;
}
