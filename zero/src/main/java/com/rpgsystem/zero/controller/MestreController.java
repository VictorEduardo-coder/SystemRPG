package com.rpgsystem.zero.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        List<Ficha> todasAsFichas = FichaDAO.buscarTodasAsFichas();

        model.addAttribute("fichas", todasAsFichas);
        model.addAttribute("usuarioLogadoId", usuarioId);
        model.addAttribute("usuarioId", usuarioId);

        return "painel-mestre";
    }

    @PostMapping("/painel-mestre/fichas/deletar")
    public String deletarFichaMestre(
            @RequestParam("fichaId") int fichaId,
            @RequestParam("usuarioId") int usuarioId
    ) {
        FichaDAO.deletarFichaPorId(fichaId);
        return "redirect:/painel-mestre?usuarioId=" + usuarioId;
    }

    @GetMapping("/painel-mestre/manual")
    public String redirecionarParaManual(@RequestParam(value = "usuarioId", required = false) Integer usuarioId) {
        if (usuarioId != null) {
            return "redirect:/Manual?usuarioId=" + usuarioId;
        }
        return "redirect:/Manual";
    }

    @GetMapping("/painel-mestre/habilidades")
    public String redirecionarParaHabilidades(@RequestParam(value = "usuarioId", required = false) Integer usuarioId) {
        if (usuarioId != null) {
            return "redirect:/habilidades?usuarioId=" + usuarioId;
        }
        return "redirect:/habilidades";
    }
}