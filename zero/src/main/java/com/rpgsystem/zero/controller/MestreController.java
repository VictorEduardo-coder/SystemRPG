package com.rpgsystem.zero.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rpgsystem.zero.model.Ficha;
import com.rpgsystem.zero.model.Usuario;
import com.rpgsystem.zero.repository.FichaDAO;
import com.rpgsystem.zero.service.AutorizacaoService;

@Controller
public class MestreController {

    private final AutorizacaoService autorizacaoService;

    public MestreController(AutorizacaoService autorizacaoService) {
        this.autorizacaoService = autorizacaoService;
    }

    @GetMapping("/painel-mestre")
    public String exibirPainelMestre(Authentication authentication, Model model) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        autorizacaoService.exigirMestreOuAdmin(usuario, "acessar_painel_mestre");

        List<Ficha> todasAsFichas = FichaDAO.buscarTodasAsFichas();
        model.addAttribute("fichas", todasAsFichas);
        model.addAttribute("usuarioLogadoId", usuario.getId());
        model.addAttribute("usuarioId", usuario.getId());

        return "painel-mestre";
    }

    @PostMapping("/painel-mestre/fichas/deletar")
    public String deletarFichaMestre(
            @RequestParam("fichaId") int fichaId,
            Authentication authentication
    ) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        autorizacaoService.exigirMestreOuAdmin(usuario, "deletar_ficha_mestre");

        FichaDAO.deletarFichaPorId(fichaId);
        return "redirect:/painel-mestre";
    }

    @GetMapping("/painel-mestre/manual")
    public String redirecionarParaManual() {
        return "redirect:/Manual";
    }

    @GetMapping("/painel-mestre/habilidades")
    public String redirecionarParaHabilidades() {
        return "redirect:/habilidades";
    }
}
