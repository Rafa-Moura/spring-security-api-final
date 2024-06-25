package br.com.rafaelmoura.spring_security_api.controller;

import br.com.rafaelmoura.spring_security_api.model.dto.LoginRequestDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.TokenResponseDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.UserRequestDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.UserResponseDTO;
import br.com.rafaelmoura.spring_security_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequest) {

        UserResponseDTO response = userService.registerUser(userRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenResponseDTO> authenticate(@RequestBody LoginRequestDTO loginRequest) {

        TokenResponseDTO tokenResponse = userService.authenticateUser(loginRequest);

        return new ResponseEntity<>(tokenResponse, HttpStatus.CREATED);
    }
}
