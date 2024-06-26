package br.com.rafaelmoura.spring_security_api.model.dto;

import br.com.rafaelmoura.spring_security_api.model.enums.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "UserRequestDTO", description = "classe de requisicao para cadastro de usuario")
public class UserRequestDTO {
    @Schema(name = "name", description = "nome do usuario")
    private String name;
    @Schema(name = "email", description = "email do usuario")
    private String email;
    @Schema(name = "password", description = "senha do usuario")
    private String password;
    @Schema(name = "role", description = "role de permissao do usuario")
    private RoleType role;
}