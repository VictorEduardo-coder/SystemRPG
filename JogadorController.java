package com.rpgsystem.zero.controller;

import com.rpgsystem.zero.model.Ficha;
import com.rpgsystem.zero.repository.FichaDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JogadorController {

    // ==================================================
    // MOSTRAR O PAINEL DO JOGADOR
    // ==================================================
    @GetMapping("/painel-player")
    public String exibirPainelPlayer(@RequestParam(defaultValue = "1") int usuarioId, Model model) {
        
        // Tenta procurar a ficha deste utilizador
        Ficha ficha = FichaDAO.buscarFichaPorUsuario(usuarioId);
        
        // Envia a ficha (ou null se ele não tiver nenhuma) para o HTML
        model.addAttribute("ficha", ficha);
        model.addAttribute("usuarioId", usuarioId);
        
        return "painel-player";
    }

    // ==================================================
    // CRIAR UMA NOVA FICHA
    // ==================================================
    @PostMapping("/fichas/criar")
    public String criarNovaFicha(@RequestParam int usuarioId, @RequestParam String nomePersonagem) {
        
        FichaDAO.criarFichaInicial(usuarioId, nomePersonagem);
        
        // Redireciona de volta para o painel
        return "redirect:/painel-player?usuarioId=" + usuarioId;
    }

    // ==================================================
    // ELIMINAR A FICHA
    // ==================================================
    @PostMapping("/fichas/deletar")
    public String deletarFicha(@RequestParam int fichaId, @RequestParam int usuarioId) {
        
        FichaDAO.deletarFicha(fichaId, usuarioId);
        
        // Redireciona de volta para o painel (que agora aparecerá vazio)
        return "redirect:/painel-player?usuarioId=" + usuarioId;
    }
}