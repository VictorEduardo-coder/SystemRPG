package com.rpgsystem.zero.repository;

import com.rpgsystem.zero.database.ConexaoSQLite;
import com.rpgsystem.zero.model.Regra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RegraDAO {

    public static List<Regra> buscarTodasAsRegras() {
        
        List<Regra> regras = new ArrayList<>();
        
        // Ordena por categoria para ficar agrupado certinho na tela
        String sql = "SELECT * FROM manual_regras ORDER BY categoria, titulo";

        try (
                Connection conn = ConexaoSQLite.conectar();
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
            System.out.println("Erro ao buscar regras do manual:");
            e.printStackTrace();
        }

        return regras;
    }
}