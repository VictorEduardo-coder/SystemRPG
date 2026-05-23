package com.rpgsystem.zero.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rpgsystem.zero.model.Classe;
import com.rpgsystem.zero.model.Ficha;
import com.rpgsystem.zero.repository.ClasseDAO;
import com.rpgsystem.zero.repository.FichaDAO;

@Controller
public class JogadorController {

    // ==================================================
    // MOSTRAR O PAINEL DO JOGADOR
    // ==================================================
    @GetMapping("/painel-player")
    public String exibirPainelPlayer(
            @RequestParam(value = "usuarioId", required = false) Integer usuarioId, 
            Model model) {
        
        // Se o ID for nulo, redireciona para o login em vez de travar
        if (usuarioId == null) {
            return "redirect:/index";
        }
        
        Ficha ficha = FichaDAO.buscarFichaPorUsuario(usuarioId);
        
        // Busca todas as classes existentes no banco
        List<Classe> todasAsClasses = ClasseDAO.listarTodasAsClasses();
        
        model.addAttribute("ficha", ficha);
        model.addAttribute("classesDisponiveis", todasAsClasses); // Envia as classes para o HTML
        model.addAttribute("usuarioId", usuarioId);
        model.addAttribute("isMestre", false); 
        
        return "painel-player";
    }

    // ==================================================
    // CRIAR UMA NOVA FICHA (PLAYER, NPC, MONSTRO OU BOSS)
    // ==================================================
    @PostMapping("/fichas/criar")
    public String criarNovaFicha(
            @RequestParam(value = "usuarioId", required = false) Integer usuarioId, 
            @RequestParam String nomePersonagem,
            @RequestParam(required = false, defaultValue = "PLAYER") String tipoFicha
    ) {
        
        // 1. Trava de Segurança: Se o ID vier vazio, aborta para não dar tela branca
        if (usuarioId == null) {
            System.out.println("ERRO: O navegador não enviou o usuarioId ao criar a ficha.");
            return "redirect:/index"; // Volta pro login por segurança
        }

        // 2. Cria a ficha no banco de dados com o tipo correto
        FichaDAO.criarFicha(usuarioId, nomePersonagem, tipoFicha);
        
        // 3. Verifica qual era o tipo de ficha e devolve o mestre para a tela certa
        if ("NPC".equalsIgnoreCase(tipoFicha)) {
            return "redirect:/painel-mestre/npcs?usuarioId=" + usuarioId;
        } else if ("MONSTRO".equalsIgnoreCase(tipoFicha)) {
            return "redirect:/painel-mestre/monstros?usuarioId=" + usuarioId;
        } else if ("BOSS".equalsIgnoreCase(tipoFicha)) {
            return "redirect:/painel-mestre/boss?usuarioId=" + usuarioId;
        }
        
        // Se for PLAYER, volta pro painel do jogador
        return "redirect:/painel-player?usuarioId=" + usuarioId;
    }

    // ==================================================
    // REDIRECIONAR PARA O MANUAL
    // ==================================================
    @GetMapping("/painel-player/manual")
    public String redirecionarParaManual(@RequestParam(value = "usuarioId", required = false) Integer usuarioId) {
        if (usuarioId != null) {
            // Redireciona para a página do manual enviando o ID do jogador
            return "redirect:/Manual?usuarioId=" + usuarioId;
        }
        return "redirect:/Manual";
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
    
    // ==================================================
    // ABRIR O INVENTÁRIO DO JOGADOR
    // ==================================================
    @GetMapping("/inventario")
    public String abrirInventario(
            @RequestParam(value = "fichaId") Integer fichaId, 
            @RequestParam(value = "usuarioId") Integer usuarioId, 
            Model model) {
        
        // Passamos os IDs para o HTML, assim podemos fazer um botão de "Voltar" funcional na tela de inventário
        model.addAttribute("fichaId", fichaId);
        model.addAttribute("usuarioId", usuarioId);
        
        // Retorna a view "inventario.html" que está na sua pasta de templates
        return "inventario";
    }
}
