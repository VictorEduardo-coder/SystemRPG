package com.rpgsystem.zero.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rpgsystem.zero.database.ConexaoDB;
import com.rpgsystem.zero.model.Regra;

public class RegraDAO {

    // ==================================================
    // BUSCAR TODAS AS REGRAS
    // ==================================================
    public static List<Regra> buscarTodasAsRegras() {
        
        List<Regra> regras = new ArrayList<>();
        
        // Ordena por categoria para ficar agrupado certinho na tela
        String sql = "SELECT * FROM manual_regras ORDER BY categoria, titulo";

        try (
                Connection conn = ConexaoDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Regra regra = new Regra(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        rs.getString("categoria")
                );
                regras.add(regra);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar regras do manual: " + e.getMessage());
        }

        return regras;
    }

    // ==================================================
    // CRIAR NOVA REGRA
    // ==================================================
    public static boolean criarRegra(String titulo, String descricao, String categoria) {
        
        String sql = "INSERT INTO manual_regras (titulo, descricao, categoria) VALUES (?, ?, ?)";

        try (
                Connection conn = ConexaoDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, titulo);
            stmt.setString(2, descricao);
            stmt.setString(3, categoria);

            int linhasAfetadas = stmt.executeUpdate();
            
            // Retorna true se conseguiu salvar no banco
            return linhasAfetadas > 0;

        } catch (Exception e) {
            System.err.println("Erro ao criar nova regra no manual: " + e.getMessage());
            return false;
        }
    }
}