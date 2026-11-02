package com.rpgsystem.zero.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rpgsystem.zero.model.PerfilAcesso;
import com.rpgsystem.zero.model.Usuario;
import com.rpgsystem.zero.repository.UsuarioDAO;

@Controller
public class LoginController {

    @GetMapping({"/", "/login", "/index"})
    public String abrirLogin(
            @RequestParam(value = "erro", required = false) String erro,
            Authentication authentication,
            Model model
    ) {
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            Usuario usuario = UsuarioDAO.buscarUsuarioPorLogin(authentication.getName());
            if (usuario != null) {
                if (PerfilAcesso.MESTRE.equals(usuario.getPerfil()) || PerfilAcesso.ADMIN.equals(usuario.getPerfil())) {
                    return "redirect:/painel-mestre?usuarioId=" + usuario.getId();
                }
                return "redirect:/painel-player?usuarioId=" + usuario.getId();
            }
        }

        if (erro != null) {
            model.addAttribute("erro", "Login ou palavra-passe inválidos.");
        }

        return "index";
    }
}