package com.leonardofuchs.imageliteapi.application.app.user;

import com.leonardofuchs.imageliteapi.domain.entities.User;
import com.leonardofuchs.imageliteapi.domain.exeption.DuplicatedTupleException;
import com.leonardofuchs.imageliteapi.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vi/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImplementation userService;
    private final UserMapper userMapper;


    @PostMapping
    public ResponseEntity save(@RequestBody UserDto userDto) {
        try {
            User user = userMapper.UserToMapper(userDto);
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DuplicatedTupleException e) {
            Map<String, String> jsonResultado = Map.of("error", e.getMessage()); // Colocando a mensagem de erro pro client (Passando pra json)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonResultado);
        }
    }
    @PostMapping("/auth")
        public ResponseEntity authenticate(@RequestBody CredentialsDTO credentialsDTO){
        var  token = userService.authenticate(credentialsDTO.getEmail(), credentialsDTO.getPassword());
        if(token == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(token);
    }

    @GetMapping()
    public ResponseEntity<User> getByEmail(String email) {

        User search = userService.getByEmail(email);
        return ResponseEntity.ok(search);

    }
}
