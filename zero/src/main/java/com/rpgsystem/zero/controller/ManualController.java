package com.rpgsystem.zero.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rpgsystem.zero.model.Regra;
import com.rpgsystem.zero.model.Usuario;
import com.rpgsystem.zero.repository.RegraDAO;
import com.rpgsystem.zero.service.AutorizacaoService;

@Controller
public class ManualController {

    private final AutorizacaoService autorizacaoService;

    public ManualController(AutorizacaoService autorizacaoService) {
        this.autorizacaoService = autorizacaoService;
    }

    @GetMapping("/Manual")
    public String exibirManual(Authentication authentication, Model model) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        boolean isMestre = autorizacaoService.isMestreOuAdmin(usuario);

        List<Regra> todasAsRegras = RegraDAO.buscarTodasAsRegras();
        Map<String, List<Regra>> regrasPorCategoria = todasAsRegras.stream()
                .collect(Collectors.groupingBy(Regra::getCategoria));

        model.addAttribute("regrasAgrupadas", regrasPorCategoria);
        model.addAttribute("urlVoltar", isMestre ? "/painel-mestre" : "/painel-player");
        model.addAttribute("isMestre", isMestre);
        model.addAttribute("usuarioId", usuario.getId());

        return "manual";
    }

    @PostMapping("/Manual/criar")
    public String criarNovaRegra(
            @RequestParam String categoria,
            @RequestParam String titulo,
            @RequestParam String descricao,
            Authentication authentication
    ) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        autorizacaoService.exigirMestreOuAdmin(usuario, "criar_regra_manual");

        RegraDAO.criarRegra(titulo, descricao, categoria);
        return "redirect:/Manual";
    }
}
