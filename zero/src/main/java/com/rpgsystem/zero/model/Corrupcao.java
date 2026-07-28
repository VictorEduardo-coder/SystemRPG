package com.rpgsystem.zero.model;

public class Corrupcao {

    private int nivel;
    private String estagio;
    private String cor;
    private String efeitoMecanico;
    private String descricaoVisual;
    private int reducaoAgilidade;
    private int reducaoDefesa;
    private boolean fogoAmigo;
    private boolean semCura;
    private boolean controleMestre;
    private int bonusForca;
    private int bonusMana;
    private String nomeMutacao;
    private String efeitoMutacao;

    public Corrupcao() {
        this.nivel = 0;
        this.estagio = "🟢 Seguro";
        this.cor = "#28a745";
        this.efeitoMecanico = "Nenhum.";
        this.descricaoVisual = "Aparência normal.";
        this.reducaoAgilidade = 0;
        this.reducaoDefesa = 0;
        this.fogoAmigo = false;
        this.semCura = false;
        this.controleMestre = false;
        this.bonusForca = 0;
        this.bonusMana = 0;
        this.nomeMutacao = "";
        this.efeitoMutacao = "";
    }

    public static Corrupcao calcular(int nivel) {
        Corrupcao c = new Corrupcao();
        c.setNivel(nivel);

        if (nivel <= 50) {
            c.setEstagio("🟢 Seguro");
            c.setCor("#28a745");
            c.setEfeitoMecanico("Nenhum.");
            c.setDescricaoVisual("Aparência normal.");
        } else if (nivel <= 60) {
            c.setEstagio("🟡 I. Ruído");
            c.setCor("#ffc107");
            c.setEfeitoMecanico("HUD Falso. Mestre pode mentir sobre HP/Mana.");
            c.setDescricaoVisual("Visão pixelada, estática.");
        } else if (nivel <= 70) {
            c.setEstagio("🟠 II. Falha");
            c.setCor("#fd7e14");
            c.setEfeitoMecanico("-5 Agi/Des. Arma pode cair (20%).");
            c.setDescricaoVisual("Membros piscam/somem.");
            c.setReducaoAgilidade(5);
            c.setReducaoDefesa(0);
        } else if (nivel <= 80) {
            c.setEstagio("🔴 III. Invasão");
            c.setCor("#dc3545");
            c.setEfeitoMecanico("Fogo Amigo. Se tirar 1, 2 ou 3 no dado, ataca aliado.");
            c.setDescricaoVisual("Vozes, glifos na pele.");
            c.setFogoAmigo(true);
        } else if (nivel <= 90) {
            c.setEstagio("🟣 IV. Híbrido");
            c.setCor("#6f42c1");
            c.setEfeitoMecanico("Sem Cura (Luz/Poção). Ganha +10 For/Mana.");
            c.setDescricaoVisual("Tentáculos, olhos extras.");
            c.setSemCura(true);
            c.setBonusForca(10);
            c.setBonusMana(10);
        } else if (nivel <= 99) {
            c.setEstagio("⚫ V. Crise");
            c.setCor("#343a40");
            c.setEfeitoMecanico("Perda de Controle. Rolar 1d20 < 10 = Mestre controla.");
            c.setDescricaoVisual("Nome ilegível, monstro.");
            c.setControleMestre(true);
        } else if (nivel >= 100) {
            c.setEstagio("💀 Assimilação");
            c.setCor("#000000");
            c.setEfeitoMecanico("GAME OVER. Personagem vira monstro NPC.");
            c.setDescricaoVisual("Morte Real.");
            c.setControleMestre(true);
        }

        return c;
    }

    public static String getEstagioPorNivel(int nivel) {
        if (nivel <= 50) return "🟢 Seguro";
        if (nivel <= 60) return "🟡 I. Ruído";
        if (nivel <= 70) return "🟠 II. Falha";
        if (nivel <= 80) return "🔴 III. Invasão";
        if (nivel <= 90) return "🟣 IV. Híbrido";
        if (nivel <= 99) return "⚫ V. Crise";
        return "💀 Assimilação";
    }

    public static String getCorPorNivel(int nivel) {
        if (nivel <= 50) return "#28a745";
        if (nivel <= 60) return "#ffc107";
        if (nivel <= 70) return "#fd7e14";
        if (nivel <= 80) return "#dc3545";
        if (nivel <= 90) return "#6f42c1";
        if (nivel <= 99) return "#343a40";
        return "#000000";
    }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public String getEstagio() { return estagio; }
    public void setEstagio(String estagio) { this.estagio = estagio; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public String getEfeitoMecanico() { return efeitoMecanico; }
    public void setEfeitoMecanico(String efeitoMecanico) { this.efeitoMecanico = efeitoMecanico; }

    public String getDescricaoVisual() { return descricaoVisual; }
    public void setDescricaoVisual(String descricaoVisual) { this.descricaoVisual = descricaoVisual; }

    public int getReducaoAgilidade() { return reducaoAgilidade; }
    public void setReducaoAgilidade(int reducaoAgilidade) { this.reducaoAgilidade = reducaoAgilidade; }

    public int getReducaoDefesa() { return reducaoDefesa; }
    public void setReducaoDefesa(int reducaoDefesa) { this.reducaoDefesa = reducaoDefesa; }

    public boolean isFogoAmigo() { return fogoAmigo; }
    public void setFogoAmigo(boolean fogoAmigo) { this.fogoAmigo = fogoAmigo; }

    public boolean isSemCura() { return semCura; }
    public void setSemCura(boolean semCura) { this.semCura = semCura; }

    public boolean isControleMestre() { return controleMestre; }
    public void setControleMestre(boolean controleMestre) { this.controleMestre = controleMestre; }

    public int getBonusForca() { return bonusForca; }
    public void setBonusForca(int bonusForca) { this.bonusForca = bonusForca; }

    public int getBonusMana() { return bonusMana; }
    public void setBonusMana(int bonusMana) { this.bonusMana = bonusMana; }

    public String getNomeMutacao() { return nomeMutacao; }
    public void setNomeMutacao(String nomeMutacao) { this.nomeMutacao = nomeMutacao; }

    public String getEfeitoMutacao() { return efeitoMutacao; }
    public void setEfeitoMutacao(String efeitoMutacao) { this.efeitoMutacao = efeitoMutacao; }

    @Override
    public String toString() {
        return """
                ========== CORRUPÇÃO ==========
                Nível: %d
                Estágio: %s
                Efeito: %s
                Visual: %s
                """
                .formatted(nivel, estagio, efeitoMecanico, descricaoVisual);
    }
}