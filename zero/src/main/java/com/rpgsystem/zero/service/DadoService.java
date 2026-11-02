package com.rpgsystem.zero.service;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class DadoService {

    private final Random random = new Random();

    public record ResultadoDado(int total, String detalhes) {}

    public ResultadoDado rolar(String expressao) {
        if (expressao == null || expressao.isBlank()) {
            return new ResultadoDado(0, "Expressão vazia = 0");
        }

        expressao = expressao.replaceAll("\\s+", "").toLowerCase();

        Pattern pattern = Pattern.compile("([+-]?)(\\d*d\\d+|\\d+)");
        Matcher matcher = pattern.matcher(expressao);

        int total = 0;
        StringBuilder detalhes = new StringBuilder();

        while (matcher.find()) {
            String sinal = matcher.group(1);
            String valor = matcher.group(2);
            int multiplicador = sinal.equals("-") ? -1 : 1;

            if (valor.contains("d")) {
                String[] partes = valor.split("d");
                int qtd = partes[0].isEmpty() ? 1 : Integer.parseInt(partes[0]);
                int lados = Integer.parseInt(partes[1]);

                if (qtd < 1 || qtd > 100 || lados < 1 || lados > 10000) {
                    return new ResultadoDado(0, "Limite inválido (máx 100d10000, mín 1 face)");
                }

                detalhes.append(sinal).append("(");
                for (int i = 0; i < qtd; i++) {
                    int rolagem = random.nextInt(lados) + 1;
                    total += rolagem * multiplicador;
                    detalhes.append(rolagem).append(i < qtd - 1 ? "+" : "");
                }
                detalhes.append(")");
            } else {
                int modificador = Integer.parseInt(valor);
                total += modificador * multiplicador;
                detalhes.append(sinal).append(modificador);
            }
        }

        String extrato = detalhes.toString().startsWith("+") ? detalhes.substring(1) : detalhes.toString();

        return new ResultadoDado(total, expressao + " = " + extrato + " = " + total);
    }
}