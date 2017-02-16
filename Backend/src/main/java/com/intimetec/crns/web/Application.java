package com.intimetec.crns.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
	@PropertySource(value="classpath:/config/override.properties", name="override1"),
	@PropertySource(value="classpath:/environments/${environment.active}.properties"),
	@PropertySource(value="classpath:/config/override.properties", name="override2")
})

/**
 * The override.properties file is intentionally loaded twice.
 * 	- The first time is needed because it contains the value for environment.active, which is needed to load the next properties file
 * 	- The second time is needed so it will override values in the environment properties file
 * 
 * Each instance of override.properties must have an explicit (and unique) name.  Without this, Spring with assign them both the same auto-generated value, and
 * only the first instance will be loaded, meaning override.properties would override the core properties, but not the site and environment values.  
 * 
 * See the following for PropertySource loading logic, especially related to placeholder values.
 * 
 * http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/PropertySource.html
 */
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}