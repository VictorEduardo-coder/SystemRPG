package com.rpgsystem.zero.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DadoServiceTest {

    private DadoService dadoService;

    @BeforeEach
    void setUp() {
        dadoService = new DadoService();
    }

    @Test
    void retornaZeroParaExpressaoVaziaOuNula() {
        assertEquals(0, dadoService.rolar(null).total());
        assertEquals(0, dadoService.rolar("   ").total());
        assertTrue(dadoService.rolar("").detalhes().contains("Expressão vazia"));
    }

    @Test
    void calculaRolagemComModificadorPositivoENegativoDentroDoIntervalo() {
        for (int i = 0; i < 40; i++) {
            DadoService.ResultadoDado res = dadoService.rolar("2d6 + 3");
            assertTrue(res.total() >= 5 && res.total() <= 15,
                    "Total fora do intervalo esperado [5, 15]: " + res.total());
            assertTrue(res.detalhes().startsWith("2d6+3 = "));
        }

        DadoService.ResultadoDado sub = dadoService.rolar("1D4 - 2");
        assertTrue(sub.total() >= -1 && sub.total() <= 2);
    }

    @Test
    void aceitaNotacaoSemQuantidadeExplicitaComoUmDado() {
        DadoService.ResultadoDado res = dadoService.rolar("d20");
        assertTrue(res.total() >= 1 && res.total() <= 20);
    }

    @Test
    void rejeitaLimitesInvalidosDeQuantidadeOuFaces() {
        DadoService.ResultadoDado zeroDados = dadoService.rolar("0d20");
        assertEquals(0, zeroDados.total());
        assertTrue(zeroDados.detalhes().contains("Limite inválido"));

        DadoService.ResultadoDado excessoDados = dadoService.rolar("101d6");
        assertEquals(0, excessoDados.total());
        assertTrue(excessoDados.detalhes().contains("Limite inválido"));

        DadoService.ResultadoDado excessoFaces = dadoService.rolar("1d10001");
        assertEquals(0, excessoFaces.total());
        assertTrue(excessoFaces.detalhes().contains("Limite inválido"));
    }
}
