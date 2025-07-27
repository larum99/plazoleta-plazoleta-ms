package com.plazoleta.plazoleta.commons.configurations.swagger.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfigSwagger {

    @Bean
    public OpenAPI plazoletaServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Plazoleta - Restaurant Service API")
                        .description("Microservicio de gestión de restaurantes, menús y platos para la plazoleta de comidas.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo Plazoleta")
                                .email("soporte@plazoleta.com")
                                .url("https://plazoleta.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación completa")
                        .url("https://plazoleta.com/docs"));
    }
}
