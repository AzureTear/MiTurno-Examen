// src/main/java/com/MiTurno/MiTurno/config/SwaggerConfig.java
package com.MiTurno.MiTurno.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI miTurnoOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("MiTurno API")
                .version("1.0")
                .description("API REST para el sistema de gestión de turnos MiTurno"))
            .addServersItem(new Server()
                .url("http://localhost:8080")
                .description("Documentación de la API de MiTurno"))
            // Etiquetas para agrupar controladores
            .addTagsItem(new Tag().name("Usuario").description("Operaciones sobre usuarios"))
            .addTagsItem(new Tag().name("Configuración").description("Operaciones sobre parámetros de configuración"))
            .addTagsItem(new Tag().name("Institución").description("Operaciones sobre instituciones"))
            .addTagsItem(new Tag().name("Módulo").description("Operaciones sobre módulos"))
            .addTagsItem(new Tag().name("Rol").description("Operaciones sobre roles de usuario"))
            .addTagsItem(new Tag().name("Sucursal").description("Operaciones sobre sucursales"))
            .addTagsItem(new Tag().name("Trabajador").description("Operaciones sobre trabajadores"))
            .addTagsItem(new Tag().name("Turno").description("Operaciones sobre turnos"));
    }
}
