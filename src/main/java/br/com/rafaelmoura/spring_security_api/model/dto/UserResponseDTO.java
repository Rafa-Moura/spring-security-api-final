package br.com.rafaelmoura.spring_security_api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "UserResponseDTO", description = "classe com a resposta da criacao do usuario")
public class UserResponseDTO {
    @Schema(name = "name", description = "nome do usuario")
    private String name;
    @Schema(name = "email", description = "email do usuario")
    private String email;
}
