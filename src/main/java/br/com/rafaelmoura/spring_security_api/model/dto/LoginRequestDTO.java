package br.com.rafaelmoura.spring_security_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequestDTO {

    private String email;
    private String password;

}
