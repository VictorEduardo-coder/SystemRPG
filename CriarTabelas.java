package com.rpgsystem.zero.database;

import java.sql.Connection;
import java.sql.Statement;

public class CriarTabelas {

    public static void criar() {

        String sqlUsuarios = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                login TEXT UNIQUE NOT NULL,
                senha TEXT NOT NULL,
                tipo_conta TEXT NOT NULL
            );
        """;

        String sqlFichas = """
            CREATE TABLE IF NOT EXISTS fichas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario_id INTEGER NOT NULL,
                tipo_ficha TEXT DEFAULT 'PLAYER',
                nome TEXT NOT NULL,
                nivel INTEGER DEFAULT 1,
                xp_atual INTEGER DEFAULT 0,
                xp_proximo INTEGER DEFAULT 10,
                idade INTEGER DEFAULT 0,
                classe TEXT DEFAULT 'Sem Classe',
                titulo TEXT DEFAULT '',
                dinheiro_real REAL DEFAULT 0,
                gold INTEGER DEFAULT 0,
                tr INTEGER DEFAULT 0,
                falha_nome TEXT DEFAULT '',
                falha_efeito TEXT DEFAULT '',
                forca INTEGER DEFAULT 0,
                vida INTEGER DEFAULT 0,
                agilidade INTEGER DEFAULT 0,
                mana INTEGER DEFAULT 0,
                carisma INTEGER DEFAULT 0,
                sorte INTEGER DEFAULT 0,
                seduzir INTEGER DEFAULT 0,
                intimidar INTEGER DEFAULT 0,
                estudar INTEGER DEFAULT 0,
                investigar INTEGER DEFAULT 0,
                politica INTEGER DEFAULT 0,
                religiao INTEGER DEFAULT 0,
                artes INTEGER DEFAULT 0,
                furtividade INTEGER DEFAULT 0,
                construir INTEGER DEFAULT 0,
                saldo_atual REAL DEFAULT 0,
                custo_vida REAL DEFAULT 0,
                dias_restantes INTEGER DEFAULT 0,
                nutricao TEXT DEFAULT 'BASICO',
                conexao TEXT DEFAULT 'NORMAL',
                criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY(usuario_id) REFERENCES usuarios(id)
            );
        """;

        String sqlRegras = """
            CREATE TABLE IF NOT EXISTS manual_regras (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                descricao TEXT NOT NULL,
                categoria TEXT NOT NULL
            );
        """;

        String sqlClasses = """
            CREATE TABLE IF NOT EXISTS classes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                descricao TEXT NOT NULL
            );
        """;

        String sqlHabilidades = """
            CREATE TABLE IF NOT EXISTS habilidades (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                descricao TEXT NOT NULL,
                dano TEXT DEFAULT '0',
                custo_mana INTEGER DEFAULT 0,
                classe_id INTEGER NOT NULL,
                FOREIGN KEY(classe_id) REFERENCES classes(id)
            );
        """;

        String sqlFichaHabilidades = """
            CREATE TABLE IF NOT EXISTS ficha_habilidades (
                ficha_id INTEGER NOT NULL,
                habilidade_id INTEGER NOT NULL,
                PRIMARY KEY (ficha_id, habilidade_id),
                FOREIGN KEY(ficha_id) REFERENCES fichas(id),
                FOREIGN KEY(habilidade_id) REFERENCES habilidades(id)
            );
        """;

        // Inserts de Teste
        String sqlInsertRegraTeste = "INSERT INTO manual_regras (titulo, descricao, categoria) SELECT 'Combate Básico', 'Rolagem de 1d20', 'Combate' WHERE NOT EXISTS (SELECT 1 FROM manual_regras WHERE id = 1);";
        String sqlInsertClassesTeste = "INSERT INTO classes (id, nome, descricao) SELECT 1, 'Guerreiro', 'Focado em combate.' WHERE NOT EXISTS (SELECT 1 FROM classes WHERE id = 1);";
        String sqlInsertHabilidadesTeste = "INSERT INTO habilidades (id, nome, descricao, dano, custo_mana, classe_id) SELECT 1, 'Golpe Forte', 'Ataque pesado', '1d8 + Força', 5, 1 WHERE NOT EXISTS (SELECT 1 FROM habilidades WHERE id = 1);";

        // ==================================================
        // EXECUÇÃO
        // ==================================================
        try (Connection conn = ConexaoSQLite.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlUsuarios);
            stmt.execute(sqlFichas);
            stmt.execute(sqlRegras);
            stmt.execute(sqlClasses);
            stmt.execute(sqlHabilidades);
            stmt.execute(sqlFichaHabilidades);
            
            stmt.execute(sqlInsertRegraTeste);
            stmt.execute(sqlInsertClassesTeste);
            stmt.execute(sqlInsertHabilidadesTeste);

            System.out.println("Tabelas criadas com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao criar tabelas:");
            e.printStackTrace();
        }
    }
}