package com.intimetec.crns.web;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.SocketUtils;

import com.intimetec.crns.core.config.MailConfig;
import com.intimetec.crns.core.config.google.GoogleApiConfig;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger1.annotations.EnableSwagger;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author shiva.dixit
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.intimetec.crns"})
@EnableAutoConfiguration
@EnableSwagger // Enable swagger 1.2 spec
@EnableSwagger2 // Enable swagger 2.0 spec
@PropertySources({ @PropertySource(value = "classpath:/core/application.properties", name = "application"),
		@PropertySource(value = "classpath:/config/override.properties", name = "override1"),
		@PropertySource(value = "classpath:/environments/${environment.active}.properties"),
		@PropertySource(value = "classpath:/config/override.properties", name = "override2") })
@EnableJpaRepositories("com.intimetec.crns.core.repository")
@EntityScan("com.intimetec.crns.core.models") 
@EnableAsync
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
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	@Value("${google.api.url}")
	private String googleApiUrl;
	
	@Value("${google.api.apikey}")
	private String googleApiKey;
	
	@Value("${spring.mail.host}")
	private String mailHost;
	
	@Value("${spring.mail.port}")
	private String mailPort;
	
	@Value("${spring.mail.username}")
	private String mailUserName;
	
	@Value("${spring.mail.password}")
	private String mailPassword;
	
	@Bean
	public GoogleApiConfig getGoogleApiConfig(){
		return new GoogleApiConfig(googleApiUrl, googleApiKey);
	}
	
	@Bean
	public MailConfig getMailConfig(){
		return new MailConfig(mailHost, mailPort, mailUserName, mailPassword);
	}

	public static void main(String[] args) throws Exception {
		LOGGER.debug("Admin credentials: demo.crns@gmail.com/crnsadmin ["+new BCryptPasswordEncoder().encode("crnsadmin")+"]");
		LOGGER.debug("User credentials: crns.demouser@gmail.com/crnsuser ["+new BCryptPasswordEncoder().encode("crnsuser")+"]");
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public Docket api() {
	    return new Docket(DocumentationType.SWAGGER_2).select()
	            .apis(RequestHandlerSelectors.any())
	            .paths(PathSelectors.any())
	            .build()
	            .apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
	    ApiInfo apiInfo = new ApiInfo("California Residents Notification Service", "Description of APIs.", "API TOS", "Terms of service", new Contact("InTimeTec", "http://intimetec.com/", "shiva.dixit@intimetec.com"), "License of API", "API license URL");
	    return apiInfo;
	}
	/*@Bean
	public Integer port() {
		return SocketUtils.findAvailableTcpPort();
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		tomcat.addAdditionalTomcatConnectors(createStandardConnector());
		return tomcat;
	}

	private Connector createStandardConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setPort(port());
		return connector;
	}*/
	
	
}