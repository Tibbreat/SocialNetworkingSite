package org.example.socialnetworkingsite.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(Collections.singletonList(new Server().url("http://localhost:8080")))
                .info(new Info().title("Social Networking Site")
                        .description("Social Networking Site by Trung Kiên")
                        .contact(new Contact()
                                .email("kiennt7902@gmail.com")
                                .name("Trung Kiên")
                                .url("https://www.facebook.com/truggkien"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                        .version("1.0.0"));
    }
}