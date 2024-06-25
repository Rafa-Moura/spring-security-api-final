package br.com.rafaelmoura.spring_security_api.model.dto;

import br.com.rafaelmoura.spring_security_api.model.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;
    private RoleType role;
}