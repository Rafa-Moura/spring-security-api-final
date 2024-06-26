package br.com.rafaelmoura.spring_security_api.security;

import br.com.rafaelmoura.spring_security_api.model.entity.Role;
import br.com.rafaelmoura.spring_security_api.model.entity.User;
import br.com.rafaelmoura.spring_security_api.model.enums.RoleType;
import br.com.rafaelmoura.spring_security_api.repository.UserRepository;
import br.com.rafaelmoura.spring_security_api.security.userdetails.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User user;

    @BeforeEach
    void setUp(){
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
    @DisplayName("Devera recuperar um usuario com sucesso")
    void mustBeReturnUserSuccess(){

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(user.getEmail(), userDetails.getUsername());

        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("Devera lancar uma excecao quando nao encontrar o usuario")
    void mustBeThrownExceptionWhenUserNotFound(){

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Throwable exception = Assertions.assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(user.getEmail()));

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(UsernameNotFoundException.class, exception.getClass());
        Assertions.assertEquals("Usuario nao encontrado", exception.getMessage());

        verify(userRepository, times(1)).findByEmail(anyString());
    }

}
