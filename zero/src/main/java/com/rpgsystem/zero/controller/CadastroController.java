package com.rpgsystem.zero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rpgsystem.zero.model.PerfilAcesso;
import com.rpgsystem.zero.repository.UsuarioDAO;

@Controller
public class CadastroController {

    /*
     * Abre a página de cadastro (via GET)
     */
    @GetMapping("/cadastro")
    public String abrirCadastro() {
        return "cadastro";
    }

    /*
     * Processa o formulário enviado pelo HTML (via POST)
     */
    @PostMapping("/cadastro")
    public String realizarCadastro(
            @RequestParam String nome,
            @RequestParam String login,
            @RequestParam String senha,
            @RequestParam String tipoConta,
            Model model
    ) {

        try {
            // 1. Validações básicas para evitar campos vazios
            if (nome == null || nome.isBlank() ||
                login == null || login.isBlank() ||
                senha == null || senha.isBlank() ||
                tipoConta == null || tipoConta.isBlank()) {

                model.addAttribute("erro", "Todos os campos devem ser preenchidos.");
                return "cadastro";
            }

            // 2. Converte a String do formulário HTML (PLAYER ou MESTRE) para o seu Enum
            PerfilAcesso perfil;
            if (tipoConta.equalsIgnoreCase("MESTRE")) {
                perfil = PerfilAcesso.MESTRE;
            } else {
                perfil = PerfilAcesso.PLAYER;
            }

            // 3. Salva no banco de dados usando a MESMA lógica que você usava no console
            boolean sucesso = UsuarioDAO.cadastrarUsuario(nome, login, senha, perfil);

            if (sucesso) {
                System.out.println("[WEB LOG] Nova conta criada com sucesso: " + login);
                
                // Redireciona para a página de login após o cadastro dar certo
                return "redirect:/index"; 

            } else {
                // Caso o login já exista no banco ou dê erro no DAO
                model.addAttribute("erro", "Erro ao criar conta. O login já existe ou ocorreu um erro interno.");
                return "cadastro";
            }

        } catch (Exception e) {
            e.printStackTrace(); // Apenas para debug no terminal
            model.addAttribute("erro", "Erro crítico ao processar o cadastro.");
            return "cadastro";
        }
    }
}