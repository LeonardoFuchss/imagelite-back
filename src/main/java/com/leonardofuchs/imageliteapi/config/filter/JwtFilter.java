package com.leonardofuchs.imageliteapi.config.filter;

import com.leonardofuchs.imageliteapi.application.app.jwt.InvalidTokenException;
import com.leonardofuchs.imageliteapi.application.app.jwt.JwtService;
import com.leonardofuchs.imageliteapi.config.SecurityConfig;
import com.leonardofuchs.imageliteapi.domain.entities.User;
import com.leonardofuchs.imageliteapi.domain.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j // Para adicionar log

public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { // Método de filtro para retornar um usuário autenticado!

        String token = getToken(request); // busca um token através da requisição.

        if (token != null) {
            try {

                String email = jwtService.getEmailFromToken(token); // Se o token não for nulo, chamamos o método do jwtService que retorna o email do token decodificado!
                User user = userService.getByEmail(email); // Buscamos o email decodificado, no banco de dados e armazenamos como uma entidade User.
                setUserAsAuthenticated(user); // Autenticando o usuário com o método que criamos abaixo

            } catch (InvalidTokenException e) {
                log.error("Token is invalid: {} ", e.getMessage());
            }

        }
        filterChain.doFilter(request, response);
    }

    private void setUserAsAuthenticated(User user) { // Lógica para setar o usuário como autenticado

        UserDetails userDetails = org.springframework.security.core.userdetails.User // Utilizando UserDetails para autenticar
                .withUsername(user.getName())
                .password(user.getPassword())
                .roles("USER")
                .build();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken); // Autenticando o token do usuário no contexto do security
    }

    private String getToken(HttpServletRequest request) { // Método para interceptar a requisição para verificar o token.
        String authHeader = request.getHeader("Authorization"); // Buscando o header da requisição do tipo authorization, que é aonde o token vai estar
        if (authHeader != null) { // Se o header não for nulo
            String[] authHeaderParts = authHeader.split(" "); // No header da requisição, vai ter escrito Bearer e logo depois o token, uma String única separada por espaço. O split vai dividir essa string a partir do espaço.
            if (authHeaderParts.length == 2) { // se o token tiver dividido em 2 partes (Bearer + token)
                return authHeaderParts[1]; // Irá retornar o elemento no espaço de memória 1 do array, ou seja, o token.
            }
        }
        return null; // Caso não existir nenhum token
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException { // "Não deve filtrar"
        return request.getRequestURI().contains("/vi/user"); // Definindo quais url´s não deve fitrare verificar o token
    }
}
