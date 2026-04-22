package com.grupo5.cronoclase.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI cronoclaseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CronoClase API - Grupo 5")
                        .description(
                            "API REST para la gestión de cursos, evaluaciones, entregas y estudiantes. " +
                            "Incluye lógica de negocio para el cálculo automático del estado de entrega " +
                            "(PENDIENTE, ENTREGADO, TARDE, CALIFICADO) según las fechas establecidas."
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Grupo 5")
                                .email("grupo5@cronoclase.com"))
                        .license(new License()
                                .name("MIT License")));
    }
}
