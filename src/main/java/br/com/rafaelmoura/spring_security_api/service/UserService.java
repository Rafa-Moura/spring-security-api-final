package br.com.rafaelmoura.spring_security_api.service;

import br.com.rafaelmoura.spring_security_api.model.dto.LoginRequestDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.TokenResponseDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.UserRequestDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO registerUser(UserRequestDTO userRequest);
    TokenResponseDTO authenticateUser(LoginRequestDTO loginRequest);
}
