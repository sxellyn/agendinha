CREATE DATABASE IF NOT EXISTS agendinha;

USE agendinha;

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

-- Tabela de endereços
CREATE TABLE IF NOT EXISTS endereco (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rua VARCHAR(255),
    numero VARCHAR(20),
    complemento VARCHAR(255),
    bairro VARCHAR(255),
    cidade VARCHAR(255),
    estado VARCHAR(255),
    cep VARCHAR(10)
);

-- Tabela de contatos
CREATE TABLE IF NOT EXISTS contato (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    endereco_id BIGINT,
    usuario_id BIGINT NOT NULL,
    FOREIGN KEY (endereco_id) REFERENCES endereco(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Tabela de telefones
CREATE TABLE IF NOT EXISTS telefone (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(20),
    contato_id BIGINT,
    FOREIGN KEY (contato_id) REFERENCES contato(id) ON DELETE CASCADE
);

-- Índices para melhor performance
CREATE INDEX idx_usuario_email ON usuario(email);
CREATE INDEX idx_contato_usuario ON contato(usuario_id);
CREATE INDEX idx_contato_nome ON contato(nome);
CREATE INDEX idx_telefone_contato ON telefone(contato_id);

-- Garantir privilégios
GRANT ALL PRIVILEGES ON agendinha.* TO 'root'@'localhost';
FLUSH PRIVILEGES;