package com.kenyahmis.dmiapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Info info = new Info()
                .title("DMI API")
                .version("1.0");
        return new OpenAPI()
                .info(info);
    }
}
