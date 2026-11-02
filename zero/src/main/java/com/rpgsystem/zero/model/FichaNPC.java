package com.rpgsystem.zero.model;

public class FichaNPC extends Ficha {

    public FichaNPC(int usuarioId, String nome) {
        super(usuarioId, nome);
    }

    @Override
    public void exibirTipoFicha() {
        System.out.println("Tipo: NPC");
    }
}