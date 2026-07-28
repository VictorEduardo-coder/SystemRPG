package com.rpgsystem.zero.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rpgsystem.zero.database.ConexaoDB;
import com.rpgsystem.zero.model.PerfilAcesso;
import com.rpgsystem.zero.model.Usuario;

public class UsuarioDAO {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private static PerfilAcesso converterParaPerfil(String tipoConta) {
        try {
            return PerfilAcesso.valueOf(tipoConta.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return PerfilAcesso.PLAYER;
        }
    }

    public static boolean cadastrarUsuario(String nome, String login, String senha, PerfilAcesso tipoConta) {
        String senhaHash = encoder.encode(senha);
        String sql = "INSERT INTO usuarios (nome, login, senha, tipo_conta) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, login);
            stmt.setString(3, senhaHash);
            stmt.setString(4, tipoConta.name());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
    }

    public static Usuario autenticarUsuario(String login, String senha) {
        String sql = "SELECT * FROM usuarios WHERE login = ?";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String senhaHash = rs.getString("senha");
                    if (encoder.matches(senha, senhaHash)) {
                        return new Usuario(
                                rs.getInt("id"),
                                rs.getString("nome"),
                                rs.getString("login"),
                                senhaHash,
                                converterParaPerfil(rs.getString("tipo_conta"))
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao autenticar usuário: " + e.getMessage());
        }
        return null;
    }

    public static Usuario buscarUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("login"),
                            rs.getString("senha"),
                            converterParaPerfil(rs.getString("tipo_conta"))
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
        }
        return null;
    }

    public static Usuario buscarUsuarioPorLogin(String login) {
        String sql = "SELECT * FROM usuarios WHERE login = ?";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("login"),
                            rs.getString("senha"),
                            converterParaPerfil(rs.getString("tipo_conta"))
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por login: " + e.getMessage());
        }
        return null;
    }

    public static Usuario buscarPrimeiroUsuario() {
        String sql = "SELECT * FROM usuarios LIMIT 1";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("login"),
                        rs.getString("senha"),
                        converterParaPerfil(rs.getString("tipo_conta"))
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar primeiro usuário: " + e.getMessage());
        }
        return null;
    }
}