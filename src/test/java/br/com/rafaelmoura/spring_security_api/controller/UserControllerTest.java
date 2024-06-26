package br.com.rafaelmoura.spring_security_api.controller;

import br.com.rafaelmoura.spring_security_api.model.dto.*;
import br.com.rafaelmoura.spring_security_api.model.enums.RoleType;
import br.com.rafaelmoura.spring_security_api.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserService userService;
    @InjectMocks
    UserController userController;
    private final String URL_BASE = "http://localhost:8080/api/users";
    private LoginRequestDTO loginRequest;
    private TokenResponseDTO tokenResponse;
    private UserRequestDTO userRequest;
    private UserResponseDTO userResponse;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        loginRequest = LoginRequestDTO.builder()
                .email("admin@email.com")
                .password("123")
                .build();

        tokenResponse = TokenResponseDTO.builder()
                .token(generateTokenUtils("admin@email.com"))
                .build();

        userRequest = UserRequestDTO.builder()
                .name("admin")
                .email("admin@email.com")
                .role(RoleType.ROLE_ADMINISTRATOR)
                .password("123")
                .build();

        userResponse = UserResponseDTO.builder()
                .name("admin")
                .email("admin@email.com")
                .build();

    }

    @Test
    @DisplayName("devera integrar junto ao controller de usuarios e simular um registro com sucesso")
    void mustBeRegisterUser() throws Exception {
        when(userService.registerUser(any(UserRequestDTO.class))).thenReturn(userResponse);

        mockMvc.perform(post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userRequest))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(userResponse.getName()))
                .andExpect(jsonPath("$.email").value(userResponse.getEmail()))
                .andDo(print());

        verify(userService, times(1)).registerUser(any(UserRequestDTO.class));
    }

    @Test
    @DisplayName("devera integrar junto ao controller de usuarios e simular uma autenticação de usuario com sucesso")
    void mustBeAuthenticateUserSuccess() throws Exception {
        when(userService.authenticateUser(any(LoginRequestDTO.class))).thenReturn(tokenResponse);

        mockMvc.perform(post(URL_BASE.concat("/login"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value(generateTokenUtils(loginRequest.getEmail())))
                .andDo(print());

        verify(userService, times(1)).authenticateUser(any(LoginRequestDTO.class));
    }

    private String generateTokenUtils(String username){
        String secret = "minhachavesecreta";

        Instant issuedAt = Instant.now().atZone(ZoneId.systemDefault()).toInstant();
        Instant expiresAt = issuedAt.atZone(ZoneId.systemDefault()).plusMinutes(2).toInstant();

        return JWT.create()
                .withIssuer("spring-security-api")
                .withClaim("email", username)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(username)
                .sign(Algorithm.HMAC256(secret));
    }
}
