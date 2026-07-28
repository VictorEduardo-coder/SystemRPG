# 🎲 Sistema de RPG (Zero RPG System)

## 📖 Sobre o Projeto
O **Sistema de RPG (Zero)** é uma aplicação web robusta desenvolvida em **Java (Spring Boot)**, criada com o objetivo de digitalizar e facilitar campanhas de RPG de mesa. A plataforma oferece uma interface dividida para Mestres e Jogadores, permitindo o gerenciamento completo de fichas, inventários, NPCs, rolagem de dados interativa e muito mais.

---

## 🚀 Principais Funcionalidades

### 🛡️ Para Jogadores
* **Criação e Gestão de Fichas:** Crie seu personagem, escolha sua classe e edite os atributos diretamente no **Painel do Player**.
* **Inventário Dinâmico:** Adicione, remova e gerencie itens do seu personagem através do sistema de inventário.
* **Habilidades:** Controle as habilidades específicas da sua classe ou personagem.
* **Rolagem de Dados:** Sistema integrado de rolagem de dados para realizar testes durante a partida.

### 🧙‍♂️ Para o Mestre
* **Painel do Mestre:** Um hub central para gerenciar a campanha.
* **Controle de Entidades:** Crie e gerencie fichas exclusivas para **NPCs**, **Monstros** e **Bosses**.
* **Gerenciamento de Regras e Classes:** Controle flexível do universo do jogo.
* **Visão Geral:** Acompanhe as estatísticas, testes e o progresso da narrativa.

### ⚙️ Recursos de Sistema
* **Tempo Real:** Utilização de **WebSockets** para comunicação instantânea (ex: compartilhamento de resultados de dados).
* **Autenticação:** Sistema de Login e Cadastro com separação por Perfil de Acesso (`Mestre` vs `Jogador`).

---

## 🛠️ Tecnologias Utilizadas
* **Backend:** Java 17+, Spring Boot
* **Frontend:** HTML5, CSS3, Thymeleaf (Templates Dinâmicos)
* **Comunicação em Tempo Real:** Spring WebSockets (`WebSocketConfig`)
* **Banco de Dados:** Banco de dados embutido (`rpg.db`), gerenciado via padrão DAO e JDBC (`ConexaoDB.java`).
* **Gerenciador de Dependências:** Maven

---

## 📁 Arquitetura e Estrutura de Diretórios

A aplicação segue o padrão MVC (Model-View-Controller) tradicional do ecossistema Spring:

* `model/`: Contém as entidades do sistema (`Usuario`, `FichaPlayer`, `FichaBoss`, `ItemRPG`, `Regra`, etc).
* `controller/`: Gerencia as requisições web, roteamento das páginas e WebSockets (`FichaController`, `DadoController`, `LoginController`).
* `repository/`: Camada de persistência de dados padrão DAO (`UsuarioDAO`, `FichaDAO`, `ItemDAO`).
* `service/`: Regras de negócio e lógicas independentes (`DadoService`, `LogEmailService`).
* `config/`: Configurações de infraestrutura como o `WebSocketConfig`.
* `resources/templates/`: Páginas da interface de usuário prontas para renderização.
* `resources/static/`: Arquivos estáticos (CSS, scripts).

---

## 💻 Como Executar o Projeto Localmente

1. **Pré-requisitos:** Certifique-se de ter o **Java (JDK 17 ou superior)** instalado na sua máquina.
2. **Abra o terminal** na pasta `zero` (onde se encontra o arquivo `pom.xml`).
3. **Inicie o servidor** utilizando o Maven Wrapper que já vem no projeto:
   * No Windows:
     ```bash
     mvnw.cmd spring-boot:run
     ```
   * No Linux/Mac:
     ```bash
     ./mvnw spring-boot:run
     ```
4. **Acesse no Navegador:** Após a inicialização, abra `http://localhost:8080`. Você será direcionado para a página inicial (`index.html`) ou para a tela de login.

---
*Documentação gerada automaticamente a partir da análise estrutural do repositório SystemRPG-main.*
