package com.leonardofuchs.imageliteapi.application.app.user;

import com.leonardofuchs.imageliteapi.application.app.jwt.JwtService;
import com.leonardofuchs.imageliteapi.domain.AcessToken;
import com.leonardofuchs.imageliteapi.domain.entities.User;
import com.leonardofuchs.imageliteapi.domain.exeption.DuplicatedTupleException;
import com.leonardofuchs.imageliteapi.domain.service.UserService;
import com.leonardofuchs.imageliteapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    private void encodePassword(User user) { // Método para codificar e criptografar  a senha do usuário
        String passwordUser = user.getPassword(); // armazenando a senha do usuário
        String encodePassword = passwordEncoder.encode(passwordUser); // Codificando a senha do usuário através do password encoder do security
        user.setPassword(encodePassword); // setando um novo valor com a senha codificada e criptografada ao usuário
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    @Transactional
    public User saveUser(User user) {

        var possibleEmailExist = getByEmail(user.getEmail()); // Chama o metodo getByEmail para verificar se o email existe.
        if (possibleEmailExist != null) { // Se a verificação não for nula, ou seja, o email existir, lança a exeption criada!
            throw new DuplicatedTupleException("User already exist!");
        }
        encodePassword(user); // criptografando a senha
        return userRepository.save(user); // Se a verificação for nula, ou seja, o email não existir, salva no banco de dados!
    }


    @Override
    public AcessToken authenticate(String email, String password) { // Autenticação do usuário

        var user = getByEmail(email); // Verifica se o usuário passado por parametro existe no banco de dados
        if (user == null) {
            return null;
        }
        boolean matches = passwordEncoder.matches(password, user.getPassword()); // Se o usuário existir, verifica se a senha digitada bate com a senha do usuário no banco
        if (matches) {
            return jwtService.generatedToken(user); // Se a senha existir no banco de dados, gera um novo token
        } else {
            return null;
        }
    }
}
