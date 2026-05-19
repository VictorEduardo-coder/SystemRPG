package com.rpgsystem.zero.controller;

import com.rpgsystem.zero.model.Ficha;
import com.rpgsystem.zero.repository.FichaDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FichaWebController {

   // ==================================================
    // GUARDAR AS ALTERAÇÕES NO BANCO DE DADOS (COM SEGURANÇA)
    // ==================================================
    @PostMapping("/fichas/salvar")
    public String salvarEdicao(
            @RequestParam int fichaId, @RequestParam int usuarioLogadoId,
            @RequestParam String nome, @RequestParam String classe, @RequestParam String titulo,
            @RequestParam int forca, @RequestParam int vida, @RequestParam int agilidade,
            @RequestParam int mana, @RequestParam int carisma, @RequestParam int sorte) {
        
        // 1. Vai buscar a ficha e o utilizador à base de dados
        Ficha ficha = FichaDAO.buscarFichaPorId(fichaId);
        com.rpgsystem.zero.model.Usuario usuario = com.rpgsystem.zero.repository.UsuarioDAO.buscarUsuarioPorId(usuarioLogadoId);
        
        if (ficha != null && usuario != null) {
            
            // 2. A TRAVA DE SEGURANÇA: 
            // O utilizador só pode editar se for o dono da ficha (IDs iguais) 
            // OU se tiver o Perfil de MESTRE/ADMIN
            boolean isDono = (ficha.getUsuarioId() == usuario.getId());
            boolean isMestre = (usuario.getPerfil() == com.rpgsystem.zero.model.PerfilAcesso.MESTRE || usuario.getPerfil() == com.rpgsystem.zero.model.PerfilAcesso.ADMIN);
            
            if (isDono || isMestre) {
                // Atualiza os valores
                ficha.setNome(nome);
                ficha.setClasse(classe);
                ficha.setTitulo(titulo);
                ficha.setForca(forca);
                ficha.setVida(vida);
                ficha.setAgilidade(agilidade);
                ficha.setMana(mana);
                ficha.setCarisma(carisma);
                ficha.setSorte(sorte);
                
                FichaDAO.atualizarFicha(ficha);
                System.out.println("Ficha " + fichaId + " atualizada com sucesso por " + usuario.getNome());
            } else {
                // Tentativa de Hack Bloqueada!
                System.out.println("ALERTA DE SEGURANÇA: Utilizador " + usuario.getNome() + " tentou editar a ficha de outro jogador!");
            }
        }
        
        // Se for o Mestre a editar, volta para o painel do Mestre. Se for o Jogador, volta para o dele.
        if (usuario != null && usuario.getPerfil() == com.rpgsystem.zero.model.PerfilAcesso.MESTRE) {
            return "redirect:/painel-mestre";
        }
        
        return "redirect:/painel-player?usuarioId=" + usuarioLogadoId;
    }
}