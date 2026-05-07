package com.grupo5.cronoclase.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class OpenApiConfig {

     @Bean
    //esto es lo que crea todos los endpoints
    public OpenAPI adopcionOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API CronoClase")
                        .description("""
                                Backend para un sistema simple de programacion de evaluaciones.
                                
                                Permite ......
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("dev@adopcion.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
    

    

}
