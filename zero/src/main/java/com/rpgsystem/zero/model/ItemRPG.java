package com.rpgsystem.zero.model;

public class ItemRPG {
    private Long id;
    private String nome;
    private String tipo; // Ex: Consumível, Espada, Escudo
    private String raridade; // Ex: Comum, Raro, Épico, Lendário
    private int quantidade;
    private int poder; // Pode ser dano de arma ou cura de poção

    // Construtor vazio
    public ItemRPG() {}

    // Construtor completo
    public ItemRPG(Long id, String nome, String tipo, String raridade, int quantidade, int poder) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.raridade = raridade;
        this.quantidade = quantidade;
        this.poder = poder;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getRaridade() { return raridade; }
    public void setRaridade(String raridade) { this.raridade = raridade; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public int getPoder() { return poder; }
    public void setPoder(int poder) { this.poder = poder; }
}