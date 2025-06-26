package hackerton.demo.util.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("네, 그게 칭찬입니다 API")
                .description("DEPth 해커톤 *오늘도 살아남으셨군요, 축하드립니다* 프로젝트 API 문서입니다.")
                .version("1.0.0");
    }
}