package com.rpgsystem.zero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rpgsystem.zero.model.PerfilAcesso;
import com.rpgsystem.zero.repository.UsuarioDAO;

@Controller
public class CadastroController {

    @GetMapping("/Cadastro")
    public String exibirCadastro() {
        return "Cadastro";
    }

    @PostMapping("/Cadastro")
    public String processarCadastro(
            @RequestParam String nome,
            @RequestParam String login,
            @RequestParam String senha,
            @RequestParam String tipoConta) {

        // Converte a string (PLAYER ou MESTRE) para o Enum PerfilAcesso
        PerfilAcesso perfil = PerfilAcesso.valueOf(tipoConta);

        // Chama o método do seu DAO que já existe
        boolean sucesso = UsuarioDAO.cadastrarUsuario(nome, login, senha, perfil);

        if (sucesso) {
            return "redirect:/Login?sucesso";
        } else {
            return "redirect:/Cadastro?erro";
        }
    }
}