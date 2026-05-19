package com.rpgsystem.zero.repository;

import com.rpgsystem.zero.database.ConexaoSQLite;
import com.rpgsystem.zero.model.Habilidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HabilidadeDAO {

    // Busca habilidades da classe e marca quais o jogador (fichaId) já tem
    public static List<Habilidade> buscarHabilidadesDaClasseParaFicha(int classeId, int fichaId) {
        List<Habilidade> habilidades = new ArrayList<>();

        // Fazemos um LEFT JOIN para saber se a habilidade existe na tabela ficha_habilidades para este jogador
        String sql = """
            SELECT h.*, 
                   CASE WHEN fh.habilidade_id IS NOT NULL THEN 1 ELSE 0 END as possui
            FROM habilidades h
            LEFT JOIN ficha_habilidades fh ON h.id = fh.habilidade_id AND fh.ficha_id = ?
            WHERE h.classe_id = ?
        """;

        try (
                Connection conn = ConexaoSQLite.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, fichaId);
            stmt.setInt(2, classeId);
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Habilidade hab = new Habilidade(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("dano"),
                        rs.getInt("custo_mana"),
                        rs.getInt("classe_id")
                );
                // Se a query retornou 1 na coluna 'possui', o jogador tem a habilidade
                hab.setDesbloqueada(rs.getInt("possui") == 1);
                
                habilidades.add(hab);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar habilidades:");
            e.printStackTrace();
        }

        return habilidades;
    }
}