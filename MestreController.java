package com.rpgsystem.zero.controller;

import com.rpgsystem.zero.model.Ficha;
import com.rpgsystem.zero.repository.FichaDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MestreController {

    @GetMapping("/painel-mestre")
    public String exibirPainelMestre(Model model) {
        
        // Vai buscar todas as fichas ao banco de dados
        List<Ficha> todasAsFichas = FichaDAO.buscarTodasAsFichas();
        
        // Envia a lista para o Thymeleaf com o nome "fichas"
        model.addAttribute("fichas", todasAsFichas);
        
        // Retorna o nome do ficheiro HTML que vamos criar a seguir
        return "painel-mestre"; 
    }
}