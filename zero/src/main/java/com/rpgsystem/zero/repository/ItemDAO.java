package com.rpgsystem.zero.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rpgsystem.zero.database.ConexaoDB;
import com.rpgsystem.zero.model.ItemRPG;

@Repository
public class ItemDAO {

    public List<ItemRPG> listarInventarioPorFicha(int fichaId) {
        List<ItemRPG> inventario = new ArrayList<>();
        String sql = "SELECT * FROM inventario_rpg WHERE ficha_id = ? ORDER BY raridade DESC";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, fichaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemRPG item = new ItemRPG(
                        rs.getLong("id"),
                        rs.getInt("ficha_id"),
                        rs.getString("nome"),
                        rs.getString("tipo"),
                        rs.getString("raridade"),
                        rs.getInt("quantidade"),
                        rs.getInt("poder")
                    );
                    inventario.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventario;
    }

    public void adicionarItem(ItemRPG item) {
        String sql = "INSERT INTO inventario_rpg (ficha_id, nome, tipo, raridade, quantidade, poder) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getFichaId());
            stmt.setString(2, item.getNome());
            stmt.setString(3, item.getTipo());
            stmt.setString(4, item.getRaridade());
            stmt.setInt(5, item.getQuantidade());
            stmt.setInt(6, item.getPoder());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarItem(Long id) {
        String sql = "DELETE FROM inventario_rpg WHERE id = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarItem(ItemRPG item) {
        String sql = "UPDATE inventario_rpg SET nome = ?, tipo = ?, raridade = ?, quantidade = ?, poder = ? WHERE id = ?";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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