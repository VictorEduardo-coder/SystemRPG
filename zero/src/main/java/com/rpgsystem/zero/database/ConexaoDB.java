package com.rpgsystem.zero.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

public class ConexaoDB {

    private static volatile DataSource springDataSource;

    public static void configurarDataSource(DataSource dataSource) {
        springDataSource = dataSource;
    }

    public static Connection conectar() {
        try {
            if (springDataSource != null) {
                return springDataSource.getConnection();
            }

            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USERNAME");
            String password = System.getenv("DB_PASSWORD");

            if (url == null || url.isBlank() || user == null || user.isBlank()) {
                throw new IllegalStateException(
                        "DataSource Spring não inicializado e variáveis DB_URL/DB_USERNAME não definidas.");
            }
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar no banco de dados: " + e.getMessage());
            return null;
        }
    }

    @Component
    public static class ConexaoDBInitializer {
        public ConexaoDBInitializer(DataSource dataSource) {
            ConexaoDB.configurarDataSource(dataSource);
        }
    }
}
