package com.leonardofuchs.imageliteapi.config;

import com.leonardofuchs.imageliteapi.application.app.jwt.JwtService;
import com.leonardofuchs.imageliteapi.config.filter.JwtFilter;
import com.leonardofuchs.imageliteapi.domain.entities.User;
import com.leonardofuchs.imageliteapi.domain.service.UserService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity // Anotando classe como contexto de segurança do Spring
public class SecurityConfig { // Método de configuração de segurança da aplicação!

    @Bean // Registrando o filtro do jwt para ser utilizado no contexto do spring
    public JwtFilter jwtFilter(JwtService jwtService, UserService userService){

        return new JwtFilter(jwtService, userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // Método para cryptografar a senha de um user
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception { // Método para configurar as regras de segurança da aplicação!
        return http
                .csrf(AbstractHttpConfigurer::disable) // Desabilitando o csrf (Configuração de token desnecessária)
                .cors(cors -> cors.configure(http)) // configurando o objeto http do security baseado na configuração de cors registrada no método "CorsConfigurationSource"
                .authorizeHttpRequests(auth -> { // Permitindo todas as requisições
                    auth.requestMatchers("/vi/user/**").permitAll(); // qualquer coisa que venha após o /user, será permitido acessar a nossa api
                    auth.requestMatchers(HttpMethod.GET, "/vi/images/**").permitAll(); // Não podemos acessar o SRC da imagem de forma direta quando a requisição está autenticada, então configuramos aqui no security para liberar essa requisição específica para renderizar as imagens a partir do src.
                    auth.anyRequest().authenticated(); // Qualquer outra requisição terá que estar autenticada
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Registrando o filtro que criamos (jwtFilter), antes do filtro que verifica o usuário logado (UserNamePasswordAuthenticateFilter). Ou seja, vai verificar se o token e o usuário estão válidos e autenticados antes.
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() { // Configurando os Cors da aplicação para liberar o acesso á qualquer origem, método e cabeçalho.
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();
        cors.registerCorsConfiguration("/**", config); // o /** diz que qualquer url poderá ter acesso. Caso queira limitar apenas para api´s específicas, no lugar do ** colocar o caminho da url!
        return cors;
    }

}
