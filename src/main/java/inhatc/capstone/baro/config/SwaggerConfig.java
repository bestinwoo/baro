package inhatc.capstone.baro.config;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Baro API", version = "1.0", description = "바로 API 명세서"))
public class SwaggerConfig {
	@Bean
	public GroupedOpenApi securityGroupOpenApi() {
		return GroupedOpenApi
			.builder()
			.group("Security Open Api")
			.pathsToExclude("/token/*")
			.addOpenApiCustomiser(buildSecurityOpenApi())
			.build();
	}

	@Bean
	public GroupedOpenApi NonSecurityGroupOpenApi() {
		return GroupedOpenApi
			.builder()
			.group("Non Security Open Api")
			.pathsToMatch("/token/*")
			.build();
	}

	public OpenApiCustomiser buildSecurityOpenApi() {
		SecurityScheme securityScheme = new SecurityScheme()
			.name("Authorization")
			.type(SecurityScheme.Type.HTTP)
			.in(SecurityScheme.In.HEADER)
			.bearerFormat("JWT")
			.scheme("bearer");

		return OpenApi -> OpenApi
			.addSecurityItem(new SecurityRequirement().addList("jwt token"))
			.getComponents().addSecuritySchemes("jwt token", securityScheme);
	}
}
