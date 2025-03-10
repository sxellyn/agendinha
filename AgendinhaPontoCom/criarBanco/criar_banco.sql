-- Criar o banco de dados
CREATE DATABASE IF NOT EXISTS agendinha;

-- Usar o banco de dados
USE agendinha;

-- Criar tabela de usuários
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(50) NOT NULL,  -- Reduzido o tamanho já que não será mais criptografada
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de contatos
CREATE TABLE IF NOT EXISTS contato (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(255),
    usuario_id BIGINT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Criar índices para melhor performance
CREATE INDEX idx_usuario_email ON usuario(email);
CREATE INDEX idx_contato_usuario ON contato(usuario_id);
CREATE INDEX idx_contato_nome ON contato(nome);

-- Garantir permissões
GRANT ALL PRIVILEGES ON agendinha.* TO 'root'@'localhost';
FLUSH PRIVILEGES;

-- Inserir um usuário de teste (senha em texto puro)
INSERT INTO usuario (nome, email, senha) VALUES 
('Usuário Teste', 'teste@teste.com', 'teste123'); 