package com.rpgsystem.zero.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.rpgsystem.zero.model.PerfilAcesso;
import com.rpgsystem.zero.model.Usuario;
import com.rpgsystem.zero.repository.UsuarioDAO;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index", "/login", "/cadastro",
                        "/css/**", "/js/**", "/img/**",
                        "/webjars/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/index")
                .loginProcessingUrl("/login")
                .usernameParameter("login")
                .passwordParameter("senha")
                .successHandler(authenticationSuccessHandler())
                .failureUrl("/index?erro=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index?logout=true")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            Usuario usuario = UsuarioDAO.buscarUsuarioPorLogin(authentication.getName());
            if (usuario != null) {
                if (PerfilAcesso.MESTRE.equals(usuario.getPerfil()) || PerfilAcesso.ADMIN.equals(usuario.getPerfil())) {
                    response.sendRedirect("/painel-mestre?usuarioId=" + usuario.getId());
                } else {
                    response.sendRedirect("/painel-player?usuarioId=" + usuario.getId());
                }
            } else {
                response.sendRedirect("/index?erro=true");
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Usuario usuario = UsuarioDAO.buscarUsuarioPorLogin(username);
            if (usuario == null) {
                throw new org.springframework.security.core.userdetails.UsernameNotFoundException(
                        "Usuário não encontrado: " + username);
            }
            return org.springframework.security.core.userdetails.User
                    .withUsername(usuario.getLogin())
                    .password(usuario.getSenha())
                    .roles(usuario.getPerfil().name())
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}