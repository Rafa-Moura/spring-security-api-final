package br.com.rafaelmoura.spring_security_api.service.impl;

import br.com.rafaelmoura.spring_security_api.model.dto.LoginRequestDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.TokenResponseDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.UserRequestDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.UserResponseDTO;
import br.com.rafaelmoura.spring_security_api.model.entity.User;
import br.com.rafaelmoura.spring_security_api.repository.UserRepository;
import br.com.rafaelmoura.spring_security_api.security.authentication.JwtTokenService;
import br.com.rafaelmoura.spring_security_api.security.userdetails.UserDetailsImpl;
import br.com.rafaelmoura.spring_security_api.service.UserService;
import br.com.rafaelmoura.spring_security_api.service.mapper.UserServiceImplMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequest) {

        String encryptedPassword = passwordEncoder.encode(userRequest.getPassword());

        userRequest.setPassword(encryptedPassword);

        User user = UserServiceImplMapper.userRequestDtoToUserEntity(userRequest);

        User createdUser = userRepository.save(user);
        return UserServiceImplMapper.userToUserResponseDto(createdUser);
    }

    @Override
    public TokenResponseDTO authenticateUser(LoginRequestDTO loginRequest) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return TokenResponseDTO.builder()
                .token(jwtTokenService.generateToken(userDetails))
                .build();
    }
}
