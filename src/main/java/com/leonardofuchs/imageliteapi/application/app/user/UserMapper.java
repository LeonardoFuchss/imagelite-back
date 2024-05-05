package com.leonardofuchs.imageliteapi.application.app.user;

import com.leonardofuchs.imageliteapi.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User UserToMapper (UserDto userDto){

        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }
}
