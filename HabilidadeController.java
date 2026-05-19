package com.rpgsystem.zero.controller;

import com.rpgsystem.zero.model.Habilidade;
import com.rpgsystem.zero.repository.HabilidadeDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HabilidadeController {

    // No futuro, o fichaId virá da sessão do usuário logado. 
    // Por enquanto, passaremos na URL para testar: /habilidades?classeId=1&fichaId=1
    @GetMapping("/habilidades")
    public String verHabilidades(
            @RequestParam(defaultValue = "1") int classeId,
            @RequestParam(defaultValue = "1") int fichaId,
            Model model) {
        
        List<Habilidade> habilidades = HabilidadeDAO.buscarHabilidadesDaClasseParaFicha(classeId, fichaId);
        
        model.addAttribute("habilidades", habilidades);
        
        return "habilidades"; 
    }
}