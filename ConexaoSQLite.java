package com.rpgsystem.zero.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQLite {

    private static final String URL =
            "jdbc:sqlite:rpg.db";

    public static Connection conectar() {

        try {

            return DriverManager.getConnection(URL);

        } catch (SQLException e) {

            System.out.println("Erro ao conectar:");
            e.printStackTrace();

            return null;
        }
    }
}