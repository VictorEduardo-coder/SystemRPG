package com.rpgsystem.zero.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Habilita um broker em memória que vai carregar as mensagens para o tópico específico
        config.enableSimpleBroker("/topic"); 
        
        // Define o prefixo das mensagens que vão do cliente para o servidor (caso você precise no futuro)
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Define a rota ("/ws-rpg") onde o front-end vai se conectar para escutar o WebSocket.
        // O withSockJS() cria um fallback confiável caso a rede do jogador bloqueie WebSockets puros.
        registry.addEndpoint("/ws-rpg").withSockJS();
    }
}