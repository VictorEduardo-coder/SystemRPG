package com.rpgsystem.zero.model; // Ajustado para o pacote correto

import java.util.Scanner;

import com.rpgsystem.zero.repository.FichaDAO;

public class FichaNPC extends Ficha {

    // Atributos mockados para compatibilidade com o seu código de menu
    protected String nome;
    protected PerfilAcesso perfil = PerfilAcesso.PLAYER; // Substitua pelo seu objeto real de Perfil se houver
    
    // Construtor obrigatório herdando da classe mãe
    public FichaNPC(int usuarioId, String nome) {
        super(usuarioId, nome);
        this.nome = nome;
    }

    @Override
    public void exibirTipoFicha() {
        System.out.println("Tipo: NPC");
    }

    // ==================================================
    // PROCESSAR ESCOLHA DO MENU
    // ==================================================
    private void processarEscolha(int opcao, Scanner leitor) {
        switch (opcao) {
            case 1:
                if (podeExecutar("VER_PROPRIA_FICHA")) {
                    visualizarFicha();
                }
                break;
            case 2:
                if (podeExecutar("EDITAR_PROPRIA_FICHA")) {
                    editarFicha(leitor);
                }
                break;
            case 3:
                if (podeExecutar("VER_INVENTARIO")) {
                    visualizarInventario();
                }
                break;
            case 4:
                if (podeExecutar("VER_HABILIDADES")) {
                    visualizarHabilidades();
                }
                break;
            case 5:
                if (podeExecutar("VER_TODAS_FICHAS")) {
                    visualizarTodasFichas();
                }
                break;
            case 6:
                if (podeExecutar("VER_LOGS")) {
                    visualizarLogs();
                }
                break;
            case 0:
                System.out.println("Saindo do sistema...");
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // ==================================================
    // ACESSAR SISTEMA (MENU PRINCIPAL)
    // ==================================================
    public void acessarSistema() {
        Scanner leitor = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n===== PAINEL RPG =====");
            System.out.println("NPC: " + this.getNome());
            System.out.println("========================");

            if (podeExecutar("VER_PROPRIA_FICHA")) {
                System.out.println("1. Ver Minha Ficha");
            }
            if (podeExecutar("EDITAR_PROPRIA_FICHA")) {
                System.out.println("2. Editar Minha Ficha");
            }
            if (podeExecutar("VER_INVENTARIO")) {
                System.out.println("3. Ver Inventário");
            }
            if (podeExecutar("VER_HABILIDADES")) {
                System.out.println("4. Ver Habilidades");
            }
            if (podeExecutar("VER_TODAS_FICHAS")) {
                System.out.println("5. Ver Todas as Fichas");
            }
            if (podeExecutar("VER_LOGS")) {
                System.out.println("6. Ver Logs do Sistema");
            }

            System.out.println("0. Logout");
            System.out.print("Escolha: ");

            opcao = leitor.nextInt();
            leitor.nextLine();

            processarEscolha(opcao, leitor);

        } while (opcao != 0);
    }

    // ==================================================
    // EDITAR FICHA
    // ==================================================
    private void editarFicha(Scanner leitor) {
        Ficha ficha = FichaDAO.buscarFichaPorUsuario(this.getId());

        if (ficha == null) {
            System.out.println("Ficha não encontrada.");
            return;
        }

        System.out.println("\n===== EDITAR FICHA =====");
        System.out.println("1. Alterar Classe");
        System.out.println("2. Distribuir Pontos");
        System.out.println("3. Alterar Título");
        System.out.print("Escolha: ");

        int opcao = leitor.nextInt();
        leitor.nextLine();

        switch (opcao) {
            case 1:
                System.out.print("Nova classe: ");
                String novaClasse = leitor.nextLine();
                ficha.setClasse(novaClasse);
                break;
            case 2:
                distribuirPontos(ficha, leitor);
                break;
            case 3:
                System.out.print("Novo título: ");
                String titulo = leitor.nextLine();
                ficha.setTitulo(titulo);
                break;
        }

        FichaDAO.atualizarFicha(ficha);
        System.out.println("Ficha atualizada!");
    }

    // ==================================================
    // MÉTODOS AUXILIARES (STUBS/MOCKS)
    // ==================================================
    private boolean podeExecutar(String permissao) {
        return true; // Mude para a sua regra real de controle de acessos
    }

    private void visualizarFicha() {
        System.out.println(this.toString());
    }

    private void distribuirPontos(Ficha ficha, Scanner leitor) {
        System.out.println("Funcionalidade de distribuir pontos (Em desenvolvimento).");
    }

    private void visualizarInventario() { System.out.println("Exibindo Inventário..."); }
    private void visualizarHabilidades() { System.out.println("Exibindo Habilidades..."); }
    private void visualizarTodasFichas() { System.out.println("Exibindo Todas as Fichas..."); }
    private void visualizarLogs() { System.out.println("Exibindo Logs..."); }
}