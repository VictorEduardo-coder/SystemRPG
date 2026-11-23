package com.rpgsystem.zero.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rpgsystem.zero.model.Classe;
import com.rpgsystem.zero.model.Corrupcao;
import com.rpgsystem.zero.model.Ficha;
import com.rpgsystem.zero.model.Usuario;
import com.rpgsystem.zero.repository.ClasseDAO;
import com.rpgsystem.zero.repository.FichaDAO;
import com.rpgsystem.zero.service.AutorizacaoService;

@Controller
public class JogadorController {

    private final AutorizacaoService autorizacaoService;

    public JogadorController(AutorizacaoService autorizacaoService) {
        this.autorizacaoService = autorizacaoService;
    }

    @GetMapping("/painel-player")
    public String exibirPainelPlayer(
            @RequestParam(value = "fichaId", required = false) Integer fichaId,
            Authentication authentication,
            Model model
    ) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        boolean isMestre = autorizacaoService.isMestreOuAdmin(usuario);

        Ficha ficha;
        if (fichaId != null) {
            ficha = autorizacaoService.exigirAcessoFicha(usuario, fichaId);
        } else {
            ficha = FichaDAO.buscarFichaPorUsuario(usuario.getId());
        }

        List<Classe> todasAsClasses = ClasseDAO.listarTodasAsClasses();
        Corrupcao corrupcao = Corrupcao.calcular(ficha != null ? ficha.getCorrupcao() : 0);

        model.addAttribute("ficha", ficha);
        model.addAttribute("classesDisponiveis", todasAsClasses);
        model.addAttribute("usuarioId", usuario.getId());
        model.addAttribute("isMestre", isMestre);
        model.addAttribute("corrupcao", corrupcao);

        return "painel-player";
    }

    @PostMapping("/fichas/criar")
    public String criarNovaFicha(
            @RequestParam String nomePersonagem,
            @RequestParam(required = false, defaultValue = "PLAYER") String tipoFicha,
            Authentication authentication
    ) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);

        if (!"PLAYER".equalsIgnoreCase(tipoFicha)) {
            autorizacaoService.exigirMestreOuAdmin(usuario, "criar_entidade_" + tipoFicha);
        }

        FichaDAO.criarFicha(usuario.getId(), nomePersonagem, tipoFicha);

        if ("NPC".equalsIgnoreCase(tipoFicha)) {
            return "redirect:/painel-mestre/npcs";
        } else if ("MONSTRO".equalsIgnoreCase(tipoFicha)) {
            return "redirect:/painel-mestre/monstros";
        } else if ("BOSS".equalsIgnoreCase(tipoFicha)) {
            return "redirect:/painel-mestre/boss";
        }

        return "redirect:/painel-player";
    }

    @GetMapping("/painel-player/manual")
    public String redirecionarParaManual() {
        return "redirect:/Manual";
    }

    @PostMapping("/fichas/deletar")
    public String deletarFicha(
            @RequestParam int fichaId,
            Authentication authentication
    ) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        Ficha ficha = autorizacaoService.exigirAcessoFicha(usuario, fichaId);

        FichaDAO.deletarFicha(ficha.getId(), ficha.getUsuarioId());
        return "redirect:/painel-player";
    }

    @GetMapping("/inventario")
    public String abrirInventario(
            @RequestParam("fichaId") Integer fichaId,
            Authentication authentication,
            Model model
    ) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        autorizacaoService.exigirAcessoFicha(usuario, fichaId);

        model.addAttribute("fichaId", fichaId);
        model.addAttribute("usuarioId", usuario.getId());
        return "inventario";
    }
}
