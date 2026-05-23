package com.rpgsystem.zero.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.rpgsystem.zero.database.ConexaoDB;
import com.rpgsystem.zero.model.Classe;

public class ClasseDAO {

    public static int salvarClasse(String nome, String bonus, String debuff, String tipo, String descricao, Integer base1, Integer base2) {
    String sqlInsert = "INSERT INTO classes (nome, bonus, debuff, eh_hibrida, descricao, classe_base_id, classe_base2_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    try (Connection conn = ConexaoDB.conectar();
         PreparedStatement stmt = conn.prepareStatement(sqlInsert)) { // Retirado o RETURN_GENERATED_KEYS
        
        stmt.setString(1, nome);
        stmt.setString(2, bonus);
        stmt.setString(3, debuff);
        
        boolean isHibrida = "HIBRIDA".equalsIgnoreCase(tipo);
        stmt.setBoolean(4, isHibrida);
        stmt.setString(5, descricao);
        
        if (isHibrida && base1 != null) stmt.setInt(6, base1);
        else stmt.setNull(6, java.sql.Types.INTEGER);
        
        if (isHibrida && base2 != null) stmt.setInt(7, base2);
        else stmt.setNull(7, java.sql.Types.INTEGER);
        
        // Executa a inserção
        stmt.executeUpdate();
        
        // Pega o ID usando o comando nativo do SQLite
        try (Statement stmtId = conn.createStatement();
             ResultSet rsId = stmtId.executeQuery("SELECT last_insert_rowid()")) {
            if (rsId.next()) {
                return rsId.getInt(1); 
            }
        }
        
    } catch (Exception e) {
        System.err.println("Erro ao salvar classe: " + e.getMessage());
    }
    
    return 1; // Fallback
}

    public static Classe buscarClassePorId(int id) {
        String sql = "SELECT * FROM classes WHERE id = ?";
        
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Classe(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("bonus"),
                        rs.getString("debuff"),
                        rs.getString("descricao"),
                        rs.getBoolean("eh_hibrida"),
                        rs.getInt("classe_base_id"),
                        rs.getInt("classe_base2_id") // <-- ADICIONADO AQUI
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar classe: " + e.getMessage());
        }
        return null;
    }


    public static List<Classe> listarTodasAsClasses() {
        List<Classe> lista = new ArrayList<>();
        String sql = "SELECT * FROM classes";
        
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                lista.add(new Classe(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("bonus"),
                    rs.getString("debuff"),
                    rs.getString("descricao"),
                    rs.getBoolean("eh_hibrida"),
                    rs.getInt("classe_base_id"),
                    rs.getInt("classe_base2_id") // <-- ADICIONADO AQUI
                ));
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar classes: " + e.getMessage());
        }
        return lista;
    }
    
}