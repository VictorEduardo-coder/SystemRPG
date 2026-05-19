package com.rpgsystem.zero.model;

public enum PerfilAcesso {

    PLAYER(0, "Jogador"),
    MESTRE(1, "Mestre da Campanha"),
    ADMIN(2, "Administrador do Sistema");

    private final int nivel;
    private final String descricao;

    PerfilAcesso(int nivel, String descricao) {

        this.nivel = nivel;
        this.descricao = descricao;
    }

    // ==================================================
    // GETTERS
    // ==================================================

    public int getNivel() {

        return nivel;
    }

    public String getDescricao() {

        return descricao;
    }

    // ==================================================
    // BUSCA POR NÍVEL
    // ==================================================

    public static PerfilAcesso porNivel(int nivel) {

        for (PerfilAcesso perfil : PerfilAcesso.values()) {

            if (perfil.getNivel() == nivel) {

                return perfil;
            }
        }

        throw new IllegalArgumentException(
                "Nível inválido: " + nivel
        );
    }

    // ==================================================
    // SISTEMA DE PERMISSÕES
    // ==================================================

    public boolean temPermissao(String acao) {

        switch (acao) {

            // ==========================================
            // VISUALIZAÇÃO
            // ==========================================

            case "VER_PROPRIA_FICHA":
            case "VER_INVENTARIO":
            case "VER_HABILIDADES":
            case "VER_LOJA":
                return true;

            // ==========================================
            // ALTERAÇÕES PLAYER
            // ==========================================

            case "EDITAR_PROPRIA_FICHA":
            case "USAR_ITEM":
            case "EQUIPAR_ARMA":
                return this == PLAYER
                        || this == MESTRE
                        || this == ADMIN;

            // ==========================================
            // ECONOMIA
            // ==========================================

            case "GANHAR_GOLD":
            case "GASTAR_GOLD":
                return this == PLAYER
                        || this == MESTRE
                        || this == ADMIN;

            // ==========================================
            // LOGS
            // ==========================================

            case "VER_LOGS":
                return this == MESTRE
                        || this == ADMIN;

            case "LIMPAR_LOGS":
                return this == ADMIN;

            // ==========================================
            // CONTROLE DE PLAYERS
            // ==========================================

            case "VER_TODAS_FICHAS":
            case "EDITAR_FICHAS":
            case "GERENCIAR_LOJA":
            case "CONTROLAR_ECONOMIA":
            case "GERENCIAR_EVENTOS":
                return this == MESTRE
                        || this == ADMIN;

            // ==========================================
            // ADMINISTRAÇÃO
            // ==========================================

            case "CRIAR_USUARIO":
            case "EDITAR_USUARIO":
            case "EXCLUIR_USUARIO":
                return this == ADMIN;

            default:
                return false;
        }
    }
}