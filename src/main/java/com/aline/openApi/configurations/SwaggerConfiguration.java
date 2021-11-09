package com.aline.openApi.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import org.springdoc.webmvc.ui.SwaggerConfig;
@OpenAPIDefinition(
        info = @Info(
                title = "API para estudos do OpenApi",
                description = "Essa api é para estudos do OpenApi",
                version = "1.000",
                license = @License(name = "Aline 2.0", url = "http://aline.com.br"),
                contact = @Contact(name = "aline", url = "aline.com.br", email = "aline@aline.com.br")
        )/*,
        servers = @Server(
                url = "/openApi/v1",
                description = "Servidor",
                variables = {
                        @ServerVariable(name = "serverUrl", defaultValue = "localhost"),
                        @ServerVariable(name = "serverHttpPort", defaultValue = "8081")
                })*/)
public class SwaggerConfiguration extends SwaggerConfig {

}