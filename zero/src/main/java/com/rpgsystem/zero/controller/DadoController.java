package com.rpgsystem.zero.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rpgsystem.zero.service.DadoService;

@RestController
@RequestMapping("/api/dados")
public class DadoController {

    private final DadoService dadoService;
    private final SimpMessagingTemplate messagingTemplate;

    // Injetamos o SimpMessagingTemplate no construtor
    public DadoController(DadoService dadoService, SimpMessagingTemplate messagingTemplate) {
        this.dadoService = dadoService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/rolar")
    public ResponseEntity<?> rolarDado(@RequestBody Map<String, String> payload) {
        String expressao = payload.get("expressao");
        String nomeJogador = payload.get("nomeJogador");

        // 1. Calcula a rolagem
        DadoService.ResultadoDado resultado = dadoService.rolar(expressao);

        // 2. Envia a mensagem em tempo real para o Mestre (WebSockets)
        String mensagemMestre = nomeJogador + " rolou: " + resultado.detalhes();
        
        // Dispara para o tópico "/topic/mestre" (A tela do mestre vai ficar "escutando" esse tópico)
        // Enviamos um Map para o front-end receber como JSON facilitando a formatação lá na tela
        messagingTemplate.convertAndSend("/topic/mestre", Map.of(
                "jogador", nomeJogador,
                "resultado", resultado.detalhes(),
                "total", resultado.total(),
                "mensagem", mensagemMestre
        ));

        // Mantido apenas para log no console do servidor
        System.out.println("NOTIFICAÇÃO MESTRE: " + mensagemMestre); 

        // 3. Devolve para a tela do jogador para atualizar a ficha dele
        return ResponseEntity.ok(Map.of(
                "total", resultado.total(),
                "detalhes", resultado.detalhes()
        ));
    }
}