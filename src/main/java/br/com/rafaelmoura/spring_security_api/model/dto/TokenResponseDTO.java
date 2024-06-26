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
@Schema(name = "TokenResponseDTO", description = "classe de resposta do login do usuario contendo o token")
public class TokenResponseDTO {

    @Schema(name = "token", description = "token gerado na autenticacao do usuario", example = "eeayysu.udyya.sdadw")
    private String token;

}
