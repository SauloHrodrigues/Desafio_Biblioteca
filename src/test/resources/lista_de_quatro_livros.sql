SELECT 'Rodando lista_de_quatro_livros.sql' AS log;

INSERT INTO autores (id, nome, data_de_nascimento, cpf, sexo)
VALUES
    (1, 'Cecília Meireles', '1901-11-07', '12345678900', 'FEMININO'),
    (2, 'Jose Maria', '2000-02-10', '51752495098', 'MASCULINO'),
    (3, 'Machado de Assis', '1839-06-21', '98765432100', 'MASCULINO'),
    (4, 'Clarice Lispector', '1920-12-10', '84573912055', 'FEMININO');

INSERT INTO livros (id, titulo, data_publicacao, isbn, categoria_do_livro, status, aluguel_id)
VALUES
    (1, 'Ou Isto ou Aquilo', '1964-01-01', '9788572326971', 'POESIA', 0, NULL),
    (2, 'Casais Inteligentes Enriquecem Juntos', '2002-01-01', '9788535212664', 'ECONOMIA', 0, NULL),
    (3, 'Harry Potter e a Pedra Filosofal', '1997-06-26', '9788532511012', 'FANTASIA', 0, NULL),
    (4, '1984', '1949-06-08', '9780451524935', 'ROMANCE', 0, NULL);

INSERT INTO autores_livros (livro_id, autor_id) VALUES
    (1, 1),  -- Cecília Meireles
    (2, 2),  -- Jose Maria
    (3, 3),  -- Machado de Assis
    (4, 4);  -- Clarice Lispector