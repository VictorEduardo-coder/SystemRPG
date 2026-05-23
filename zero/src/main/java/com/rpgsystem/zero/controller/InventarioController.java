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
import org.springframework.web.bind.annotation.RestController;

import com.rpgsystem.zero.database.ConexaoDB;
import com.rpgsystem.zero.model.ItemRPG;
import com.rpgsystem.zero.repository.ItemDAO;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*") // Permite que o HTML faça requisições
public class InventarioController {

    private final ItemDAO itemDAO = new ItemDAO(ConexaoDB.conectar());

    // Rota para listar (Retorna JSON para o HTML)
    @GetMapping
    public List<ItemRPG> getInventario() {
        return itemDAO.listarInventario();
    }

    // Rota para adicionar um item
    @PostMapping("/adicionar")
    public String adicionarLoot(@RequestBody ItemRPG novoItem) {
        itemDAO.adicionarItem(novoItem);
        return "Item guardado na mochila!";
    }

    // =========================================================
    // NOVAS ROTAS ADICIONADAS ABAIXO
    // =========================================================

    // Rota para deletar um item (Ex: quando jogar fora ou consumir)
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        itemDAO.deletarItem(id);
        return ResponseEntity.ok().build(); // Retorna HTTP 200 (OK) vazio para o JavaScript
    }

    // Rota para atualizar um item (Ex: mudar a quantidade de poções)
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarItem(@PathVariable Long id, @RequestBody ItemRPG itemAtualizado) {
        // Garante que o ID do objeto é o mesmo da URL
        itemAtualizado.setId(id); 
        itemDAO.atualizarItem(itemAtualizado);
        return ResponseEntity.ok("Item atualizado com sucesso!");
    }
}
