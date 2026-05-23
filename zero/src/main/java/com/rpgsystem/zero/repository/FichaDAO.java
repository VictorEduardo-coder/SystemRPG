package com.rpgsystem.zero.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpgsystem.zero.database.ConexaoDB;
import com.rpgsystem.zero.model.Ficha;
import com.rpgsystem.zero.model.FichaBoss;
import com.rpgsystem.zero.model.FichaMonstro;
import com.rpgsystem.zero.model.FichaNPC;
import com.rpgsystem.zero.model.FichaPlayer;

public class FichaDAO {

    private static final Logger logger = LoggerFactory.getLogger(FichaDAO.class);

    // ==================================================
    // MAPEAMENTO DE FICHA
    // ==================================================

    private static Ficha mapearFicha(ResultSet rs) throws SQLException {

        String tipo = rs.getString("tipo_ficha");
        if (tipo == null || tipo.isBlank()) {
            tipo = "PLAYER";
        }

        Ficha ficha;

        switch (tipo.toUpperCase()) {

            case "NPC" ->
                    ficha = new FichaNPC(
                            rs.getInt("usuario_id"),
                            rs.getString("nome")
                    );

            case "MONSTRO" ->
                    ficha = new FichaMonstro(
                            rs.getInt("usuario_id"),
                            rs.getString("nome"),
                            rs.getString("classe"),
                            rs.getInt("nivel")
                    );

            case "BOSS" ->
                    ficha = new FichaBoss(
                            rs.getInt("usuario_id"),
                            rs.getString("nome"),
                            rs.getString("classe"),
                            rs.getInt("nivel")
                    );

            default ->
                    ficha = new FichaPlayer(
                            rs.getInt("usuario_id"),
                            rs.getString("nome")
                    );
        }

        // 1. IDENTIFICAÇÃO
        ficha.setId(rs.getInt("id"));
        ficha.setNivel(rs.getInt("nivel"));
        ficha.setXpAtual(rs.getInt("xp_atual"));
        ficha.setXpProximo(rs.getInt("xp_proximo"));
        ficha.setIdade(rs.getInt("idade")); // <-- Faltava
        ficha.setClasse(rs.getString("classe"));
        ficha.setTitulo(rs.getString("titulo"));

        // 2. ECONOMIA <-- Faltava tudo isso
        ficha.setDinheiroReal(rs.getDouble("dinheiro_real"));
        ficha.setGold(rs.getInt("gold"));
        ficha.setTr(rs.getInt("tr"));

        // 3. FALHAS <-- Faltava tudo isso
        ficha.setFalhaNome(rs.getString("falha_nome"));
        ficha.setFalhaEfeito(rs.getString("falha_efeito"));

        // 4. STATUS BASE
        ficha.setForca(rs.getInt("forca"));
        ficha.setVida(rs.getInt("vida"));
        ficha.setAgilidade(rs.getInt("agilidade"));
        ficha.setMana(rs.getInt("mana"));
        ficha.setCarisma(rs.getInt("carisma"));
        ficha.setSorte(rs.getInt("sorte"));

        // 5. PERÍCIAS <-- Faltava tudo isso
        ficha.setSeduzir(rs.getInt("seduzir"));
        ficha.setIntimidar(rs.getInt("intimidar"));
        ficha.setEstudar(rs.getInt("estudar"));
        ficha.setInvestigar(rs.getInt("investigar"));
        ficha.setPolitica(rs.getInt("politica"));
        ficha.setReligiao(rs.getInt("religiao"));
        ficha.setArtes(rs.getInt("artes"));
        ficha.setFurtividade(rs.getInt("furtividade"));
        ficha.setConstruir(rs.getInt("construir"));

        // 6. REALIDADE <-- Faltava tudo isso
        ficha.setSaldoAtual(rs.getDouble("saldo_atual"));
        ficha.setCustoVida(rs.getDouble("custo_vida"));
        ficha.setDiasRestantes(rs.getInt("dias_restantes"));
        ficha.setNutricao(rs.getString("nutricao"));
        ficha.setConexao(rs.getString("conexao"));

        return ficha;
    }
    // ==================================================
    // CRIAR FICHA INICIAL
    // ==================================================

 public static boolean criarFicha(int usuarioId, String nome, String tipoFicha) {

        // Adicionamos a coluna tipo_ficha no INSERT
        String sql = """
            INSERT INTO fichas (
                usuario_id,
                nome,
                tipo_ficha, 
                nivel,
                xp_atual,
                xp_proximo,
                classe,
                titulo,
                nutricao,
                conexao
            )
            VALUES (?, ?, ?, 1, 0, 10, 'Sem Classe', '', 'BASICO', 'NORMAL')
        """;

        try (
                Connection conn = ConexaoDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, usuarioId);
            stmt.setString(2, nome);
            
            // Garante que o tipo seja guardado em maiúsculas (ex: "BOSS")
            if (tipoFicha == null || tipoFicha.isBlank()) {
                tipoFicha = "PLAYER";
            }
            stmt.setString(3, tipoFicha.toUpperCase()); 

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            logger.error("Erro ao criar ficha do tipo: " + tipoFicha, e);
            return false;
        }
    }

    // ==================================================
    // BUSCAR FICHA POR USUÁRIO
    // ==================================================

    public static Ficha buscarFichaPorUsuario(int usuarioId) {

        String sql = "SELECT * FROM fichas WHERE usuario_id = ?";

        try (
                Connection conn = ConexaoDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapearFicha(rs);
                }
            }

        } catch (SQLException e) {

            logger.error("Erro ao buscar ficha por usuário", e);
        }

        return null;
    }

    // ==================================================
    // BUSCAR FICHA POR ID
    // ==================================================

    public static Ficha buscarFichaPorId(int fichaId) {

        String sql = "SELECT * FROM fichas WHERE id = ?";

        try (
                Connection conn = ConexaoDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, fichaId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapearFicha(rs);
                }
            }

        } catch (SQLException e) {

            logger.error("Erro ao buscar ficha por ID", e);
        }

        return null;
    }

    // ==================================================
    // BUSCAR TODAS AS FICHAS
    // ==================================================

    public static List<Ficha> buscarTodasAsFichas() {

        List<Ficha> listaFichas = new ArrayList<>();

        String sql = "SELECT * FROM fichas";

        try (
                Connection conn = ConexaoDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Ficha ficha = mapearFicha(rs);
                listaFichas.add(ficha);
            }

        } catch (SQLException e) {

            logger.error("Erro ao buscar todas as fichas", e);
        }

        return listaFichas;
    }

    // ==================================================
    // ATUALIZAR STATUS
    // ==================================================

    public static boolean atualizarStatus(Ficha ficha) {

        String sql = """
            UPDATE fichas
            SET
                forca = ?,
                vida = ?,
                agilidade = ?,
                mana = ?,
                carisma = ?,
                sorte = ?
            WHERE id = ?
        """;

        try (
                Connection conn = ConexaoDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, ficha.getForca());
            stmt.setInt(2, ficha.getVida());
            stmt.setInt(3, ficha.getAgilidade());
            stmt.setInt(4, ficha.getMana());
            stmt.setInt(5, ficha.getCarisma());
            stmt.setInt(6, ficha.getSorte());
            stmt.setInt(7, ficha.getId());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {

            logger.error("Erro ao atualizar status", e);
            return false;
        }
    }

    // ==================================================
    // ATUALIZAR FICHA COMPLETA
    // ==================================================

    public static boolean atualizarFicha(Ficha ficha) {

        String sql = """
            UPDATE fichas
            SET
                nome = ?,
                nivel = ?,
                xp_atual = ?,
                xp_proximo = ?,
                idade = ?,
                classe = ?,
                titulo = ?,
                dinheiro_real = ?,
                gold = ?,
                tr = ?,
                falha_nome = ?,
                falha_efeito = ?,

                -- STATUS
                forca = ?,
                vida = ?,
                agilidade = ?,
                mana = ?,
                carisma = ?,
                sorte = ?,

                -- PERÍCIAS
                seduzir = ?,
                intimidar = ?,
                estudar = ?,
                investigar = ?,
                politica = ?,
                religiao = ?,
                artes = ?,
                furtividade = ?,
                construir = ?,

                -- REALIDADE
                saldo_atual = ?,
                custo_vida = ?,
                dias_restantes = ?,
                nutricao = ?,
                conexao = ?

            WHERE id = ?
        """;

        try (
                Connection conn = ConexaoDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            // IDENTIFICAÇÃO
            stmt.setString(1, ficha.getNome());
            stmt.setInt(2, ficha.getNivel());
            stmt.setInt(3, ficha.getXpAtual());
            stmt.setInt(4, ficha.getXpProximo());
            stmt.setInt(5, ficha.getIdade());
            stmt.setString(6, ficha.getClasse());
            stmt.setString(7, ficha.getTitulo());

            // ECONOMIA
            stmt.setDouble(8, ficha.getDinheiroReal());
            stmt.setInt(9, ficha.getGold());
            stmt.setInt(10, ficha.getTr());

            // FALHAS
            stmt.setString(11, ficha.getFalhaNome());
            stmt.setString(12, ficha.getFalhaEfeito());

            // STATUS
            stmt.setInt(13, ficha.getForca());
            stmt.setInt(14, ficha.getVida());
            stmt.setInt(15, ficha.getAgilidade());
            stmt.setInt(16, ficha.getMana());
            stmt.setInt(17, ficha.getCarisma());
            stmt.setInt(18, ficha.getSorte());

            // PERÍCIAS
            stmt.setInt(19, ficha.getSeduzir());
            stmt.setInt(20, ficha.getIntimidar());
            stmt.setInt(21, ficha.getEstudar());
            stmt.setInt(22, ficha.getInvestigar());
            stmt.setInt(23, ficha.getPolitica());
            stmt.setInt(24, ficha.getReligiao());
            stmt.setInt(25, ficha.getArtes());
            stmt.setInt(26, ficha.getFurtividade());
            stmt.setInt(27, ficha.getConstruir());

            // REALIDADE
            stmt.setDouble(28, ficha.getSaldoAtual());
            stmt.setDouble(29, ficha.getCustoVida());
            stmt.setInt(30, ficha.getDiasRestantes());
            stmt.setString(31, ficha.getNutricao());
            stmt.setString(32, ficha.getConexao());

            // ID
            stmt.setInt(33, ficha.getId());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {

            logger.error("Erro ao atualizar ficha", e);
            return false;
        }
    }

    // ==================================================
    // DELETAR FICHA
    // ==================================================

    public static boolean deletarFicha(int fichaId, int usuarioId) {

        String sql = "DELETE FROM fichas WHERE id = ? AND usuario_id = ?";

        try (
                Connection conn = ConexaoDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, fichaId);
            stmt.setInt(2, usuarioId);

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException e) {

            logger.error("Erro ao deletar ficha", e);
            return false;
        }
    }
}