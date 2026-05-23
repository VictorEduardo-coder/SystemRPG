package com.rpgsystem.zero.model;

public class Habilidade {
    private final int id;
    private final String nome;
    private final String descricao;
    private final String dano;
    private final int custoMana;
    private final int classeId;
    private final String tier; // Ex: PASSIVA, ATIVO_1, ATIVO_2, ATIVO_3, SUPREMO
    
    // Campo para a interface saber se o jogador já possui esta habilidade
    private boolean desbloqueada;

    /**
     * Construtor completo
     */
    public Habilidade(int id, String nome, String descricao, String dano, int custoMana, int classeId, String tier) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dano = dano;
        this.custoMana = custoMana;
        this.classeId = classeId;
        this.tier = tier;
        this.desbloqueada = false; // Por padrão, começa bloqueada
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getDano() { return dano; }
    public int getCustoMana() { return custoMana; }
    public int getClasseId() { return classeId; }
    public String getTier() { return tier; }
    
    // Getters e Setters para o estado de desbloqueio
    public boolean isDesbloqueada() { return desbloqueada; }
    public void setDesbloqueada(boolean desbloqueada) { this.desbloqueada = desbloqueada; }
}