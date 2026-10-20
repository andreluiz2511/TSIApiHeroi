
package com.andre.apiheroi.configuracoes;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiOAuthProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springdoc.webmvc.ui.SwaggerIndexPageTransformer;
import org.springdoc.webmvc.ui.SwaggerWelcomeCommon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configSwagger {


    @Bean
    public OpenAPI customOpenAPI(
            @Value("${springdoc.version:1.0.0}") String appVersion) {

        String descricao = """
            Central de gerenciamento de heróis, poderes, fraquezas,
            perfis e equipes.

            **[O que posso fazer com esta API?](/ajuda.html)**
            """;

        return new OpenAPI()
                .info(new Info()
                        .title("HeroVerse — Universo de Heróis")
                        .version(appVersion)
                        .description(descricao)
                        .license(new License()
                                .name("MIT")
                                .url("https://mit-license.org/"))
                );
    }


    @Bean
    public SwaggerIndexPageTransformer indexPageTransformer(
            SwaggerUiConfigProperties swaggerUiConfig,
            SwaggerUiOAuthProperties swaggerUiOAuthProperties,
            SwaggerWelcomeCommon swaggerWelcomeCommon,
            ObjectMapperProvider objectMapperProvider) {

        return new SwaggerIndexPageTransformer(
                swaggerUiConfig,
                swaggerUiOAuthProperties,
                swaggerWelcomeCommon,
                objectMapperProvider) {

            @Override
            protected String addParameters(
                    String html,
                    SwaggerUiConfigParameters parameters)
                    throws JsonProcessingException {

                String pagina = super.addParameters(html, parameters);

                return pagina.replace(
                        "layout: \"StandaloneLayout\"",
                        "customCssUrl: \"/swagger-herois.css\",\n"
                                + "layout: \"StandaloneLayout\"");
            }
        };
    }
}
