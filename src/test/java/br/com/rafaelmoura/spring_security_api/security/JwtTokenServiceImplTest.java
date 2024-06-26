package br.com.rafaelmoura.spring_security_api.security;

import br.com.rafaelmoura.spring_security_api.model.dto.LoginRequestDTO;
import br.com.rafaelmoura.spring_security_api.model.entity.Role;
import br.com.rafaelmoura.spring_security_api.model.entity.User;
import br.com.rafaelmoura.spring_security_api.model.enums.RoleType;
import br.com.rafaelmoura.spring_security_api.security.authentication.JwtTokenService;
import br.com.rafaelmoura.spring_security_api.security.userdetails.UserDetailsImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

class JwtTokenServiceImplTest {

    private JwtTokenService jwtTokenService;

    @BeforeEach
    void setUp(){
        jwtTokenService = new JwtTokenService();
        ReflectionTestUtils.setField(jwtTokenService, "jwtSecret", "minhachavesecreta");
        ReflectionTestUtils.setField(jwtTokenService, "issuer", "nome-da-aplicacao");
    }

    @Test
    @DisplayName("devera gerar um token com sucesso")
    void mustBeGenerateTokenSuccess(){
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("admin@email.com", "123");

        User user = new User(1L, "admin", loginRequestDTO.getEmail(), loginRequestDTO.getPassword(), List.of(Role.builder()
                        .roleName(RoleType.ROLE_ADMINISTRATOR)
                .build()));

        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        String token = jwtTokenService.generateToken(userDetails);
        String subject = jwtTokenService.getSubjectFromToken(token);

        Assertions.assertNotNull(token);
        Assertions.assertEquals("admin@email.com", subject);

    }

}
