# API Desafio biblioteca

A API foi desenvolvida para gerenciar o cadastro de livros e autores, bem como empréstimos. Seu contexto de desenvolvimento é o exercício e desafio
de boas práticas de programação orientada a objeto, bem como a aplicação das arquiteturas de mercado.
Boas práticas utilizando padrões de projeto, princípios SOLID e conceitos de Clean Code, além do desenvolvimento de testes unitários e automatizados utilizando JUnit e Mockito.



---

## 🛠️ Tecnologias Utilizadas

![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Docker Compose](https://img.shields.io/badge/Docker_Compose-384d54?style=for-the-badge&logo=docker&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct-FF6C37?style=for-the-badge&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit_5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-DB7093?style=for-the-badge&logo=mockito&logoColor=white)
![Testes Unitários](https://img.shields.io/badge/Testes_Unitários-6E5494?style=for-the-badge&logo=testrail&logoColor=white)
![Testes Integrados](https://img.shields.io/badge/Testes_Integrados-0078D6?style=for-the-badge&logo=selenium&logoColor=white)

---

## Pré-requisitos

- Docker instalado ([link](https://docs.docker.com/get-docker/))
- Docker Compose instalado (normalmente já vem com Docker Desktop)

---
## Sobre o Projeto


O projeto trata-se sistema de gerenciamento de biblioteca será composto por quatro módulos
principais: Autores, Livros, Locatários e Alugueis. Cada módulo terá
funcionalidades específicas para cadastro, atualização, exclusão, e listagem de
dados, seguindo as regras de negócio estabelecidas.

Nesse primeiro momento estamos entregando os módulos de Auto e Livros, já com os testes unitários.


O módulo **Autor** possui os seguintes atributos:
```
 id (Long)
 nome (String)
 data_de_Nascimento (LocalDate)
 cpf (String)
 sexo (enum)
 livros (list<Livros>)
```

O **id** é a chave primária que faz uso da estrategia **GenerationType.IDENTITY**, para persistir no banco de dados.
O **livros** é a chave estrangeira, relacionando-se com autos. Relacionalento **ManyToMany**.

O módulo **Livro** possui os seguintes atributos:

````
id (Long)
titulo (String)
isbn (String
publicacao (LocalDate)
autores (Set<autores>)
Categoria (enum)

````
O **id** é a chave primária que faz uso da estrategia **GenerationType.IDENTITY**, para persistir no banco de dados.
O **autores** é a chave estrangeira, relacionando-se com livros. Relacionalento **ManyToMany**.

---
## Do Banco de Dados

O projeto faz uso do **postgres**, que deve se instalado em um container no docker;

---

## Configuração do ambiente


O `docker-compose.yml` está na pasta docker na raiz do projeto, e configurado 
para usar as variáveis contidas no arquivo **.env**, conforme modelo a seguir:

Na raiz do projeto deve-se criar um arqivo .env, que deverá conter as seguites variáveis de ambiente:

````
POSTGRES_USER=teste123
POSTGRES_PASSWORD=teste123
POSTGRES_DB=db_biblioteca
DB_URL=jdbc:postgresql://postgres:5432/db_biblioteca
```` 

---

## Executando o projeto com Docker

No terminal, na raiz do projeto, execute:

```bash
docker-compose up --build
```

Isso vai:

- Construir a imagem da aplicação
- Subir os containers da aplicação e do banco PostgreSQL
- Configurar o banco com as variáveis do `.env`

A aplicação ficará disponível em: [http://localhost:8080](http://localhost:8080)

---

## Parando a execução

Para parar e remover os containers, execute:

```bash
docker-compose down
```
---

## Rodando localmente (sem Docker)

Se preferir rodar localmente, configure seu banco PostgreSQL e altere 
o arquivo `application.properties` ou `application.yml` do Spring Boot 
com as credenciais corretas.

---

## Testes

Realizei testes unitários utilizando JUnit e Mockito. Criei classes Fixitures para reutilização de código nos testes. Compreendo que testes são uma parte fundamental de uma aplicação.
````bash
        mvn test
````      
---

## Estrutura do projeto

- `src/main/java`: Código fonte da aplicação
- `src/main/resources`: Configurações e arquivos estáticos
- `docker-compose.yml`: Configuração do Docker Compose
- `.env`: Variáveis de ambiente (não versionar se conter dados sensíveis)
- `Dockerfile`: Dockerfile para a aplicação

---

## Considerações finais

- Certifique-se que as portas 8080 (app) e 5433 (Postgres) estão livres no seu sistema.
- Para limpar volumes e dados do banco, remova o volume `postgres-data` com:

```bash
docker volume rm postgres-data
```
---

##### Projeto no GitHub:  https://github.com/SauloHrodrigues/Desafio_Biblioteca.git

## Autor:

### Nome: Saulo Henrique Rodrigues

##### LinkedIn: https://www.linkedin.com/in/saulohenriquerodrigues/

##### Swagger: http://localhost:8080/swagger-ui.html