package com.rpgsystem.zero.model;

public class Classe {
    private final int id;
    private final String nome;
    private final String bonus;
    private final String debuff;
    private final String descricao;
    private final boolean ehHibrida;
    private final int classeBaseId;
    private final int classeBase2Id; // <-- NOVO CAMPO AQUI

    public Classe(int id, String nome, String bonus, String debuff, String descricao, boolean ehHibrida, int classeBaseId, int classeBase2Id) {
        this.id = id;
        this.nome = nome;
        this.bonus = bonus;
        this.debuff = debuff;
        this.descricao = descricao;
        this.ehHibrida = ehHibrida;
        this.classeBaseId = classeBaseId;
        this.classeBase2Id = classeBase2Id; // <-- ATRIBUIÇÃO AQUI
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getBonus() { return bonus; }
    public String getDebuff() { return debuff; }
    public String getDescricao() { return descricao; }
    public boolean isEhHibrida() { return ehHibrida; }
    public int getClasseBaseId() { return classeBaseId; }
    public int getClasseBase2Id() { return classeBase2Id; } // <-- GETTER PARA O THYMELEAF LER
}