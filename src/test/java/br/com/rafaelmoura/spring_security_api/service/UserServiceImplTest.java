package br.com.rafaelmoura.spring_security_api.service;


import br.com.rafaelmoura.spring_security_api.model.dto.LoginRequestDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.TokenResponseDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.UserRequestDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.UserResponseDTO;
import br.com.rafaelmoura.spring_security_api.model.entity.Role;
import br.com.rafaelmoura.spring_security_api.model.entity.User;
import br.com.rafaelmoura.spring_security_api.model.enums.RoleType;
import br.com.rafaelmoura.spring_security_api.repository.UserRepository;
import br.com.rafaelmoura.spring_security_api.security.authentication.JwtTokenService;
import br.com.rafaelmoura.spring_security_api.security.userdetails.UserDetailsImpl;
import br.com.rafaelmoura.spring_security_api.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    JwtTokenService jwtTokenService;
    @Mock
    UserRepository userRepository;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    UserServiceImpl userService;
    private User user;
    private UserRequestDTO userRequest;
    private LoginRequestDTO loginRequest;

    private static final String ENCRYPTED_PASSWORD = "$2a$12$OoasJBteg5/8stD1XH3JOelwKGAY.ooMUXSllnHQc7.7O9vMh62bO";

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .name("user")
                .email("user@email.com")
                .roles(List.of(Role.builder()
                                .roleName(RoleType.ROLE_ADMINISTRATOR)
                        .build()))
                .build();

        userRequest = UserRequestDTO.builder()
                .email("user@email.com")
                .name("user")
                .password("123")
                .role(RoleType.ROLE_ADMINISTRATOR)
                .build();

        loginRequest = LoginRequestDTO.builder()
                .email("sobreescreva-o-email")
                .password("123")
                .build();
    }

    @Test
    @DisplayName("devera registrar um usuario com sucesso")
    void mustBeRegisterUserSuccess(){

        when(passwordEncoder.encode(anyString())).thenReturn(ENCRYPTED_PASSWORD);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO response = userService.registerUser(userRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(user.getEmail(), response.getEmail());
        Assertions.assertEquals(user.getName(), response.getName());

        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName(value = "Devera autenticar um usuario com sucesso")
    void mustBeAuthenticateUserSuccess(){

        LoginRequestDTO loginRequestDTO = loginRequest;
        loginRequestDTO.setEmail("admin@email.com");
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtTokenService.generateToken(userDetails)).thenReturn("jwtToken");

        TokenResponseDTO tokenResponse = userService.authenticateUser(loginRequestDTO);

        Assertions.assertEquals("jwtToken", tokenResponse.getToken());

        verify(authenticationManager, times(1)).authenticate(any(Authentication.class));
        verify(jwtTokenService, times(1)).generateToken(any(UserDetailsImpl.class));
        verify(authentication, times(1)).getPrincipal();
    }
}
