package com.rpgsystem.zero.model;

public class FichaPlayer extends Ficha {

    public FichaPlayer(int usuarioId, String nome) {
        super(usuarioId, nome);
    }

    @Override
    public void exibirTipoFicha() {
        System.out.println("Tipo: Jogador (Player)");
    }
}