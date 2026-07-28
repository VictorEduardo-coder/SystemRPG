package com.rpgsystem.zero.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USERNAME");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    static {
        if (URL == null || URL.isBlank()) {
            throw new IllegalStateException("A variável de ambiente DB_URL não está definida.");
        }
        if (USER == null || USER.isBlank()) {
            throw new IllegalStateException("A variável de ambiente DB_USERNAME não está definida.");
        }
    }

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar no banco de dados: " + e.getMessage());
            return null;
        }
    }
}