package eu.senla.dutov.config;

import eu.senla.dutov.util.ControllerConstantClass;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = ControllerConstantClass.BEARER_AUTH,
        type = SecuritySchemeType.HTTP, scheme = SwaggerConfig.BEARER, bearerFormat = SwaggerConfig.JWT)
@OpenAPIDefinition
public class SwaggerConfig {

    public static final String BEARER = "bearer";
    public static final String JWT = "JWT";
}
