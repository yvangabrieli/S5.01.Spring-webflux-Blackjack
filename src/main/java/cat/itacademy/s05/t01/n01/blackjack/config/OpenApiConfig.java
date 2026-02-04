package cat.itacademy.s05.t01.n01.blackjack.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;

public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blackjack API")
                        .version("1.0")
                        .description("API for a Blackjack game"));
    }
}
