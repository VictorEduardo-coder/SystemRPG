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

    /*
     * Abre a tela inicial/login ao acessar localhost:8080/ ou localhost:8080/index
     */
    @GetMapping({"/", "/login", "/index"})
    public String abrirLogin() {
        // Agora o Spring Boot vai procurar o arquivo "index.html" na pasta templates
        return "index"; 
    }

   
   @PostMapping("/login")
public String realizarLogin(@RequestParam String login, @RequestParam String senha, Model model) {
    Usuario usuario = UsuarioDAO.autenticarUsuario(login, senha);
    
    if (usuario != null) {
        // Redireciona passando o ID do usuário que foi autenticado
        if (PerfilAcesso.MESTRE.equals(usuario.getPerfil()) || PerfilAcesso.ADMIN.equals(usuario.getPerfil())) {
            return "redirect:/painel-mestre?usuarioId=" + usuario.getId();
        } else {
            return "redirect:/painel-player?usuarioId=" + usuario.getId();
        }
    }
    
    // Login falhou, volta para o index
    return "redirect:/index?erro=true";

    }

}