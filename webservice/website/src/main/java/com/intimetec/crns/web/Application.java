/**
 * @author bincy.samuel
 *
 */
package com.intimetec.crns.web;

import static springfox.documentation.builders.PathSelectors.regex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import com.google.common.base.Predicate;
import com.intimetec.crns.web.controller.DefaultController;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger1.annotations.EnableSwagger;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger // Enable swagger 1.2 spec
@EnableSwagger2 // Enable swagger 2.0 spec
@ComponentScan(basePackageClasses = { DefaultController.class })
@PropertySources({ @PropertySource(value = "classpath:/config/override.properties", name = "override1"),
		@PropertySource(value = "classpath:/environments/${environment.active}.properties"),
		@PropertySource(value = "classpath:/config/override.properties", name = "override2") })

/**
 * The override.properties file is intentionally loaded twice. - The first time
 * is needed because it contains the value for environment.active, which is
 * needed to load the next properties file - The second time is needed so it
 * will override values in the environment properties file
 * 
 * Each instance of override.properties must have an explicit (and unique) name.
 * Without this, Spring with assign them both the same auto-generated value, and
 * only the first instance will be loaded, meaning override.properties would
 * override the core properties, but not the site and environment values.
 * 
 * See the following for PropertySource loading logic, especially related to
 * placeholder values.
 * 
 * http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/
 * context/annotation/PropertySource.html
 */
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Docket categoryApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("default-api").apiInfo(apiInfo()).select()
				.paths(categoryPaths()).build().ignoredParameterTypes(ApiIgnore.class).enableUrlTemplating(true);
	}

	private Predicate<String> categoryPaths() {
		return regex("/*");
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("CRNS REST API")
				.description(
						"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.")
				.termsOfServiceUrl("http://springfox.io").contact("XYZ")
				.license("XYZ").licenseUrl("http:abc.com").version("2.0").build();
	}

}