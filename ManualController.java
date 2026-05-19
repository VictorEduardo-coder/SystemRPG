package com.rpgsystem.zero.controller;

import com.rpgsystem.zero.model.Regra;
import com.rpgsystem.zero.repository.RegraDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ManualController {

    @GetMapping("/manual")
    public String exibirManual(Model model) {
        
        // 1. Busca todas as regras no banco de dados
        List<Regra> todasAsRegras = RegraDAO.buscarTodasAsRegras();

        // 2. Agrupa as regras pela Categoria (ex: "Combate", "Magia") usando recursos do Java 8+
        Map<String, List<Regra>> regrasPorCategoria = todasAsRegras.stream()
                .collect(Collectors.groupingBy(Regra::getCategoria));

        // 3. Envia o pacote de regras agrupadas para a página HTML renderizar
        model.addAttribute("regrasAgrupadas", regrasPorCategoria);

        // 4. Retorna o nome do arquivo HTML (manual.html) que está na pasta templates
        return "manual"; 
    }
}