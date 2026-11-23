package com.rpgsystem.zero.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CorrupcaoAndFichaTest {

    @Test
    void calculaTodosOsEstagiosDeCorrupcaoESeusEfeitosMecanicos() {
        Corrupcao seguro = Corrupcao.calcular(50);
        assertEquals("🟢 Seguro", seguro.getEstagio());
        assertFalse(seguro.isFogoAmigo());
        assertFalse(seguro.isSemCura());
        assertFalse(seguro.isControleMestre());

        Corrupcao ruido = Corrupcao.calcular(55);
        assertEquals("🟡 I. Ruído", ruido.getEstagio());

        Corrupcao falha = Corrupcao.calcular(70);
        assertEquals("🟠 II. Falha", falha.getEstagio());
        assertEquals(5, falha.getReducaoAgilidade());

        Corrupcao invasao = Corrupcao.calcular(75);
        assertEquals("🔴 III. Invasão", invasao.getEstagio());
        assertTrue(invasao.isFogoAmigo());

        Corrupcao hibrido = Corrupcao.calcular(85);
        assertEquals("🟣 IV. Híbrido", hibrido.getEstagio());
        assertTrue(hibrido.isSemCura());
        assertEquals(10, hibrido.getBonusForca());
        assertEquals(10, hibrido.getBonusMana());

        Corrupcao crise = Corrupcao.calcular(95);
        assertEquals("⚫ V. Crise", crise.getEstagio());
        assertTrue(crise.isControleMestre());

        Corrupcao assimilacao = Corrupcao.calcular(100);
        assertEquals("💀 Assimilação", assimilacao.getEstagio());
        assertTrue(assimilacao.isControleMestre());
    }

    @Test
    void calculaStatusDerivadosEValoresPadraoDaFicha() {
        Ficha ficha = new Ficha();
        assertEquals(1, ficha.getNivel());
        assertEquals(0, ficha.getXpAtual());
        assertEquals(10, ficha.getXpProximo());
        assertEquals("Sem Classe", ficha.getClasse());
        assertEquals(0, ficha.getCorrupcao());

        ficha.setForca(12);
        ficha.setVida(18);
        ficha.setAgilidade(10);
        ficha.setMana(8);

        assertEquals(30, ficha.getDefesa());
        assertEquals(5.0, ficha.getFlexibilidade());
        assertEquals(7.5, ficha.getEsquiva());
        assertEquals(13.0, ficha.getVontade());
    }

    @Test
    void validaMatrizDePermissoesPorPerfilDeAcesso() {
        assertEquals(PerfilAcesso.PLAYER, PerfilAcesso.porNivel(0));
        assertEquals(PerfilAcesso.MESTRE, PerfilAcesso.porNivel(1));
        assertEquals(PerfilAcesso.ADMIN, PerfilAcesso.porNivel(2));
        assertThrows(IllegalArgumentException.class, () -> PerfilAcesso.porNivel(99));

        assertTrue(PerfilAcesso.PLAYER.temPermissao("EDITAR_PROPRIA_FICHA"));
        assertFalse(PerfilAcesso.PLAYER.temPermissao("VER_TODAS_FICHAS"));
        assertFalse(PerfilAcesso.PLAYER.temPermissao("LIMPAR_LOGS"));

        assertTrue(PerfilAcesso.MESTRE.temPermissao("VER_TODAS_FICHAS"));
        assertTrue(PerfilAcesso.MESTRE.temPermissao("VER_LOGS"));
        assertFalse(PerfilAcesso.MESTRE.temPermissao("EXCLUIR_USUARIO"));

        assertTrue(PerfilAcesso.ADMIN.temPermissao("EXCLUIR_USUARIO"));
        assertTrue(PerfilAcesso.ADMIN.temPermissao("LIMPAR_LOGS"));
        assertFalse(PerfilAcesso.ADMIN.temPermissao("ACAO_DESCONHECIDA"));
    }
}
