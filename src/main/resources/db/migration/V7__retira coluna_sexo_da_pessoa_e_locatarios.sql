CREATE TABLE autores (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    data_de_nascimento DATE NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    genero_da_pessoa  VARCHAR(20) NOT NULL
);

ALTER TABLE locatarios
    DROP COLUMN sexo;