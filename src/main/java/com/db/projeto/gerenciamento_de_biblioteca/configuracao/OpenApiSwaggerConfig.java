package com.db.projeto.gerenciamento_de_biblioteca.configuracao;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiSwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API -  Sistema de Gerenciamento\n" +
                                "de Biblioteca")
                        .version("v1")
                        .description(
                                "O sistema de gerenciamento de biblioteca será composto por quatro módulos\n" +
                                        "principais: Autores, Livros, Locatários e Alugueis. Cada módulo terá\n" +
                                        "funcionalidades específicas para cadastro, atualização, exclusão, e listagem de\n" +
                                        "dados, seguindo as regras de negócio estabelecidas"
                        )
                        .contact(new Contact()
                                .name("Saulo Henrique Rodrigues")
                                .email("saulo.rodrigues@db.tec.br"))
                )
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local")
                ));
    }
}