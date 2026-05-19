package com.rpgsystem.zero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rpgsystem.zero.model.PerfilAcesso;
import com.rpgsystem.zero.model.Usuario;
import com.rpgsystem.zero.repository.UsuarioDAO;

@Controller
public class LoginController {

    // ==========================================
    // 1. EXIBIR A TELA DE LOGIN
    // ==========================================
    @GetMapping("/login")
    public String login() {
        // CORREÇÃO: Deve ser exatamente o nome do ficheiro (sem o .html)
        return "LoginRPG"; 
    }

    // ==========================================
    // 2. PROCESSAR O BOTÃO "ENTRAR"
    // ==========================================
    @PostMapping("/login")
    public String processarLogin(
            @RequestParam("email") String loginDigitado, // Captura o campo name="email" do HTML
            @RequestParam("senha") String senhaDigitada,
            Model model) {

        // Verifica no banco de dados se o usuário e senha existem e estão corretos
        Usuario usuario = UsuarioDAO.autenticar(loginDigitado, senhaDigitada);

        if (usuario != null) {
            // SUCESSO: Redireciona com base no Perfil do usuário
            if (usuario.getPerfil() == PerfilAcesso.MESTRE || usuario.getPerfil() == PerfilAcesso.ADMIN) {
                // Vai para o painel de Mestre e passa o ID para evitar erros na tela do Mestre
                return "redirect:/painel-mestre?usuarioId=" + usuario.getId();
            } else {
                // Vai para o painel de Player
                return "redirect:/painel-player?usuarioId=" + usuario.getId();
            }
        } else {
            // FALHA: Volta para a tela de login
            model.addAttribute("erro", "Credenciais incorretas!");
            return "LoginRPG";
        }
    }
}