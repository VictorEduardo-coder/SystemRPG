package com.rpgsystem.zero.controller;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DadoAndSecurityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimpMessagingTemplate messagingTemplate;

    @Test
    void rotasPublicasRespondemSemAutenticacao() throws Exception {
        mockMvc.perform(get("/index")).andExpect(status().isOk());
        mockMvc.perform(get("/cadastro")).andExpect(status().isOk());
    }

    @Test
    void rotasProtegidasRedirecionamUsuarioAnonimoParaLogin() throws Exception {
        mockMvc.perform(get("/painel-player"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/index"));

        mockMvc.perform(get("/painel-mestre"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/index"));

        mockMvc.perform(post("/api/dados/rolar")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"expressao\":\"1d20\",\"nomeJogador\":\"Anon\"}"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/index"));
    }

    @Test
    @WithMockUser(username = "jogador1", roles = {"PLAYER"})
    void postSemTokenCsrfOuComTokenInvalidoEhBloqueadoCom403() throws Exception {
        mockMvc.perform(post("/api/dados/rolar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"expressao\":\"2d6\",\"nomeJogador\":\"Kael\"}"))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/api/dados/rolar")
                        .with(csrf().useInvalidToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"expressao\":\"2d6\",\"nomeJogador\":\"Kael\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "jogador1", roles = {"PLAYER"})
    void rolagemAutenticadaComCsrfValidoRetornaJsonENotificaTopicoDoMestre() throws Exception {
        mockMvc.perform(post("/api/dados/rolar")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"expressao\":\"2d6+1\",\"nomeJogador\":\"Kael\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").isNumber())
                .andExpect(jsonPath("$.detalhes").isString());

        verify(messagingTemplate).convertAndSend(eq("/topic/mestre"), anyMap());
    }
}
