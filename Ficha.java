package com.rpgsystem.zero.model;

public abstract class Ficha {

    // ==========================================
    // IDENTIFICAÇÃO
    // ==========================================
    private int id;
    private int usuarioId;
    private String nome;
    private int nivel;
    private int xpAtual;
    private int xpProximo;
    private int i_idade; // Renomeado levemente ou mantido como idade
    private int idade;
    private String classe;
    private String titulo;

    // ==========================================
    // ECONOMIA
    // ==========================================
    private double dinheiroReal;
    private int gold;
    private int tr;

    // ==========================================
    // FALHA
    // ==========================================
    private String falhaNome;
    private String falhaEfeito;

    // ==========================================
    // STATUS BASE
    // ==========================================
    private int forca;
    private int vida;
    private int agilidade;
    private int mana;
    private int carisma;
    private int sorte;

    // ==========================================
    // PERÍCIAS
    // ==========================================
    private int seduzir;
    private int intimidar;
    private int estudar;
    private int investigar;
    private int politica;
    private int religiao;
    private int artes;
    private int furtividade;
    private int construir;

    // ==========================================
    // REALIDADE
    // ==========================================
    private double saldoAtual;
    private double custoVida;
    private int diasRestantes;
    private String nutricao;
    private String conexao;

    // ==========================================
    // CONSTRUTOR
    // ==========================================
    public Ficha(int usuarioId, String nome) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.nivel = 1;
        this.xpAtual = 0;
        this.xpProximo = 10;
        this.classe = "Sem Classe";
        this.titulo = "";
        this.nutricao = "BASICO";
        this.conexao = "NORMAL";
    }

    public abstract void exibirTipoFicha();

    // ==========================================
    // STATUS DERIVADOS (SEM SETTERS - PROTEGIDOS)
    // ==========================================
    public int getDefesa() {
        return vida + forca;
    }

    public double getFlexibilidade() {
        return agilidade / 2.0;
    }

    public double getEsquiva() {
        return (agilidade + getFlexibilidade()) / 2.0;
    }

    public double getVontade() {
        return (mana + vida) / 2.0;
    }

    // ==========================================
    // GETTERS E SETTERS BASE
    // ==========================================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public int getXpAtual() { return xpAtual; }
    public void setXpAtual(int xpAtual) { this.xpAtual = xpAtual; }

    public int getXpProximo() { return xpProximo; }
    public void setXpProximo(int xpProximo) { this.xpProximo = xpProximo; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public double getDinheiroReal() { return dinheiroReal; }
    public void setDouble(double dinheiroReal) { this.dinheiroReal = dinheiroReal; }
    public void setDinheiroReal(double dinheiroReal) { this.dinheiroReal = dinheiroReal; }

    public int getGold() { return gold; }
    public void setGold(int gold) { this.gold = gold; }

    public int getTr() { return tr; }
    public void setTr(int tr) { this.tr = tr; }

    public String getFalhaNome() { return falhaNome; }
    public void setFalhaNome(String falhaNome) { this.falhaNome = falhaNome; }

    public String getFalhaEfeito() { return falhaEfeito; }
    public void setFalhaEfeito(String falhaEfeito) { this.falhaEfeito = falhaEfeito; }

    public int getForca() { return forca; }
    public void setForca(int forca) { this.forca = forca; }

    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = vida; }

    public int getAgilidade() { return agilidade; }
    public void setAgilidade(int agilidade) { this.agilidade = agilidade; }

    public int getMana() { return mana; }
    public void setMana(int mana) { this.mana = mana; }

    public int getCarisma() { return carisma; }
    public void setCarisma(int carisma) { this.carisma = carisma; }

    public int getSorte() { return sorte; }
    public void setSorte(int sorte) { this.sorte = sorte; }

    // Perícias
    public int getSeduzir() { return seduzir; }
    public void setSeduzir(int seduzir) { this.seduzir = seduzir; }

    public int getIntimidar() { return intimidar; }
    public void setIntimidar(int intimidar) { this.intimidar = intimidar; }

    public int getEstudar() { return estudar; }
    public void setEstudar(int estudar) { this.estudar = estudar; }

    public int getInvestigar() { return investigar; }
    public void setInvestigar(int investigar) { this.investigar = investigar; }

    public int getPolitica() { return politica; }
    public void setPolitica(int politica) { this.politica = politica; }

    public int getReligiao() { return religiao; }
    public void setReligiao(int religiao) { this.religiao = religiao; }

    public int getArtes() { return artes; }
    public void setArtes(int artes) { this.artes = artes; }

    public int getFurtividade() { return furtividade; }
    public void setFurtividade(int furtividade) { this.furtividade = furtividade; }

    public int getConstruir() { return construir; }
    public void setConstruir(int construir) { this.construir = construir; }

    // Realidade
    public double getSaldoAtual() { return saldoAtual; }
    public void setSaldoAtual(double saldoAtual) { this.saldoAtual = saldoAtual; }

    public double getCustoVida() { return custoVida; }
    public void setCustoVida(double custoVida) { this.custoVida = custoVida; }

    public int getDiasRestantes() { return diasRestantes; }
    public void setDiasRestantes(int diasRestantes) { this.diasRestantes = diasRestantes; }

    public String getNutricao() { return nutricao; }
    public void setNutricao(String nutricao) { this.nutricao = nutricao; }

    public String getConexao() { return conexao; }
    public void setConexao(String conexao) { this.conexao = conexao; }

    @Override
    public String toString() {
        return "\n========== FICHA =========="
                + "\nNome: " + nome
                + "\nNível: " + nivel + " (XP: " + xpAtual + "/" + xpProximo + ")"
                + "\nClasse: " + classe
                + "\nTítulo: " + titulo
                + "\n\n===== STATUS ====="
                + "\nForça: " + forca
                + "\nVida: " + vida
                + "\nAgilidade: " + agilidade
                + "\nMana: " + mana
                + "\nCarisma: " + carisma
                + "\nSorte: " + sorte
                + "\n\n===== DERIVADOS ====="
                + "\nDefesa: " + getDefesa()
                + "\nFlexibilidade: " + getFlexibilidade()
                + "\nEsquiva: " + getEsquiva()
                + "\nVontade: " + getVontade();
    }
}