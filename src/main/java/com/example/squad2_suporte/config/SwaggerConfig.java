package com.example.squad2_suporte.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customAPI(){
        return new OpenAPI().info(new Info().title("API de Envio de Amostras").version("1.0.0")
                .description("Projeto desenvolvido pelo Squad 2 para a Fundação de Saúde Parreiras Horta (FSPH).\n" +
                        "Esta API gerencia o cadastro, envio e controle de amostras biológicas enviadas pelas prefeituras para o LACEN/SE."));

    }

}

