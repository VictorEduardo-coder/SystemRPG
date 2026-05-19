package com.rpgsystem.zero.model;

public class FichaBoss extends Ficha {
    
    private String tipoBoss; // Comum, Glitch, Craft, Chefe
    private int xpBase;
    private int intimidacao;

    public FichaBoss(int usuarioId, String nome, String tipoBoss, int xpBase) {
        super(usuarioId, nome);
        this.tipoBoss = tipoBoss;
        this.xpBase = xpBase;
    }

    // Método exigido pela classe abstrata Ficha
    @Override
    public void exibirTipoFicha() {
        System.out.println("Tipo de Boss: " + this.tipoBoss);
    }

    // ==========================================
    // GETTERS E SETTERS ESPECÍFICOS
    // ==========================================
    public String getTipoBoss() { return tipoBoss; }
    public void setTipoBoss(String tipoBoss) { this.tipoBoss = tipoBoss; }

    public int getXpBase() { return xpBase; }
    public void setXpBase(int xpBase) { this.xpBase = xpBase; }

    public int getIntimidacao() { return intimidacao; }
    public void setIntimidacao(int intimidacao) { this.intimidacao = intimidacao; }

    // ==========================================
    // CÁLCULOS ESPECÍFICOS
    // ==========================================
    
    // O HP total é 15x o valor da Vida (conforme sua ficha base)
    public int getHpTotal() {
        return getVida() * 15;
    }

    @Override
    public String toString() {
        return super.toString() 
             + "\n===== DADOS DO BOSS ====="
             + "\nTipo: " + tipoBoss
             + "\nXP Base: " + xpBase
             + "\nHP Total: " + getHpTotal()
             + "\nIntimidação: " + intimidacao;
    }
}
