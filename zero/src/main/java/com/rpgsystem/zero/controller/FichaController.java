package com.rpgsystem.zero.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rpgsystem.zero.model.Classe;
import com.rpgsystem.zero.model.Ficha;
import com.rpgsystem.zero.model.PerfilAcesso;
import com.rpgsystem.zero.model.Usuario;
import com.rpgsystem.zero.repository.ClasseDAO;
import com.rpgsystem.zero.repository.FichaDAO;
import com.rpgsystem.zero.repository.UsuarioDAO;

@Controller
public class FichaController {

    // ==================================================
    // ABRIR TELA DE EDIÇÃO
    // ==================================================
    @GetMapping("/fichas/editar")
    public String abrirTelaEdicao(
            @RequestParam("fichaId") int fichaId,
            @RequestParam("usuarioId") int usuarioId,
            Model model
    ) {
        // 1. Busca a Ficha e o Usuário
        Ficha ficha = FichaDAO.buscarFichaPorId(fichaId);
        Usuario usuario = UsuarioDAO.buscarUsuarioPorId(usuarioId);
        
        if (ficha != null && usuario != null) {
            // 2. Verifica se é Mestre
            boolean isMestre = PerfilAcesso.MESTRE.equals(usuario.getPerfil()) || PerfilAcesso.ADMIN.equals(usuario.getPerfil());
            
            // 3. Busca todas as Classes para preencher o menu
            List<Classe> todasAsClasses = ClasseDAO.listarTodasAsClasses();
            
            // 4. Envia tudo para o HTML
            model.addAttribute("ficha", ficha);
            model.addAttribute("classesDisponiveis", todasAsClasses);
            model.addAttribute("usuarioId", usuarioId);
            model.addAttribute("isMestre", isMestre);
            
            return "editar-ficha"; 
        }
        
        return "redirect:/index";
    }

    // ==================================================
    // SALVAR FICHA EDITADA
    // ==================================================
@PostMapping("/fichas/salvar")
    public String salvarEdicao(
            @ModelAttribute Ficha fichaAtualizada, 
            @RequestParam(value = "usuarioId", required = false) Integer usuarioId 
    ) {
        System.out.println("--- INICIANDO TENTATIVA DE SALVAR FICHA ---");
        System.out.println("Ficha recebida do HTML: " + fichaAtualizada.getNome() + " | ID da Ficha: " + fichaAtualizada.getId());
        System.out.println("ID do Usuário logado: " + usuarioId);

        if (usuarioId == null) {
            System.out.println("ERRO: usuarioId é nulo! Abortando...");
            return "redirect:/index";
        }

        Usuario usuarioLogado = UsuarioDAO.buscarUsuarioPorId(usuarioId);
        Ficha fichaOriginal = FichaDAO.buscarFichaPorId(fichaAtualizada.getId());
        
        if (fichaOriginal == null) {
            System.out.println("ERRO: Não encontrou a ficha original no banco de dados!");
        } else {
            System.out.println("Dono original da ficha (usuario_id): " + fichaOriginal.getUsuarioId());
        }

        boolean isMestre = false;
        
        if (fichaOriginal != null && usuarioLogado != null) {
            boolean isDono = (fichaOriginal.getUsuarioId() == usuarioLogado.getId());
            isMestre = PerfilAcesso.MESTRE.equals(usuarioLogado.getPerfil()) || PerfilAcesso.ADMIN.equals(usuarioLogado.getPerfil());
            
            System.out.println("O usuário é o Dono? " + isDono);
            System.out.println("O usuário é Mestre? " + isMestre);
            
            if (isDono || isMestre) {
                // PROTEÇÃO: Se não for mestre, mantém os dados secretos originais
                if (!isMestre) {
                    fichaAtualizada.setSaldoAtual(fichaOriginal.getSaldoAtual());
                    fichaAtualizada.setCustoVida(fichaOriginal.getCustoVida());
                    fichaAtualizada.setDiasRestantes(fichaOriginal.getDiasRestantes());
                    fichaAtualizada.setNutricao(fichaOriginal.getNutricao());
                    fichaAtualizada.setConexao(fichaOriginal.getConexao());
                    fichaAtualizada.setTitulo(fichaOriginal.getTitulo());
                }
                
                boolean sucesso = FichaDAO.atualizarFicha(fichaAtualizada);
                System.out.println("RESULTADO DO SALVAMENTO NO MYSQL: " + (sucesso ? "SUCESSO!" : "FALHA NO SQL!"));
            } else {
                System.out.println("BLOQUEADO: O utilizador não tem permissão para editar esta ficha.");
            }
        }
        
        System.out.println("--- FIM DA TENTATIVA ---");
        
        if (isMestre) {
            return "redirect:/painel-mestre?usuarioId=" + usuarioId;
        }
        
        return "redirect:/painel-player?usuarioId=" + usuarioId;
    }
}