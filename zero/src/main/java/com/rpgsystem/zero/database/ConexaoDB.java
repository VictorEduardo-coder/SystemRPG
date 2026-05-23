package com.rpgsystem.zero.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    // Altere "root" e "suasenha" para os dados reais do seu MySQL
    private static final String URL = "jdbc:mysql://avnadmin:AVNS_lhER5H14eXVyEHztoOY@mysql-2d6dae3a-rpg-1e8b.f.aivencloud.com:12321/defaultdb?ssl-mode=REQUIRED";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_lhER5H14eXVyEHztoOY";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar no MySQL: " + e.getMessage());
            return null;
        }
    }
}