package com.rpgsystem.zero.model;

public class Habilidade {
    private int id;
    private String nome;
    private String descricao;
    private String dano;
    private int custoMana;
    private int classeId;
    
    // Campo extra para a tela saber se o jogador já possui esta habilidade
    private boolean desbloqueada;

    public Habilidade(int id, String nome, String descricao, String dano, int custoMana, int classeId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dano = dano;
        this.custoMana = custoMana;
        this.classeId = classeId;
        this.desbloqueada = false; // Por padrão, não possui
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getDano() { return dano; }
    public int getCustoMana() { return custoMana; }
    public int getClasseId() { return classeId; }
    
    public boolean isDesbloqueada() { return desbloqueada; }
    public void setDesbloqueada(boolean desbloqueada) { this.desbloqueada = desbloqueada; }
}