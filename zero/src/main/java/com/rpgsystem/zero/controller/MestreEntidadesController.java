package com.rpgsystem.zero.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rpgsystem.zero.model.Ficha;
import com.rpgsystem.zero.model.FichaBoss;
import com.rpgsystem.zero.model.FichaMonstro;
import com.rpgsystem.zero.model.FichaNPC;
import com.rpgsystem.zero.model.Usuario;
import com.rpgsystem.zero.repository.FichaDAO;
import com.rpgsystem.zero.service.AutorizacaoService;

@Controller
public class MestreEntidadesController {

    private final AutorizacaoService autorizacaoService;

    public MestreEntidadesController(AutorizacaoService autorizacaoService) {
        this.autorizacaoService = autorizacaoService;
    }

    @GetMapping("/painel-mestre/npcs")
    public String exibirNPCs(Authentication authentication, Model model) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        autorizacaoService.exigirMestreOuAdmin(usuario, "listar_npcs");

        List<Ficha> npcs = FichaDAO.buscarTodasAsFichas().stream()
                .filter(ficha -> ficha instanceof FichaNPC)
                .collect(Collectors.toList());

        model.addAttribute("fichasNPC", npcs);
        model.addAttribute("usuarioId", usuario.getId());
        return "painel-npcs";
    }

    @PostMapping("/painel-mestre/npcs/deletar")
    public String deletarNPC(@RequestParam("fichaId") int fichaId, Authentication authentication) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        autorizacaoService.exigirMestreOuAdmin(usuario, "deletar_npc");

        FichaDAO.deletarFichaPorId(fichaId);
        return "redirect:/painel-mestre/npcs";
    }

    @GetMapping("/painel-mestre/monstros")
    public String exibirMonstros(Authentication authentication, Model model) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        autorizacaoService.exigirMestreOuAdmin(usuario, "listar_monstros");

        List<Ficha> monstros = FichaDAO.buscarTodasAsFichas().stream()
                .filter(ficha -> ficha instanceof FichaMonstro)
                .collect(Collectors.toList());

        model.addAttribute("fichasMonstro", monstros);
        model.addAttribute("usuarioId", usuario.getId());
        return "painel-monstros";
    }

    @PostMapping("/painel-mestre/monstros/deletar")
    public String deletarMonstro(@RequestParam("fichaId") int fichaId, Authentication authentication) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        autorizacaoService.exigirMestreOuAdmin(usuario, "deletar_monstro");

        FichaDAO.deletarFichaPorId(fichaId);
        return "redirect:/painel-mestre/monstros";
    }

    @GetMapping("/painel-mestre/boss")
    public String exibirBosses(Authentication authentication, Model model) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        autorizacaoService.exigirMestreOuAdmin(usuario, "listar_bosses");

        List<Ficha> bosses = FichaDAO.buscarTodasAsFichas().stream()
                .filter(ficha -> ficha instanceof FichaBoss)
                .collect(Collectors.toList());

        model.addAttribute("fichasBoss", bosses);
        model.addAttribute("usuarioId", usuario.getId());
        return "painel-boss";
    }

    @PostMapping("/painel-mestre/boss/deletar")
    public String deletarBoss(@RequestParam("fichaId") int fichaId, Authentication authentication) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        autorizacaoService.exigirMestreOuAdmin(usuario, "deletar_boss");

        FichaDAO.deletarFichaPorId(fichaId);
        return "redirect:/painel-mestre/boss";
    }
}
