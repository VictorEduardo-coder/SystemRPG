# 🎲 SystemRPG — Plataforma Web de Fichas e Regras de RPG

![Java 21](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.2.5-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL_%2F_TiDB_Cloud-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
[![CI](https://github.com/VictorEduardo-coder/SystemRPG/actions/workflows/ci.yml/badge.svg)](https://github.com/VictorEduardo-coder/SystemRPG/actions/workflows/ci.yml)

## Sobre o Projeto

O **SystemRPG** é uma aplicação web e backend desenvolvida em **Java 21 com Spring Boot** para gerenciar fichas, regras matemáticas, economia interna e rolagens em tempo real de um sistema autoral de Tabletop RPG (universo *Zero Life*).

O objetivo do projeto é substituir planilhas manuais por uma plataforma centralizada com separação de perfis (**Mestre** e **Jogador**), garantindo consistência nas regras de progressão, atributos, inventário, corrupção e habilidades.

---

## Prévia da Aplicação

<p align="center">
  <img src="./docs/images/systemrpg-status.webp" alt="Ficha de Personagem e Atributos" width="48%" />
  <img src="./docs/images/systemrpg-rolagens.webp" alt="Painel de Rolagens de Dados em Tempo Real" width="48%" />
</p>

<details>
<summary>Ver mais telas (Login, Criação de Ficha e Painel de Realidade)</summary>

<p align="center">
  <img src="./docs/images/systemrpg-login.webp" alt="Tela de Autenticação" width="48%" />
  <img src="./docs/images/systemrpg-criacao.webp" alt="Criação de Personagem" width="48%" />
</p>
<p align="center">
  <img src="./docs/images/systemrpg-realidade.webp" alt="Acompanhamento de Realidade e Recursos" width="76%" />
</p>

</details>

---

## Principais Funcionalidades

- **Autenticação e Perfis de Acesso:** Cadastro e login com **Spring Security** e senhas protegidas por hash **BCrypt**, diferenciando painéis de **Jogador** e **Mestre** (controle de NPCs, monstros e bosses).
- **Gerenciamento de Fichas e Classes:** Criação e edição de personagens com atributos, perícias, classes base/híbridas, evolução de nível/XP e associação de habilidades.
- **Rolagem de Dados em Tempo Real:** Integração via **WebSockets** para disparar e sincronizar rolagens entre participantes da sessão.
- **Sistema Dinâmico de Corrupção:** Controle de acúmulo e abatimento de corrupção com regras ligadas a eventos da campanha e evolução do personagem.
- **Motor Econômico e Inventário:** Gestão de moedas do sistema (`Gold`, `Tron` e saldo real), custo de vida e controle relacional de itens/equipamentos.
- **Manual de Regras Integrado:** Consulta organizada por categorias diretamente na interface web.

---

## Arquitetura e Estrutura

```text
SystemRPG/
├── zero/                          # Aplicação Maven / Spring Boot
│   ├── src/main/java/com/rpgsystem/zero/
│   │   ├── config/                # Segurança, CORS e WebSocket
│   │   ├── controller/            # Endpoints Web/REST (Login, Fichas, Mestre, Dados)
│   │   ├── database/              # Conexão JDBC e inicialização
│   │   ├── model/                 # Entidades do domínio (Ficha, Classe, Habilidade, ItemRPG, Corrupcao)
│   │   └── ...
│   ├── src/main/resources/
│   │   ├── templates/             # Views Thymeleaf (painel-player, painel-mestre, inventario, manual...)
│   │   ├── static/                # Estilos CSS e scripts do cliente
│   │   └── application.properties # Configuração parametrizada por variáveis de ambiente
│   ├── .env.example               # Modelo de variáveis de ambiente
│   └── pom.xml
├── schema.sql                     # Script de criação das tabelas MySQL
├── sys.csv                        # Dicionário de dados das tabelas e colunas
├── Dockerfile                     # Build multi-stage com Maven + Eclipse Temurin 21
├── render.yaml                    # Blueprint de deploy para Render
└── Documentacao_Sistema_RPG.md    # Documentação de regras e modelagem do sistema
```

---

## Tecnologias Utilizadas

- **Backend:** Java 21, Spring Boot 3.2.5, Spring Web, Spring Security (`BCrypt`), Spring Data JPA, Spring Mail
- **Tempo Real & Views:** Spring WebSocket, Thymeleaf, HTML5, CSS3, JavaScript
- **Banco de Dados:** MySQL (`mysql-connector-j`), compatível com **TiDB Cloud** via TLS
- **Build & Deploy:** Maven, Docker (multi-stage), Render (`render.yaml`)

---

## Segurança e Variáveis de Ambiente

- As senhas de usuários são armazenadas com hash **BCrypt**.
- O arquivo `zero/src/main/resources/application.properties` lê todas as credenciais a partir de **variáveis de ambiente** (`${DB_URL}`, `${DB_USERNAME}`, `${DB_PASSWORD}`, `${PORT}`), sem segredos fixos no código.
- O arquivo `.env` local é ignorado pelo Git (`.gitignore`). Utilize `zero/.env.example` apenas como modelo de referência.

| Variável | Descrição | Padrão |
| :--- | :--- | :--- |
| `PORT` | Porta HTTP do servidor Spring Boot | `4000` |
| `DB_URL` | String JDBC do banco MySQL / TiDB | `jdbc:mysql://localhost:3306/system_rpg` |
| `DB_USERNAME` | Usuário do banco de dados | `root` |
| `DB_PASSWORD` | Senha do banco de dados | *(vazio)* |
| `SMTP_EMAIL` / `SMTP_PASSWORD` | Credenciais SMTP para envio de e-mail | *(opcional)* |
| `SMTP_HOST` / `SMTP_PORT` | Host e porta SMTP | `smtp.gmail.com` / `587` |
| `ALLOWED_ORIGINS` | Origens permitidas no CORS | `http://localhost:4000` |

---

## Como Executar Localmente

### Pré-requisitos

- **Java JDK 21+**
- **Maven 3.9+** (ou o wrapper `./mvnw` incluído em `zero/`)
- **MySQL 8+** (ou instância compatível com MySQL/TiDB)

### 1. Prepare o banco de dados

Crie o banco `system_rpg` e importe o schema inicial:

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS system_rpg;"
mysql -u root -p system_rpg < schema.sql
```

### 2. Configure as variáveis de ambiente

Copie o arquivo de exemplo e preencha suas credenciais locais:

```bash
cp zero/.env.example zero/.env
```

Exporte as variáveis no terminal (ou carregue pelo seu IDE/ambiente):

```bash
export DB_URL="jdbc:mysql://localhost:3306/system_rpg"
export DB_USERNAME="root"
export DB_PASSWORD="sua_senha"
export PORT=4000
```

### 3. Execute a aplicação

```bash
cd zero
./mvnw spring-boot:run
```

A aplicação ficará disponível em **`http://localhost:4000`** (ou na porta definida na variável `PORT`).

### Executar com Docker

```bash
docker build -t systemrpg .
docker run --rm -p 4000:4000 \
  -e PORT=4000 \
  -e DB_URL="jdbc:mysql://host.docker.internal:3306/system_rpg" \
  -e DB_USERNAME="root" \
  -e DB_PASSWORD="sua_senha" \
  systemrpg
```

---

## Principais Rotas e Endpoints

| Tipo | Método e Rota | Autenticação | Responsabilidade |
| :--- | :--- | :---: | :--- |
| **Web (Thymeleaf)** | `GET /`, `/index`, `/login`, `GET/POST /cadastro` | Pública | Login, redirecionamento inicial e criação de conta |
| **Web (Jogador)** | `GET /painel-player`, `GET /fichas/editar`, `POST /fichas/criar`, `POST /fichas/salvar`, `POST /fichas/deletar` | Obrigatória | Painel do jogador e CRUD de fichas |
| **Web (Mestre)** | `GET /painel-mestre`, `/painel-mestre/npcs`, `/painel-mestre/monstros`, `/painel-mestre/boss` | Obrigatória | Gestão da campanha, NPCs, monstros, bosses e log ao vivo |
| **Web (Compêndio)** | `GET /Manual`, `POST /Manual/criar`, `GET /habilidades`, `POST /classes/salvar`, `POST /habilidades/salvar` | Obrigatória | Manual de regras, classes base/híbridas e habilidades |
| **API REST** | `POST /api/dados/rolar` | Obrigatória | Calcula rolagem (`2d6+3`) e publica no tópico WebSocket `/topic/mestre` |
| **API REST** | `GET /api/inventario?fichaId=`, `POST /api/inventario/adicionar`, `PUT /api/inventario/atualizar/{id}`, `DELETE /api/inventario/deletar/{id}` | Obrigatória | CRUD de itens da mochila por ficha |

---

## Modelagem do Banco de Dados (`schema.sql`)

```mermaid
erDiagram
    usuarios ||--o{ fichas : possui
    classes ||--o{ habilidades : define
    classes ||--o{ classes : "herda (classe_base)"
    fichas ||--o{ ficha_habilidades : vincula
    habilidades ||--o{ ficha_habilidades : associada

    usuarios {
        int id PK
        varchar nome
        varchar login UK
        varchar senha
        varchar tipo_conta
    }
    fichas {
        int id PK
        int usuario_id FK
        varchar nome
        varchar tipo_ficha
        int nivel
        int gold
        int tr
        int corrupcao
    }
    classes {
        int id PK
        varchar nome
        boolean eh_hibrida
        int classe_base_id FK
        int classe_base2_id FK
    }
    habilidades {
        int id PK
        varchar nome
        varchar tier
        int custo_mana
        int classe_id FK
    }
    inventario_rpg {
        bigint id PK
        varchar nome
        varchar tipo
        varchar raridade
        int quantidade
        int poder
    }
    manual_regras {
        int id PK
        varchar titulo
        varchar categoria
    }
```

---

## Testes Automatizados e CI

A suíte de testes usa **JUnit 5**, **Mockito**, **Spring Security Test** e **H2 em memória (modo MySQL)** — rodando sem depender de banco externo:

- `ZeroApplicationTests`: valida bootstrap do contexto Spring e configuração do `BCryptPasswordEncoder`.
- `DadoServiceTest`: testa rolagem com modificadores positivos/negativos, notação reduzida (`d20`), entradas vazias e rejeição de limites inválidos (`0d20`, `101d6`, `1d10001`).
- `CorrupcaoAndFichaTest`: valida todos os 7 estágios de corrupção, atributos derivados da `Ficha` e a matriz de permissões RBAC de `PerfilAcesso`.
- `DadoAndSecurityControllerTest` (`MockMvc`): valida rotas públicas, redirecionamento de acessos anônimos para `/index`, rolagem autenticada com envio ao tópico `/topic/mestre` e validação HTTP `400` no inventário.

```bash
cd zero
./mvnw test
```

O workflow `.github/workflows/ci.yml` executa a compilação e toda a suíte automaticamente no **GitHub Actions** (JDK 21).

---

## Limitações Conhecidas e Próximos Passos

- Reativar proteção **CSRF** com injeção explícita de tokens nos formulários Thymeleaf e requisições `fetch()`.
- Substituir parâmetros de query (`?usuarioId=`) pela resolução exclusiva do usuário autenticado via `Principal` / `SecurityContext` em todos os controllers.
- Expandir a camada `Service` e DTOs para reduzir acoplamento direto entre controllers e DAOs JDBC.
- Publicar documentação interativa OpenAPI/Swagger para os endpoints `/api/**`.

---

## Licença

Distribuído sob a licença **MIT**. Consulte o arquivo [LICENSE](./LICENSE) para mais detalhes.
