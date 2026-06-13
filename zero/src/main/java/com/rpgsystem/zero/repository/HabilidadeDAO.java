package com.rpgsystem.zero.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rpgsystem.zero.database.ConexaoDB;
import com.rpgsystem.zero.model.Classe;
import com.rpgsystem.zero.model.Habilidade;

public class HabilidadeDAO {

    public static List<Habilidade> buscarHabilidadesDaClasseParaFicha(int classeId, int fichaId) {
        Classe classe = ClasseDAO.buscarClassePorId(classeId);
        
        // ==================================================
        // 1. REGRA ESPECIAL: MÍMICO E FACTOTUM
        // ==================================================
        boolean temAcessoTotal = false;
        
        if (classe != null) {
            String nome = classe.getNome().toUpperCase();
            
            // Verifica a classe principal
            if (nome.contains("MÍMICO") || nome.contains("MIMICO") || nome.contains("FACTOTUM")) {
                temAcessoTotal = true;
            }
            
            // Se for híbrida, verifica se alguma das bases é Mímico ou Factotum
            if (classe.isEhHibrida()) {
                Classe base1 = ClasseDAO.buscarClassePorId(classe.getClasseBaseId());
                Classe base2 = ClasseDAO.buscarClassePorId(classe.getClasseBase2Id());
                
                if (base1 != null) {
                    String nomeBase1 = base1.getNome().toUpperCase();
                    if (nomeBase1.contains("MÍMICO") || nomeBase1.contains("MIMICO") || nomeBase1.contains("FACTOTUM")) {
                        temAcessoTotal = true;
                    }
                }
                
                if (base2 != null) {
                    String nomeBase2 = base2.getNome().toUpperCase();
                    if (nomeBase2.contains("MÍMICO") || nomeBase2.contains("MIMICO") || nomeBase2.contains("FACTOTUM")) {
                        temAcessoTotal = true;
                    }
                }
            }
        }
        
        // Se a regra especial foi ativada, retorna o Compêndio inteiro!
        if (temAcessoTotal) {
            return listarTodasAsHabilidades(fichaId);
        }

        // ==================================================
        // 2. LÓGICA NORMAL PARA AS OUTRAS CLASSES
        // ==================================================
        List<Habilidade> habilidades = new ArrayList<>();
        
        String filtroClasse = (classe != null && classe.isEhHibrida()) 
                        ? " h.classe_id = ? OR h.classe_id = ? OR h.classe_id = ?" 
                        : " h.classe_id = ?";

        String sql = """
            SELECT h.*, 
                   CASE WHEN fh.habilidade_id IS NOT NULL THEN 1 ELSE 0 END as possui
            FROM habilidades h
            LEFT JOIN ficha_habilidades fh ON h.id = fh.habilidade_id AND fh.ficha_id = ?
            WHERE 
        """ + filtroClasse;

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, fichaId);
            stmt.setInt(2, classeId);
            
            if (classe != null && classe.isEhHibrida()) {
                stmt.setInt(3, classe.getClasseBaseId());
                stmt.setInt(4, classe.getClasseBase2Id()); 
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Habilidade hab = new Habilidade(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getString("dano"),
                            rs.getInt("custo_mana"),
                            rs.getInt("classe_id"),
                            rs.getString("tier") 
                    );
                    
                    hab.setDesbloqueada(rs.getInt("possui") == 1);
                    habilidades.add(hab);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar habilidades: " + e.getMessage());
        }

        return habilidades;
    }

    public static List<Habilidade> listarTodasAsHabilidades(int fichaId) {
        List<Habilidade> habilidades = new ArrayList<>();
        
        String sql = """
            SELECT h.*, 
                   CASE WHEN fh.habilidade_id IS NOT NULL THEN 1 ELSE 0 END as possui
            FROM habilidades h
            LEFT JOIN ficha_habilidades fh ON h.id = fh.habilidade_id AND fh.ficha_id = ?
        """;

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, fichaId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Habilidade hab = new Habilidade(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getString("dano"),
                            rs.getInt("custo_mana"),
                            rs.getInt("classe_id"),
                            rs.getString("tier") 
                    );
                    
                    hab.setDesbloqueada(rs.getInt("possui") == 1);
                    habilidades.add(hab);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar todas as habilidades: " + e.getMessage());
        }

        return habilidades;
    }

    public static void salvarHabilidade(String nome, String descricao, String tier, String dano, int custoMana, int classeId) {
        String sql = "INSERT INTO habilidades (nome, descricao, tier, dano, custo_mana, classe_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setString(3, tier);
            stmt.setInt(4, classeId);
            
            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Erro ao salvar habilidade: " + e.getMessage());
        }
    }
}