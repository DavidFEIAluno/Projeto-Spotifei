# Spotifei

**Spotifei** é uma plataforma de informações sobre músicas que permite aos usuários realizar buscas, curtir faixas, gerenciar playlists e manter um histórico de atividades. Desenvolvido com Java (Swing) e PostgreSQL, o projeto segue o padrão de arquitetura **MVC** (Model-View-Controller).

### Autores
Nome: David Ivaldi Elhain

RA: 24.124.009-2

Curso: Ciências da Computação

Demonstração (Vídeo)
https://youtu.be/HvAnRmAbJZU?si=G6FUKNnlaPlo5sQm

---
Link do Git: https://github.com/DavidFEIAluno/Projeto-Spotifei.git
## Índice

- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Funcionalidades Principais](#funcionalidades-principais)
- [Modelagem do Banco de Dados](#modelagem-do-banco-de-dados)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Padrão de Projeto (MVC)](#padrão-de-projeto-mvc)
- [Como Executar o Projeto](#como-executar-o-projeto)
- [Demonstração (Vídeo)](#demonstração-vídeo)
- [Autores](#autores)

---

## Tecnologias Utilizadas

- **Linguagem:** Java SE (JDK 17)
- **Interface Gráfica:** Java Swing (NetBeans)
- **Banco de Dados:** PostgreSQL
- **Conexão com BD:** JDBC (Java Database Connectivity)
- **Organização:** Arquitetura MVC
- **IDE:** NetBeans
- **Controle de Versão:** Git e GitHub

---

## Funcionalidades Principais

### Cadastro e Login
- Cadastro de usuários com nome, e-mail e senha
- Login com validação de credenciais

### Busca de Músicas
- Busca por nome, artista ou gênero
- Resultados exibidos em tabelas

### Curtidas
- Curtir ou descurtir músicas
- Visualização das músicas curtidas/descurtidas

### Histórico
- Registro automático das últimas 10 músicas buscadas
- Exibição do histórico por ordem cronológica

### Playlists
- Criação de playlists personalizadas
- Adição e remoção de músicas
- Edição do nome da playlist
- Exclusão de playlists

---

## Modelagem do Banco de Dados

### Principais Tabelas

- **artistas**: armazena nome, id, genero
- **usuarios**: armazena nome, email, senha
- **musicas**: armazena nome, artista e gênero
- **playlists**: vinculadas a um usuário
- **playlist_musicas**: músicas associadas às playlists
- **historico_busca**: registro de buscas dos usuários
- **curtidas**: registros de curtidas e descurtidas

### Exemplo de Relacionamentos

```text
usuarios (1) ─── (n) playlists
usuarios (1) ─── (n) historico_busca
usuarios (1) ─── (n) curtidas
playlists (1) ─── (n) playlist_musicas
musicas (1) ─── (n) playlist_musicas

--

### Padrão de Projeto (MVC)
O projeto utiliza a arquitetura MVC, separando claramente:

Model (Modelos): Representa os dados e lógica de negócio

View (Telas): Interfaces gráficas com Swing (NetBeans GUI)

Controller: Faz a ponte entre View e Model, manipulando ações do usuário

--

# Como Executar o Projeto:

1. Pré-requisitos
Java JDK instalado (recomenda-se JDK 17)

PostgreSQL instalado e rodando

NetBeans IDE

Banco de dados criado com nome spotifei

2. Configurar Banco de Dados
Execute os comandos abaixo no PostgreSQL:

-- Tabela de usuários
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de artistas
CREATE TABLE artistas (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    genero VARCHAR(50)
);

-- Tabela de músicas
CREATE TABLE musicas (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    artista_id INTEGER REFERENCES artistas(id),
    genero VARCHAR(50),
    duracao INTEGER, -- em segundos
    data_lancamento DATE
);

-- Tabela de playlists
CREATE TABLE playlists (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER REFERENCES usuarios(id),
    nome VARCHAR(100) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de músicas nas playlists
CREATE TABLE playlist_musicas (
    playlist_id INTEGER REFERENCES playlists(id),
    musica_id INTEGER REFERENCES musicas(id),
    ordem INTEGER,
    PRIMARY KEY (playlist_id, musica_id)
);

-- Tabela de curtidas
CREATE TABLE curtidas (
    usuario_id INTEGER REFERENCES usuarios(id),
    musica_id INTEGER REFERENCES musicas(id),
    acao BOOLEAN, -- true para curtir, false para descurtir
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (usuario_id, musica_id)
);

-- Tabela de histórico de buscas
CREATE TABLE historico_buscas (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER REFERENCES usuarios(id),
    termo_busca VARCHAR(100) NOT NULL,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    acao VARCHAR(100)
);

Adicione Musicas e Artistas:

INSERT INTO artistas (nome, genero) VALUES
('The Beatles', 'Rock'),
('Adele', 'Pop'),
('Ludwig van Beethoven', 'Clássico'),
('Eminem', 'Rap'),
('Anitta', 'Funk');

INSERT INTO musicas (titulo, artista_id, genero, duracao, data_lancamento) VALUES
('Hey Jude', 1, 'Rock', 431, '1968-08-26'),
('Rolling in the Deep', 2, 'Pop', 228, '2010-11-29'),
('Symphony No. 5', 3, 'Clássico', 425, '1808-12-22'),
('Lose Yourself', 4, 'Rap', 326, '2002-10-28'),
('Envolver', 5, 'Funk', 203, '2021-11-11');

3. Rodar o Projeto
Abra no NetBeans

Execute TelaLogin como programa principal

Faça login ou cadastre-se e comece a explorar o Spotifei!
