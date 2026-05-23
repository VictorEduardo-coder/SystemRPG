package com.rpgsystem.zero.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rpgsystem.zero.model.Classe;
import com.rpgsystem.zero.model.Habilidade;
import com.rpgsystem.zero.model.PerfilAcesso;
import com.rpgsystem.zero.model.Usuario;
import com.rpgsystem.zero.repository.ClasseDAO;
import com.rpgsystem.zero.repository.HabilidadeDAO;
import com.rpgsystem.zero.repository.UsuarioDAO;

@Controller
public class HabilidadeController {

    @GetMapping("/habilidades")
    public String verHabilidades(
            @RequestParam(defaultValue = "1") int classeId,
            @RequestParam(defaultValue = "1") int fichaId,
            @RequestParam(value = "usuarioId", required = false) Integer usuarioId,
            Model model) {
        
        if (usuarioId == null) {
            return "redirect:/index"; 
        }
        
        com.rpgsystem.zero.model.Ficha ficha = com.rpgsystem.zero.repository.FichaDAO.buscarFichaPorUsuario(usuarioId);
        int fichaIdReal = (ficha != null) ? ficha.getId() : fichaId;
        
        boolean isMestre = false;
        Usuario usuario = UsuarioDAO.buscarUsuarioPorId(usuarioId);
        if (usuario != null) {
            isMestre = PerfilAcesso.MESTRE.equals(usuario.getPerfil()) || PerfilAcesso.ADMIN.equals(usuario.getPerfil());
        }
        
        List<Classe> todasAsClasses = ClasseDAO.listarTodasAsClasses();
        List<Habilidade> todasAsHabilidades = HabilidadeDAO.listarTodasAsHabilidades(fichaIdReal);
        Classe classeAtual = ClasseDAO.buscarClassePorId(classeId);
        
        // NOVO/ATUALIZADO: Enviando a lista de Tiers direto do Java para não quebrar o HTML
        // Reflete exatamente os valores aceitos pelo CHECK constraint da tabela no banco
        List<String> tiers = java.util.Arrays.asList("PASSIVA", "TIER 1", "TIER 2", "TIER 3");
        model.addAttribute("tiers", tiers);

        model.addAttribute("todasAsClasses", todasAsClasses);
        model.addAttribute("todasAsHabilidades", todasAsHabilidades);
        model.addAttribute("classe", classeAtual);
        model.addAttribute("isMestre", isMestre);
        model.addAttribute("usuarioId", usuarioId);
        
        return "habilidades"; 
    }

    // ==================================================
    // SALVAR NOVA CLASSE
    // ==================================================
    @PostMapping("/classes/salvar")
    public String salvarClasse(
            @RequestParam String nomeClasse,
            @RequestParam(required = false, defaultValue = "") String bonus,
            @RequestParam(required = false, defaultValue = "") String debuff,
            @RequestParam String tipo,
            @RequestParam String descricao,
            @RequestParam(required = false) String classeBase1, // Recebe como String para evitar crash
            @RequestParam(required = false) String classeBase2, // Recebe como String para evitar crash
            @RequestParam Integer usuarioId) {
        
        // Converte os IDs manualmente apenas se eles não forem vazios
        Integer base1 = (classeBase1 != null && !classeBase1.isEmpty()) ? Integer.parseInt(classeBase1) : null;
        Integer base2 = (classeBase2 != null && !classeBase2.isEmpty()) ? Integer.parseInt(classeBase2) : null;
        
        // Salva no banco de dados e captura o ID real gerado
        int novoClasseId = ClasseDAO.salvarClasse(nomeClasse, bonus, debuff, tipo, descricao, base1, base2);
        
        // Redireciona exibindo a classe que acabou de ser criada
        return "redirect:/habilidades?classeId=" + novoClasseId + "&usuarioId=" + usuarioId;
    }

    // ==================================================
    // SALVAR NOVA HABILIDADE
    // ==================================================
    @PostMapping("/habilidades/salvar")
    public String salvarHabilidade(
            @RequestParam String nome,
            @RequestParam String tier,
            @RequestParam String descricao,
            @RequestParam Integer usuarioId,
            @RequestParam(defaultValue = "1") int classeId) { 
            
        // Agora o parâmetro "tier" recebido do formulário HTML será enviado exatamente como "TIER 1", "TIER 2", etc.
        HabilidadeDAO.salvarHabilidade(nome, descricao, tier, classeId);
        
        return "redirect:/habilidades?classeId=" + classeId + "&usuarioId=" + usuarioId;
    }
}
