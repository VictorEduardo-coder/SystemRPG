package com.rpgsystem.zero.model;

public class FichaMonstro extends Ficha {

    private String tipoMonstro; // Comum, Glitch, Craft, Chefe
    private int xpBase;
    private int intimidacao;

    public FichaMonstro(int usuarioId, String nome, String tipoMonstro, int xpBase) {
        super(usuarioId, nome);
        this.tipoMonstro = tipoMonstro;
        this.xpBase = xpBase;
    }

    // Método exigido pela classe abstrata Ficha
    @Override
    public void exibirTipoFicha() {
        System.out.println("Tipo de Monstro: " + this.tipoMonstro);
    }

    // ==========================================
    // GETTERS E SETTERS ESPECÍFICOS
    // ==========================================
    public String getTipoMonstro() { return tipoMonstro; }
    public void setTipoMonstro(String tipoMonstro) { this.tipoMonstro = tipoMonstro; }

    public int getXpBase() { return xpBase; }
    public void setXpBase(int xpBase) { this.xpBase = xpBase; }

    public int getIntimidacao() { return intimidacao; }
    public void setIntimidacao(int intimidacao) { this.intimidacao = intimidacao; }

    // ==========================================
    // CÁLCULOS ESPECÍFICOS
    // ==========================================
    
    // O HP total é 10x o valor da Vida (conforme sua ficha base)
    public int getHpTotal() {
        return getVida() * 10;
    }

    @Override
    public String toString() {
        return super.toString() 
             + "\n===== DADOS DO MONSTRO ====="
             + "\nTipo: " + tipoMonstro
             + "\nXP Base: " + xpBase
             + "\nHP Total: " + getHpTotal()
             + "\nIntimidação: " + intimidacao;
    }
}
