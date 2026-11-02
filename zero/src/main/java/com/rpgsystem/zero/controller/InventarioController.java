package com.rpgsystem.zero.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
import com.rpgsystem.zero.repository.ItemDAO;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "${ALLOWED_ORIGINS:http://localhost:3000}")
public class InventarioController {

    private final ItemDAO itemDAO;

    public InventarioController(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @GetMapping
    public List<ItemRPG> getInventario(@RequestParam("fichaId") int fichaId) {
        return itemDAO.listarInventarioPorFicha(fichaId);
    }

    @PostMapping("/adicionar")
    public ResponseEntity<String> adicionarLoot(@RequestBody ItemRPG novoItem) {
        if (novoItem.getFichaId() == null) {
            return ResponseEntity.badRequest().body("fichaId é obrigatório.");
        }
        itemDAO.adicionarItem(novoItem);
        return ResponseEntity.ok("Item guardado na mochila!");
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        itemDAO.deletarItem(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarItem(@PathVariable Long id, @RequestBody ItemRPG itemAtualizado) {
        itemAtualizado.setId(id);
        itemDAO.atualizarItem(itemAtualizado);
        return ResponseEntity.ok("Item atualizado com sucesso!");
    }
}