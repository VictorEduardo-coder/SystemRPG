package com.rpgsystem.zero.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rpgsystem.zero.model.Regra;
import com.rpgsystem.zero.model.Usuario;
import com.rpgsystem.zero.repository.RegraDAO;
import com.rpgsystem.zero.repository.UsuarioDAO;

@Controller
public class ManualController {

    @GetMapping("/Manual")
    public String exibirManual(
            @RequestParam(value = "usuarioId", required = false) Integer usuarioId, 
            Model model
    ) {
        
        // 1. Busca todas as regras no banco de dados
        List<Regra> todasAsRegras = RegraDAO.buscarTodasAsRegras();

        // 2. Agrupa as regras pela Categoria
        Map<String, List<Regra>> regrasPorCategoria = todasAsRegras.stream()
                .collect(Collectors.groupingBy(Regra::getCategoria));

        // 3. Envia o pacote de regras agrupadas
        model.addAttribute("regrasAgrupadas", regrasPorCategoria);

        // 4. Lógica para o botão de Voltar e verificação do Mestre
        String urlVoltar = "/index"; 
        boolean isMestre = false;    

        if (usuarioId != null) {
            Usuario usuario = UsuarioDAO.buscarUsuarioPorId(usuarioId);
            
            if (usuario != null) {
                
                // SOLUÇÃO: Converter para String e validar estritamente o texto!
                String perfilConta = String.valueOf(usuario.getPerfil()).toUpperCase();
                
                // Só será mestre se a conta tiver a palavra exata "MESTRE" ou "ADMIN"
                if (perfilConta.equals("MESTRE") || perfilConta.equals("ADMIN")) {
                    isMestre = true;
                    urlVoltar = "/painel-mestre?usuarioId=" + usuarioId; 
                } else {
                    isMestre = false;
                    urlVoltar = "/painel-player?usuarioId=" + usuarioId;
                }
                
                // (Opcional) Log para você ver no console quem o Java achou que é:
                System.out.println("Acesso ao Manual -> Usuário: " + usuario.getNome() + " | É Mestre? " + isMestre);
            }
        }
        
        // Envia as variáveis para o HTML 
        model.addAttribute("urlVoltar", urlVoltar);
        model.addAttribute("isMestre", isMestre);
        model.addAttribute("usuarioId", usuarioId);
        
        // 5. Retorna o HTML
        return "manual"; 
    }

    // ==================================================
    // ROTA PARA SALVAR A NOVA REGRA CRIADA PELO MESTRE
    // ==================================================
    @PostMapping("/Manual/criar")
    public String criarNovaRegra(
            @RequestParam(value = "usuarioId", required = false) Integer usuarioId,
            @RequestParam String categoria,
            @RequestParam String titulo,
            @RequestParam String descricao
    ) {
        if (usuarioId == null) {
            return "redirect:/index";
        }
        
        RegraDAO.criarRegra(titulo, descricao, categoria);
        return "redirect:/Manual?usuarioId=" + usuarioId;
    }
}