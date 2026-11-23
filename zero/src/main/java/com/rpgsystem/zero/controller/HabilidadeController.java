package com.rpgsystem.zero.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rpgsystem.zero.model.Classe;
import com.rpgsystem.zero.model.Ficha;
import com.rpgsystem.zero.model.Habilidade;
import com.rpgsystem.zero.model.Usuario;
import com.rpgsystem.zero.repository.ClasseDAO;
import com.rpgsystem.zero.repository.FichaDAO;
import com.rpgsystem.zero.repository.HabilidadeDAO;
import com.rpgsystem.zero.service.AutorizacaoService;

@Controller
public class HabilidadeController {

    private final AutorizacaoService autorizacaoService;

    public HabilidadeController(AutorizacaoService autorizacaoService) {
        this.autorizacaoService = autorizacaoService;
    }

    @GetMapping("/habilidades")
    public String verHabilidades(
            @RequestParam(defaultValue = "1") int classeId,
            Authentication authentication,
            Model model
    ) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        boolean isMestre = autorizacaoService.isMestreOuAdmin(usuario);

        Ficha ficha = FichaDAO.buscarFichaPorUsuario(usuario.getId());
        int fichaIdReal = (ficha != null) ? ficha.getId() : 0;

        List<Classe> todasAsClasses = ClasseDAO.listarTodasAsClasses();
        List<Habilidade> todasAsHabilidades = HabilidadeDAO.listarTodasAsHabilidades(fichaIdReal);
        Classe classeAtual = ClasseDAO.buscarClassePorId(classeId);
        List<String> tiers = Arrays.asList("PASSIVA", "TIER 1", "TIER 2", "TIER 3", "ULTIMATE");

        model.addAttribute("tiers", tiers);
        model.addAttribute("todasAsClasses", todasAsClasses);
        model.addAttribute("todasAsHabilidades", todasAsHabilidades);
        model.addAttribute("classe", classeAtual);
        model.addAttribute("isMestre", isMestre);
        model.addAttribute("usuarioId", usuario.getId());

        return "habilidades";
    }

    @PostMapping("/classes/salvar")
    public String salvarClasse(
            @RequestParam String nomeClasse,
            @RequestParam(required = false, defaultValue = "") String bonus,
            @RequestParam(required = false, defaultValue = "") String debuff,
            @RequestParam String tipo,
            @RequestParam String descricao,
            @RequestParam(required = false) String classeBase1,
            @RequestParam(required = false) String classeBase2,
            Authentication authentication
    ) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        autorizacaoService.exigirMestreOuAdmin(usuario, "salvar_classe");

        Integer base1 = (classeBase1 != null && !classeBase1.isEmpty()) ? Integer.parseInt(classeBase1) : null;
        Integer base2 = (classeBase2 != null && !classeBase2.isEmpty()) ? Integer.parseInt(classeBase2) : null;

        int novoClasseId = ClasseDAO.salvarClasse(nomeClasse, bonus, debuff, tipo, descricao, base1, base2);
        return "redirect:/habilidades?classeId=" + novoClasseId;
    }

    @PostMapping("/habilidades/salvar")
    public String salvarHabilidade(
            @RequestParam String nome,
            @RequestParam String tier,
            @RequestParam String descricao,
            @RequestParam String dano,
            @RequestParam int custoMana,
            @RequestParam(defaultValue = "1") int classeId,
            Authentication authentication
    ) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        autorizacaoService.exigirMestreOuAdmin(usuario, "salvar_habilidade");

        HabilidadeDAO.salvarHabilidade(nome, descricao, tier, dano, custoMana, classeId);
        return "redirect:/habilidades?classeId=" + classeId;
    }
}
