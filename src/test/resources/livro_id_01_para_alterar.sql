SELECT 'Rodando livro_id_01_para_alterar.sql' AS log;

INSERT INTO autores (id, nome, data_de_nascimento, cpf, sexo)
VALUES
    (1, 'Cecília Meireles', '1901-11-07', '12345678900', 'FEMININO');

INSERT INTO livros (id, titulo, data_publicacao, isbn, categoria_do_livro, status, aluguel_id)
VALUES
    (1, 'Ou Isto ou Aquilo', '1964-01-01', '9788572326971', 'POESIA', 0, NULL);

INSERT INTO autores_livros (autor_id, livro_id)
VALUES
    (1, 1);
