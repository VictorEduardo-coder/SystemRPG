CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    login VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo_conta VARCHAR(50) NOT NULL
);

CREATE TABLE classes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    bonus VARCHAR(255),
    debuff VARCHAR(255),
    eh_hibrida BOOLEAN,
    descricao TEXT,
    classe_base_id INT,
    classe_base2_id INT,
    FOREIGN KEY (classe_base_id) REFERENCES classes(id),
    FOREIGN KEY (classe_base2_id) REFERENCES classes(id)
);

CREATE TABLE fichas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    tipo_ficha VARCHAR(50) NOT NULL,
    nivel INT DEFAULT 1,
    xp_atual INT DEFAULT 0,
    xp_proximo INT DEFAULT 10,
    idade INT,
    classe VARCHAR(100),
    titulo VARCHAR(255),
    dinheiro_real DOUBLE,
    gold INT,
    tr INT,
    falha_nome VARCHAR(255),
    falha_efeito TEXT,
    forca INT,
    vida INT,
    agilidade INT,
    mana INT,
    carisma INT,
    sorte INT,
    seduzir INT,
    intimidar INT,
    estudar INT,
    investigar INT,
    politica INT,
    religiao INT,
    artes INT,
    furtividade INT,
    construir INT,
    saldo_atual DOUBLE,
    custo_vida DOUBLE,
    dias_restantes INT,
    nutricao VARCHAR(100),
    conexao VARCHAR(100),
    corrupcao INT DEFAULT 0,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE habilidades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    tier VARCHAR(50),
    dano VARCHAR(50),
    custo_mana INT,
    classe_id INT,
    FOREIGN KEY (classe_id) REFERENCES classes(id)
);

CREATE TABLE ficha_habilidades (
    ficha_id INT NOT NULL,
    habilidade_id INT NOT NULL,
    PRIMARY KEY (ficha_id, habilidade_id),
    FOREIGN KEY (ficha_id) REFERENCES fichas(id) ON DELETE CASCADE,
    FOREIGN KEY (habilidade_id) REFERENCES habilidades(id) ON DELETE CASCADE
);

CREATE TABLE inventario_rpg (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(100),
    raridade VARCHAR(50),
    quantidade INT,
    poder INT
);

CREATE TABLE manual_regras (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    categoria VARCHAR(100) NOT NULL
);