package br.com.rafaelmoura.spring_security_api.service.mapper;

import br.com.rafaelmoura.spring_security_api.model.dto.UserRequestDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.UserResponseDTO;
import br.com.rafaelmoura.spring_security_api.model.entity.Role;
import br.com.rafaelmoura.spring_security_api.model.entity.User;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class UserServiceImplMapper {

    public User userRequestDtoToUserEntity(UserRequestDTO userRequest){
        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .roles(List.of(Role.builder().roleName(userRequest.getRole()).build()))
                .build();
    }

    public UserResponseDTO userToUserResponseDto(User user){
        return UserResponseDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
