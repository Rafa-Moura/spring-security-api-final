package br.com.rafaelmoura.spring_security_api.security;

import br.com.rafaelmoura.spring_security_api.model.entity.Role;
import br.com.rafaelmoura.spring_security_api.model.entity.User;
import br.com.rafaelmoura.spring_security_api.model.enums.RoleType;
import br.com.rafaelmoura.spring_security_api.repository.UserRepository;
import br.com.rafaelmoura.spring_security_api.security.authentication.CustomUserAuthenticationFilter;
import br.com.rafaelmoura.spring_security_api.security.authentication.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CustomUserAuthenticationFilterTest {


    @Mock
    private JwtTokenService jwtTokenService;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CustomUserAuthenticationFilter customUserAuthenticationFilter;
    private User user;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder()
                .email("admin@email.com")
                .name("admin")
                .password("$2a$12$OoasJBteg5/8stD1XH3JOelwKGAY.ooMUXSllnHQc7.7O9vMh62bO")
                .roles(List.of(Role.builder()
                        .roleName(RoleType.ROLE_ADMINISTRATOR)
                        .build()))
                .build();
    }

    @Test
    @DisplayName("deve passar no filtro sem validar o token")
    void mustBeValidatePublicEndpointSuccess() {

        when(request.getRequestURI()).thenReturn("/api/users/login");
        Assertions.assertDoesNotThrow(() -> customUserAuthenticationFilter.doFilter(request, response, filterChain));

        verify(request, times(1)).getRequestURI();
        verifyNoInteractions(userRepository);
        verifyNoInteractions(jwtTokenService);
    }

    @Test
    @DisplayName("deve recuperar e validar o token com sucesso")
    void mustBeValidateTokenSuccess() {

        when(request.getRequestURI()).thenReturn("/api/products/v1");
        when(request.getHeader(anyString())).thenReturn("Bearer eeyywuau.dadweqrrrrq.aususyur");
        when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn(user.getEmail());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        Assertions.assertDoesNotThrow(() -> customUserAuthenticationFilter.doFilter(request, response, filterChain));

        verify(request, times(1)).getRequestURI();
        verify(request, times(1)).getHeader(anyString());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(jwtTokenService, times(1)).getSubjectFromToken(anyString());
    }

    @Test
    @DisplayName("deve lancar excecao quando token nao estiver presente na requisicao")
    void mustBeThrowExceptionWhenTokenIsNull() {

        when(request.getRequestURI()).thenReturn("/api/products/v1");
        when(request.getHeader(anyString())).thenReturn(null);
        when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn(user.getEmail());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        Throwable throwable = Assertions.assertThrows(BadCredentialsException.class, () ->
                customUserAuthenticationFilter.doFilter(request, response, filterChain)
        );

        Assertions.assertEquals(BadCredentialsException.class, throwable.getClass());
        Assertions.assertEquals("Token nao informado na requisicao", throwable.getMessage());
        verify(request, times(1)).getRequestURI();
        verify(request, times(1)).getHeader(anyString());
        verifyNoInteractions(userRepository);
        verifyNoInteractions(jwtTokenService);
    }

}
