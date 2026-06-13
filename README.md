# 🎲 SystemRPG - TTRPG Backend API

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)

## 📖 Sobre o Projeto

O **SystemRPG** é uma API construída para gerenciar os bastidores, as regras matemáticas e o balanceamento de um sistema autoral de Tabletop RPG (TTRPG). 

Este projeto nasceu da necessidade de automatizar as lógicas complexas de _worldbuilding_ e progressão de personagens do universo Zero Life. Ele serve como o motor (engine) que calcula interações, gerencia o banco de dados da campanha e garante a integridade das regras do jogo, demonstrando a aplicação de conceitos sólidos de Orientação a Objetos e arquitetura de software em um cenário dinâmico.

## ✨ Principais Funcionalidades e Regras de Negócio

* **Gerenciamento de Personagens:** Cadastro, atualização de status e controle de progressão contínua.
* **Sistema Dinâmico de Corrupção:** Implementação de uma mecânica de acúmulo e recuperação de corrupção. A lógica backend processa algoritmos de alívio baseados em eventos específicos e rotineiros, abatendo níveis de corrupção quando o sistema registra gatilhos como situações de carinho, a conclusão de trabalhos no dia ou a evolução de nível do personagem.
* **Motor Econômico:** Gestão completa do fluxo financeiro do jogo. O sistema valida transações comerciais, armazenamento de riquezas e conversões utilizando exclusivamente o Tron ou Gold, as moedas oficiais do cenário, impedindo saldos negativos e fraudes no inventário.
* **Controle de Inventário e Itens:** Relacionamento de tabelas para associar equipamentos e consumíveis a personagens específicos.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (Foco em OOP e design patterns para regras de RPG)
* **Banco de Dados:** MySQL (Modelagem relacional para personagens, itens e transações)
* **Gerenciamento de Dependências:** Maven / Gradle *(Ajuste conforme o que você usar)*

## 🚀 Como executar este projeto

### Pré-requisitos
Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
* [Git](https://git-scm.com)
* [Java JDK 17+](https://www.oracle.com/java/technologies/javase-downloads.html) *(Ou a versão que estiver utilizando)*
* [MySQL](https://www.mysql.com/)

### Rodando a Aplicação

```bash
# Clone este repositório
$ git clone [https://github.com/VictorEduardo-coder/SystemRPG.git](https://github.com/VictorEduardo-coder/SystemRPG.git)

# Acesse a pasta do projeto no terminal/cmd
$ cd SystemRPG

# Configure o banco de dados
# 1. Crie um banco de dados no MySQL chamado `system_rpg`
# 2. Altere as credenciais no arquivo de configuração (ex: application.properties) com seu usuário e senha.

# Compile e execute a aplicação
# (Coloque aqui o comando exato que você usa, por exemplo: javac, ou ./mvnw spring-boot:run)
