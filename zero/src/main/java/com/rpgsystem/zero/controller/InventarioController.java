package com.rpgsystem.zero.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    // CORRIGIDO: Agora passa a conexão do seu conector existente para o construtor do DAO
    private final ItemDAO itemDAO = new ItemDAO(ConexaoDB.conectar());

    // Rota para listar (Retorna JSON para o HTML)
    @GetMapping
    public List<ItemRPG> getInventario() {
        return itemDAO.listarInventario();
    }

    // Rota para adicionar um item (Ex: quando um monstro morre e dropa loot)
    @PostMapping("/adicionar")
    public String adicionarLoot(@RequestBody ItemRPG novoItem) {
        itemDAO.adicionarItem(novoItem);
        return "Item guardado na mochila!";
    }
}