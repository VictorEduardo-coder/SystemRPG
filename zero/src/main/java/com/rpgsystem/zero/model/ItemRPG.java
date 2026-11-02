package com.rpgsystem.zero.model;

public class ItemRPG {
    private Long id;
    private Integer fichaId;
    private String nome;
    private String tipo;
    private String raridade;
    private int quantidade;
    private int poder;

    public ItemRPG() {}

    public ItemRPG(Long id, Integer fichaId, String nome, String tipo, String raridade, int quantidade, int poder) {
        this.id = id;
        this.fichaId = fichaId;
        this.nome = nome;
        this.tipo = tipo;
        this.raridade = raridade;
        this.quantidade = quantidade;
        this.poder = poder;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getFichaId() { return fichaId; }
    public void setFichaId(Integer fichaId) { this.fichaId = fichaId; }
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