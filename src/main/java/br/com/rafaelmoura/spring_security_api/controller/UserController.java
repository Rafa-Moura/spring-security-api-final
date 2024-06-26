package br.com.rafaelmoura.spring_security_api.controller;

import br.com.rafaelmoura.spring_security_api.exceptions.SystemException;
import br.com.rafaelmoura.spring_security_api.model.dto.LoginRequestDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.TokenResponseDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.UserRequestDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.UserResponseDTO;
import br.com.rafaelmoura.spring_security_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
@Tag(name = "UserController")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Realiza o registro de um usuario no sistema", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do sistema. Tente novamente em alguns instantes ou contate um administrador.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SystemException.class))}
            ),
    })
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequest) {

        UserResponseDTO response = userService.registerUser(userRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    @Operation(summary = "Realiza o login de um usuario no sistema", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario autenticado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Dados de login incorretos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do sistema. Tente novamente em alguns instantes ou contate um administrador.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SystemException.class))}
            ),
    })
    public ResponseEntity<TokenResponseDTO> authenticate(@RequestBody LoginRequestDTO loginRequest) {

        TokenResponseDTO tokenResponse = userService.authenticateUser(loginRequest);

        return new ResponseEntity<>(tokenResponse, HttpStatus.CREATED);
    }
}
