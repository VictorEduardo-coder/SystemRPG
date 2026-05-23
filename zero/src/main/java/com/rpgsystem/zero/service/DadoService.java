package com.rpgsystem.zero.service;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class DadoService {

    private final Random random = new Random();

    // Classe interna para devolver o total e como os dados caíram
    public record ResultadoDado(int total, String detalhes) {}

    public ResultadoDado rolar(String expressao) {
        // Remove espaços em branco para facilitar (ex: "1d20 + 5" vira "1d20+5")
        expressao = expressao.replaceAll("\\s+", "").toLowerCase();
        
        // Regex para capturar partes como "1d20", "+2d5", "+5", "-3"
        Pattern pattern = Pattern.compile("([+-]?)(\\d*d\\d+|\\d+)");
        Matcher matcher = pattern.matcher(expressao);

        int total = 0;
        StringBuilder detalhes = new StringBuilder();

        while (matcher.find()) {
            String sinal = matcher.group(1);
            String valor = matcher.group(2);
            int multiplicador = sinal.equals("-") ? -1 : 1;

            if (valor.contains("d")) {
                // É um dado (ex: 1d20 ou 2d5)
                String[] partes = valor.split("d");
                int qtd = partes[0].isEmpty() ? 1 : Integer.parseInt(partes[0]);
                int lados = Integer.parseInt(partes[1]);

                detalhes.append(sinal).append("(");
                for (int i = 0; i < qtd; i++) {
                    int rolagem = random.nextInt(lados) + 1;
                    total += rolagem * multiplicador;
                    detalhes.append(rolagem).append(i < qtd - 1 ? "+" : "");
                }
                detalhes.append(")");
            } else {
                // É um modificador fixo (ex: 5)
                int modificador = Integer.parseInt(valor);
                total += modificador * multiplicador;
                detalhes.append(sinal).append(modificador);
            }
        }

        // Limpa o primeiro sinal de "+" se houver na string de detalhes
        String extrato = detalhes.toString().startsWith("+") ? detalhes.substring(1) : detalhes.toString();
        
        return new ResultadoDado(total, expressao + " = " + extrato + " = " + total);
    }
}