package com.kenyahmis.dmiapi.config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfig {
    private static final String OAUTH_SCHEME = "auth";
    @Value("${spring.security.oauth2.authorizationserver.endpoint.token-uri}")
    String authURL;
    @Bean
    public OpenAPI myOpenAPI() {
        Info info = new Info()
                .title("DMI API")
                .version("1.0");
        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(OAUTH_SCHEME)
                )
                .components(new Components()
                        .addSecuritySchemes(OAUTH_SCHEME, createOAuthScheme()))
                .info(info);
    }

    private SecurityScheme createOAuthScheme() {
        return new SecurityScheme()
//                .type(SecurityScheme.Type.OAUTH2)
//                .flows(createOAuthFlows());
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

    }

    private OAuthFlows createOAuthFlows() {
        OAuthFlow clientCredentialsFlow = new OAuthFlow()
                .tokenUrl(authURL);
        return new OAuthFlows().clientCredentials(clientCredentialsFlow);
    }
}
