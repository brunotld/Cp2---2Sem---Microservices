-- Script de criacao do banco e das tabelas utilizadas pela aplicacao
-- no profile "prd" (onde a criacao automatica pelo Hibernate esta desativada).
--
-- Uso:
--   mysql -h <host> -P <porta> -u <usuario> -p < database/schema.sql
--
-- Ajuste o nome do schema (CINEMA_DB) conforme a variavel de ambiente DB_SCHEMA.

CREATE DATABASE IF NOT EXISTS cinema_db;
USE cinema_db;

CREATE TABLE IF NOT EXISTS filmes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    genero VARCHAR(255) NOT NULL,
    duracao_minutos INT NOT NULL,
    classificacao_etaria VARCHAR(255) NOT NULL,
    sinopse VARCHAR(1000) NULL
);

CREATE TABLE IF NOT EXISTS salas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    capacidade INT NOT NULL,
    tres_d BOOLEAN NOT NULL,
    observacao VARCHAR(1000) NULL
);
