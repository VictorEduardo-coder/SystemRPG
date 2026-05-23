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
        return switch (acao) {

            // VISUALIZAÇÃO
            case "VER_PROPRIA_FICHA", "VER_INVENTARIO", "VER_HABILIDADES", "VER_LOJA" -> true;

            // ALTERAÇÕES PLAYER
            case "EDITAR_PROPRIA_FICHA", "USAR_ITEM", "EQUIPAR_ARMA" -> (this == PLAYER || this == MESTRE || this == ADMIN);

            // ECONOMIA
            case "GANHAR_GOLD", "GASTAR_GOLD" -> (this == PLAYER || this == MESTRE || this == ADMIN);

            // LOGS
            case "VER_LOGS" -> (this == MESTRE || this == ADMIN);
            case "LIMPAR_LOGS" -> (this == ADMIN);

            // CONTROLE DE PLAYERS
            case "VER_TODAS_FICHAS", "EDITAR_FICHAS", "GERENCIAR_LOJA", "CONTROLAR_ECONOMIA", "GERENCIAR_EVENTOS" -> (this == MESTRE || this == ADMIN);

            // ADMINISTRAÇÃO
            case "CRIAR_USUARIO", "EDITAR_USUARIO", "EXCLUIR_USUARIO" -> (this == ADMIN);

            default -> false;
        };
    }
}