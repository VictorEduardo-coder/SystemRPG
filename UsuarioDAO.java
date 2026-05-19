package com.rpgsystem.zero.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rpgsystem.zero.database.ConexaoSQLite;
import com.rpgsystem.zero.model.PerfilAcesso;
import com.rpgsystem.zero.model.Usuario;

public class UsuarioDAO {

    public static boolean cadastrarUsuario(
            String nome,
            String login,
            String senha,
            PerfilAcesso tipoConta
    ) {

        String sql = """
            INSERT INTO usuarios
            (nome, login, senha, tipo_conta)

            VALUES (?, ?, ?, ?)
        """;

        try (
            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, nome);
            stmt.setString(2, login);
            stmt.setString(3, senha);
            stmt.setString(4, tipoConta.name());

            stmt.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
    public static Usuario autenticarUsuario(
        String login,
        String senha
) {

    String sql = """
        SELECT * FROM usuarios
        WHERE login = ?
        AND senha = ?
    """;

    try (
        Connection conn = ConexaoSQLite.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setString(1, login);
        stmt.setString(2, senha);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            return new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("login"),
                    rs.getString("senha"),
                    PerfilAcesso.valueOf(rs.getString("tipo_conta"))
            );
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return null;
}

public static Usuario buscarUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = ConexaoSQLite.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("login"),
                        rs.getString("senha"),
                        PerfilAcesso.valueOf(rs.getString("tipo_conta"))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Usuario autenticar(String login, String senha) {
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";
        
        try (
                java.sql.Connection conn = com.rpgsystem.zero.database.ConexaoSQLite.conectar();
                java.sql.PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, login);
            stmt.setString(2, senha);
            
            java.sql.ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("login"),
                        rs.getString("senha"),
                        com.rpgsystem.zero.model.PerfilAcesso.valueOf(rs.getString("tipo_conta"))
                );
            }
        } catch (Exception e) {
            System.out.println("Erro ao autenticar usuário:");
            e.printStackTrace();
        }
        return null; // Retorna null se a senha estiver errada ou usuário não existir
    }

}