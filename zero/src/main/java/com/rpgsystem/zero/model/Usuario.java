package com.rpgsystem.zero.model;

public class Usuario {

    // ==========================================
    // ATRIBUTOS
    // ==========================================

    private final int id;

    private String login;
    private String senha;

    private String nome;

    private PerfilAcesso perfil;

    // ==========================================
    // CONSTRUTOR
    // ==========================================

    public Usuario(
            int id,
            String nome,
            String login,
            String senha,
            PerfilAcesso perfil
    ) {

        this.id = id;
        this.nome = nome;

        this.login = login;
        this.senha = senha;

        this.perfil = perfil;
    }

    // ==========================================
    // AUTENTICAÇÃO
    // ==========================================

    public boolean autenticar(
            String loginDigitado,
            String senhaDigitada
    ) {

        return this.login.equals(loginDigitado)
                && this.senha.equals(senhaDigitada);
    }

    // ==========================================
    // PERMISSÕES
    // ==========================================

    public boolean podeExecutar(String acao) {

        if (perfil == null) {

            return false;
        }

        return perfil.temPermissao(acao);
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public int getId() {

        return id;
    }

    public String getNome() {

        return nome;
    }

    public String getLogin() {

        return login;
    }

    public String getSenha() {

        return senha;
    }

    public PerfilAcesso getPerfil() {

        return perfil;
    }

    // ==========================================
    // SETTERS
    // ==========================================

    public void setNome(String nome) {

        this.nome = nome;
    }

    public void setLogin(String login) {

        this.login = login;
    }

    public void setSenha(String senha) {

        this.senha = senha;
    }

    public void setPerfil(PerfilAcesso perfil) {

        this.perfil = perfil;
    }

    // ==========================================
    // EXIBIÇÃO
    // ==========================================

    @Override
    public String toString() {

        return """
                ===== USUÁRIO =====
                ID: %d
                Nome: %s
                Login: %s
                Perfil: %s"""
                .formatted(id, nome, login, perfil.getDescricao());
    }
}