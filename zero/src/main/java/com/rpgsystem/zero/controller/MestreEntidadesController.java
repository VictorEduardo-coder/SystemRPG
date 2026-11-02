package com.rpgsystem.zero.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rpgsystem.zero.model.Ficha;
import com.rpgsystem.zero.model.FichaBoss;
import com.rpgsystem.zero.model.FichaMonstro;
import com.rpgsystem.zero.model.FichaNPC;
import com.rpgsystem.zero.repository.FichaDAO;

@Controller
public class MestreEntidadesController {

    // ==================================================
    // ROTA: PAINEL DE NPCS
    // ==================================================
    @GetMapping("/painel-mestre/npcs")
    public String exibirNPCs(
            @RequestParam(value = "usuarioId", required = false) Integer usuarioId, 
            Model model
    ) {
        List<Ficha> todasAsFichas = FichaDAO.buscarTodasAsFichas();
        
        List<Ficha> npcs = todasAsFichas.stream()
                .filter(ficha -> ficha instanceof FichaNPC)
                .collect(Collectors.toList());

        model.addAttribute("fichasNPC", npcs);
        model.addAttribute("usuarioId", usuarioId);
        
        return "painel-npcs";
    }

    @PostMapping("/painel-mestre/npcs/deletar")
    public String deletarNPC(
            @RequestParam("fichaId") int fichaId, 
            @RequestParam("usuarioId") int usuarioId
    ) {
        FichaDAO.deletarFicha(fichaId, usuarioId);
        return "redirect:/painel-mestre/npcs?usuarioId=" + usuarioId;
    }

    // ==================================================
    // ROTA: PAINEL DE MONSTROS
    // ==================================================
    @GetMapping("/painel-mestre/monstros")
    public String exibirMonstros(
            @RequestParam(value = "usuarioId", required = false) Integer usuarioId, 
            Model model
    ) {
        List<Ficha> todasAsFichas = FichaDAO.buscarTodasAsFichas();
        
        List<Ficha> monstros = todasAsFichas.stream()
                .filter(ficha -> ficha instanceof FichaMonstro)
                .collect(Collectors.toList());

        model.addAttribute("fichasMonstro", monstros);
        model.addAttribute("usuarioId", usuarioId);
        
        return "painel-monstros";
    }

    @PostMapping("/painel-mestre/monstros/deletar")
    public String deletarMonstro(
            @RequestParam("fichaId") int fichaId, 
            @RequestParam("usuarioId") int usuarioId
    ) {
        FichaDAO.deletarFicha(fichaId, usuarioId);
        return "redirect:/painel-mestre/monstros?usuarioId=" + usuarioId;
    }

    // ==================================================
    // ROTA: PAINEL DE BOSSES
    // ==================================================
    @GetMapping("/painel-mestre/boss")
    public String exibirBosses(
            @RequestParam(value = "usuarioId", required = false) Integer usuarioId, 
            Model model
    ) {
        List<Ficha> todasAsFichas = FichaDAO.buscarTodasAsFichas();
        
        List<Ficha> bosses = todasAsFichas.stream()
                .filter(ficha -> ficha instanceof FichaBoss)
                .collect(Collectors.toList());

        model.addAttribute("fichasBoss", bosses);
        model.addAttribute("usuarioId", usuarioId);
        
        return "painel-boss";
    }

    @PostMapping("/painel-mestre/boss/deletar")
    public String deletarBoss(
            @RequestParam("fichaId") int fichaId, 
            @RequestParam("usuarioId") int usuarioId
    ) {
        FichaDAO.deletarFicha(fichaId, usuarioId);
        return "redirect:/painel-mestre/boss?usuarioId=" + usuarioId;
    }
}