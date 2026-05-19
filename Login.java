package com.rpgsystem.zero.model;

import java.util.Scanner;

import com.rpgsystem.zero.repository.UsuarioDAO;

public class Login {

    private Scanner scanner = new Scanner(System.in);

    public Login() {

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n========== RPG SYSTEM ==========");
            System.out.println("1. Criar Conta");
            System.out.println("2. Fazer Login");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
                continue;
            }

            switch (opcao) {

                case 1 -> cadastrarUsuario();

                case 2 -> realizarLogin();

                case 0 -> System.out.println("Encerrando sistema...");

                default -> System.out.println("Opção inválida!");
            }
        }
    }

    // ==================================================
    // CADASTRO
    // ==================================================

    private void cadastrarUsuario() {

        System.out.println("\n====== CRIAR CONTA ======");

        System.out.print("Nome do jogador: ");
        String nome = scanner.nextLine();

        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.println("\nTipo da Conta:");
        System.out.println("1. PLAYER");
        System.out.println("2. MESTRE");

        int tipo = Integer.parseInt(scanner.nextLine());

        PerfilAcesso perfil;

        if (tipo == 2) {
            perfil = PerfilAcesso.MESTRE;
        } else {
            perfil = PerfilAcesso.PLAYER;
        }

        boolean sucesso = UsuarioDAO.cadastrarUsuario(
                nome,
                login,
                senha,
                perfil
        );

        if (sucesso) {

            System.out.println("\nConta criada com sucesso!");

        } else {

            System.out.println("\nErro ao criar conta.");
        }
    }

    // ==================================================
    // LOGIN
    // ==================================================

    private void realizarLogin() {

        System.out.println("\n====== LOGIN ======");

        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = UsuarioDAO.autenticarUsuario(login, senha);

        if (usuario != null) {

            System.out.println("\nLogin realizado com sucesso!");

            registrarLog(usuario, "LOGIN");

            abrirPainel(usuario);

        } else {

            System.out.println("Usuário ou senha incorretos.");
        }
    }

    // ==================================================
    // REDIRECIONAMENTO
    // ==================================================

    private void abrirPainel(Usuario usuario) {

        if (usuario.getPerfil() == PerfilAcesso.MESTRE) {

            painelMestre(usuario);

        } else {

            painelPlayer(usuario);
        }
    }

    // ==================================================
    // PAINEL PLAYER
    // ==================================================

    private void painelPlayer(Usuario usuario) {

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n====== PAINEL PLAYER ======");
            System.out.println("Jogador: " + usuario.getNome());

            System.out.println("1. Ver Ficha");
            System.out.println("2. Inventário");
            System.out.println("3. Habilidades");
            System.out.println("4. Loja");
            System.out.println("5. Alterar Dados");
            System.out.println("0. Logout");

            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
                continue;
            }

            switch (opcao) {

                case 1 -> visualizarFicha(usuario);

                case 2 -> abrirInventario(usuario);

                case 3 -> abrirHabilidades(usuario);

                case 4 -> abrirLoja(usuario);

                case 5 -> alterarDados(usuario);

                case 0 -> {
                    registrarLog(usuario, "LOGOUT");
                    System.out.println("Logout realizado.");
                }

                default -> System.out.println("Opção inválida.");
            }
        }
    }

    // ==================================================
    // PAINEL MESTRE
    // ==================================================

    private void painelMestre(Usuario usuario) {

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n====== PAINEL MESTRE ======");

            System.out.println("1. Ver Todos os Players");
            System.out.println("2. Ver Logs");
            System.out.println("3. Editar Fichas");
            System.out.println("4. Controlar Economia");
            System.out.println("5. Gerenciar Loja");
            System.out.println("0. Logout");

            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
                continue;
            }

            switch (opcao) {

                case 1 -> listarPlayers();

                case 2 -> visualizarLogs();

                case 3 -> editarFicha();

                case 4 -> controlarEconomia();

                case 5 -> gerenciarLoja();

                case 0 -> {
                    registrarLog(usuario, "LOGOUT");
                    System.out.println("Logout realizado.");
                }

                default -> System.out.println("Opção inválida.");
            }
        }
    }

    // ==================================================
    // MÉTODOS FUTUROS
    // ==================================================

    private void visualizarFicha(Usuario usuario) {

        System.out.println("Abrindo ficha...");
    }

    private void abrirInventario(Usuario usuario) {

        System.out.println("Abrindo inventário...");
    }

    private void abrirHabilidades(Usuario usuario) {

        System.out.println("Abrindo habilidades...");
    }

    private void abrirLoja(Usuario usuario) {

        System.out.println("Abrindo loja...");
    }

    private void alterarDados(Usuario usuario) {

        System.out.println("Alterando dados...");
    }

    private void listarPlayers() {

        System.out.println("Listando players...");
    }

    private void visualizarLogs() {

        System.out.println("Abrindo logs...");
    }

    private void editarFicha() {

        System.out.println("Editando ficha...");
    }

    private void controlarEconomia() {

        System.out.println("Controlando economia...");
    }

    private void gerenciarLoja() {

        System.out.println("Gerenciando loja...");
    }

    private void registrarLog(Usuario usuario, String evento) {

        System.out.println("[LOG] "
                + usuario.getNome()
                + " -> "
                + evento);
    }
}