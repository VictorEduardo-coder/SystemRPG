package com.rpgsystem.zero.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rpgsystem.zero.model.ItemRPG;

public class ItemDAO {
    
    private Connection conn;

    // A conexão agora é recebida quando o DAO é instanciado
    public ItemDAO(Connection conn) {
        this.conn = conn;
    }

    public List<ItemRPG> listarInventario() {
        List<ItemRPG> inventario = new ArrayList<>();
        String sql = "SELECT * FROM inventario_rpg ORDER BY raridade DESC";

        // A conexão (conn) foi removida do try-with-resources para não ser fechada automaticamente
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ItemRPG item = new ItemRPG(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getString("tipo"),
                    rs.getString("raridade"),
                    rs.getInt("quantidade"),
                    rs.getInt("poder")
                );
                inventario.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventario;
    }

    public void adicionarItem(ItemRPG item) {
        String sql = "INSERT INTO inventario_rpg (nome, tipo, raridade, quantidade, poder) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getNome());
            stmt.setString(2, item.getTipo());
            stmt.setString(3, item.getRaridade());
            stmt.setInt(4, item.getQuantidade());
            stmt.setInt(5, item.getPoder());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Para excluir
public void deletarItem(Long id) {
    String sql = "DELETE FROM inventario_rpg WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, id);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

// Para atualizar
public void atualizarItem(ItemRPG item) {
    String sql = "UPDATE inventario_rpg SET nome = ?, tipo = ?, raridade = ?, quantidade = ?, poder = ? WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, item.getNome());
        stmt.setString(2, item.getTipo());
        stmt.setString(3, item.getRaridade());
        stmt.setInt(4, item.getQuantidade());
        stmt.setInt(5, item.getPoder());
        stmt.setLong(6, item.getId());
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
