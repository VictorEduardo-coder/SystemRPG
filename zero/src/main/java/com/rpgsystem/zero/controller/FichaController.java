package com.rpgsystem.zero.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class FichaController {

    private final AutorizacaoService autorizacaoService;

    public FichaController(AutorizacaoService autorizacaoService) {
        this.autorizacaoService = autorizacaoService;
    }

    @GetMapping("/fichas/editar")
    public String abrirTelaEdicao(
            @RequestParam("fichaId") int fichaId,
            Authentication authentication,
            Model model
    ) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        Ficha ficha = autorizacaoService.exigirAcessoFicha(usuario, fichaId);
        boolean isMestre = autorizacaoService.isMestreOuAdmin(usuario);

        List<Classe> todasAsClasses = ClasseDAO.listarTodasAsClasses();
        Corrupcao corrupcao = Corrupcao.calcular(ficha.getCorrupcao());

        model.addAttribute("ficha", ficha);
        model.addAttribute("classesDisponiveis", todasAsClasses);
        model.addAttribute("usuarioId", usuario.getId());
        model.addAttribute("isMestre", isMestre);
        model.addAttribute("corrupcao", corrupcao);

        return "editar-ficha";
    }

    @PostMapping("/fichas/salvar")
    public String salvarEdicao(
            @ModelAttribute Ficha fichaAtualizada,
            Authentication authentication
    ) {
        Usuario usuarioLogado = autorizacaoService.exigirUsuarioAutenticado(authentication);
        Ficha fichaOriginal = autorizacaoService.exigirAcessoFicha(usuarioLogado, fichaAtualizada.getId());
        boolean isMestre = autorizacaoService.isMestreOuAdmin(usuarioLogado);

        // Impede transferência indevida de propriedade da ficha
        fichaAtualizada.setUsuarioId(fichaOriginal.getUsuarioId());

        if (!isMestre) {
            fichaAtualizada.setSaldoAtual(fichaOriginal.getSaldoAtual());
            fichaAtualizada.setCustoVida(fichaOriginal.getCustoVida());
            fichaAtualizada.setDiasRestantes(fichaOriginal.getDiasRestantes());
            fichaAtualizada.setNutricao(fichaOriginal.getNutricao());
            fichaAtualizada.setConexao(fichaOriginal.getConexao());
            fichaAtualizada.setTitulo(fichaOriginal.getTitulo());
        }

        FichaDAO.atualizarFicha(fichaAtualizada);

        if (isMestre) {
            return "redirect:/painel-mestre";
        }
        return "redirect:/painel-player";
    }
}
