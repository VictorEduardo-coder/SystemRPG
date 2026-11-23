package com.rpgsystem.zero.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rpgsystem.zero.model.ItemRPG;
import com.rpgsystem.zero.model.Usuario;
import com.rpgsystem.zero.repository.ItemDAO;
import com.rpgsystem.zero.service.AutorizacaoService;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "${ALLOWED_ORIGINS:http://localhost:4000}")
public class InventarioController {

    private final ItemDAO itemDAO;
    private final AutorizacaoService autorizacaoService;

    public InventarioController(ItemDAO itemDAO, AutorizacaoService autorizacaoService) {
        this.itemDAO = itemDAO;
        this.autorizacaoService = autorizacaoService;
    }

    @GetMapping
    public List<ItemRPG> getInventario(
            @RequestParam("fichaId") int fichaId,
            Authentication authentication
    ) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        autorizacaoService.exigirAcessoFicha(usuario, fichaId);
        return itemDAO.listarInventarioPorFicha(fichaId);
    }

    @PostMapping("/adicionar")
    public ResponseEntity<String> adicionarLoot(
            @RequestBody ItemRPG novoItem,
            Authentication authentication
    ) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        if (novoItem.getFichaId() == null) {
            return ResponseEntity.badRequest().body("fichaId é obrigatório.");
        }
        autorizacaoService.exigirAcessoFicha(usuario, novoItem.getFichaId());
        itemDAO.adicionarItem(novoItem);
        return ResponseEntity.ok("Item guardado na mochila!");
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarItem(
            @PathVariable Long id,
            Authentication authentication
    ) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        autorizacaoService.exigirAcessoItem(usuario, id);
        itemDAO.deletarItem(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarItem(
            @PathVariable Long id,
            @RequestBody ItemRPG itemAtualizado,
            Authentication authentication
    ) {
        Usuario usuario = autorizacaoService.exigirUsuarioAutenticado(authentication);
        ItemRPG existente = autorizacaoService.exigirAcessoItem(usuario, id);
        itemAtualizado.setId(existente.getId());
        itemAtualizado.setFichaId(existente.getFichaId());
        itemDAO.atualizarItem(itemAtualizado);
        return ResponseEntity.ok("Item atualizado com sucesso!");
    }
}
