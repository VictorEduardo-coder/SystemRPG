package com.rpgsystem.zero.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rpgsystem.zero.model.Ficha;
import com.rpgsystem.zero.repository.FichaDAO;

@Controller
public class MestreController {

    @GetMapping("/painel-mestre")
    public String exibirPainelMestre(
            @RequestParam(value = "usuarioId", required = false) Integer usuarioId,
            Model model
    ) {
        
        // Vai buscar todas as fichas ao banco de dados
        List<Ficha> todasAsFichas = FichaDAO.buscarTodasAsFichas();
        
        // Envia a lista para o Thymeleaf com o nome "fichas"
        model.addAttribute("fichas", todasAsFichas);
        
        // ==========================================================
        // SOLUÇÃO: Enviando o ID com os dois nomes para o Thymeleaf
        // ==========================================================
        model.addAttribute("usuarioLogadoId", usuarioId); // Mantém o antigo para não quebrar o que já funciona
        model.addAttribute("usuarioId", usuarioId);       // Adiciona o novo para o botão do manual e das entidades
        
        // Retorna o nome do ficheiro HTML
        return "painel-mestre"; 
    }
    /*
     * REDIRECIONAMENTOS DO MESTRE
     */

    @GetMapping("/painel-mestre/manual")
    public String redirecionarParaManual(@RequestParam(value = "usuarioId", required = false) Integer usuarioId) {
        if (usuarioId != null) {
            // Redireciona para o Manual enviando o ID para que o botão "Voltar" saiba para onde regressar
            return "redirect:/Manual?usuarioId=" + usuarioId;
        }
        return "redirect:/Manual";
    }

    @GetMapping("/painel-mestre/habilidades")
    public String redirecionarParaHabilidades(@RequestParam(value = "usuarioId", required = false) Integer usuarioId) {
        if (usuarioId != null) {
            // Redireciona para a tela de Habilidades enviando o ID
            return "redirect:/habilidades?usuarioId=" + usuarioId;
        }
        // Fallback caso a rota seja acessada sem ID
        return "redirect:/habilidades";
    }
}