CREATE TABLE autores (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    data_de_nascimento DATE NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    sexo VARCHAR(20) NOT NULL
);

CREATE TABLE locatarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    data_de_nascimento DATE NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    sexo VARCHAR(20) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(150) UNIQUE NOT NULL
);

CREATE TABLE alugueis (
    id BIGSERIAL PRIMARY KEY,
    retirada DATE NOT NULL,
    devolucao DATE,
    status VARCHAR(30) NOT NULL,
    locatario_id BIGINT NOT NULL,
    CONSTRAINT fk_aluguel_locatario
        FOREIGN KEY (locatario_id) REFERENCES locatarios(id) ON DELETE CASCADE
);

CREATE TABLE livros (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    publicacao DATE,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    genero VARCHAR(100),
    aluguel_id BIGINT,
    CONSTRAINT fk_livro_aluguel
        FOREIGN KEY (aluguel_id) REFERENCES alugueis(id) ON DELETE SET NULL
);

CREATE TABLE livro_autor (
    livro_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,
    PRIMARY KEY (livro_id, autor_id),
    CONSTRAINT fk_livro_autor_livro
        FOREIGN KEY (livro_id) REFERENCES livros(id) ON DELETE CASCADE,
    CONSTRAINT fk_livro_autor_autor
        FOREIGN KEY (autor_id) REFERENCES autores(id) ON DELETE CASCADE
);
