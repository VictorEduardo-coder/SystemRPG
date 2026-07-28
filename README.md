# 🎲 SystemRPG - TTRPG Backend API

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
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
* **Framework:** Spring Boot 3.2.5
* **Frontend:** Thymeleaf + JavaScript (Dado rolling via WebSocket)
* **Segurança:** Spring Security + BCrypt Password Encoding

## 🔐 Segurança

* Senhas armazenadas com **BCrypt** hash
* Credenciais de banco de dados e SMTP carregadas via **variáveis de ambiente**
* `.env` e `application.properties` estão no `.gitignore`
* Nunca commite credenciais reais no repositório

## 🚀 Como executar este projeto

### Pré-requisitos
Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
* [Git](https://git-scm.com)
* [Java JDK 21+](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)
* [Maven](https://maven.apache.org/)
* [MySQL](https://www.mysql.com/)

### Configuração Local

1. Clone este repositório
2. Copie o arquivo `.env.example` para `.env` e preencha as credenciais:
   ```
   DB_URL=jdbc:mysql://localhost:3306/system_rpg
   DB_USERNAME=root
   DB_PASSWORD=sua_senha
   SMTP_EMAIL=seu_email@gmail.com
   SMTP_PASSWORD=sua_senha_app_gmail
   SMTP_HOST=smtp.gmail.com
   SMTP_PORT=587
   ```
3. Configure o banco de dados MySQL com o nome `system_rpg`
4. Compile e execute:
   ```bash
   cd zero
   ./mvnw spring-boot:run
   ```

### Executar com Maven

```bash
cd zero
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

## 🌐 Deploy na Vercel

> **Nota:** A Vercel não suporta aplicações Spring Boot nativamente. Para fazer deploy desta API, considere as seguintes alternativas:
> - **[Railway](https://railway.app)** — Suporte nativo para Java/Spring Boot
> **[Render](https://render.com)** — Suporte para Java com Docker
> - **[Google Cloud Run](https://cloud.google.com/run)** — Container-based deployment
> - **[Fly.io](https://fly.io)** — Suporte para Java via Docker
>
> Se ainda assim desejar usar a Vercel, é possível empacotar a aplicação como um container Docker e usar o [Vercel Docker Deploy](https://vercel.com/docs/concepts/projects/docker), ou utilizar a Vercel apenas para o frontend estático (Thymeleaf templates compilados como assets) e a API em um serviço separado.

### Variáveis de Ambiente para Deploy

Configure as seguintes variáveis no painel do serviço de deploy:

| Variável | Descrição | Exemplo |
|----------|-----------|---------|
| `DB_URL` | URL de conexão MySQL | `jdbc:mysql://host:port/db` |
| `DB_USERNAME` | Usuário do banco | `root` |
| `DB_PASSWORD` | Senha do banco | `sua_senha` |
| `SMTP_EMAIL` | Email para envio de logs | `email@gmail.com` |
| `SMTP_PASSWORD` | Senha app do email | `senha_app` |
| `SMTP_HOST` | Host SMTP | `smtp.gmail.com` |
| `SMTP_PORT` | Porta SMTP | `587` |
| `ALLOWED_ORIGINS` | Origens CORS permitidas | `http://localhost:3000` |
