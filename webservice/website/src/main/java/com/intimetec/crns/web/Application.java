package com.intimetec.crns.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.intimetec.crns.core.config.MailConfig;
import com.intimetec.crns.core.config.fcm.FcmConfig;
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
 * @author In Time Tec
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.intimetec.crns"})
@EnableAutoConfiguration
@EnableSwagger // Enable swagger 1.2 spec
@EnableSwagger2 // Enable swagger 2.0 spec
@PropertySources({ @PropertySource(value = 
"classpath:/core/application.properties", name = "application"),
		@PropertySource(value = "classpath:/config/override.properties", 
		name = "override1"),
		@PropertySource(value = "classpath:/environments/"
				+ "${environment.active}.properties"),
		@PropertySource(value = "classpath:/config/"
				+ "override.properties", name = "override2") })
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
	/**
	 * To log the application messages. 
	 */
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(Application.class);
	
	/**
	 * URL of the Google API. 
	 */
	@Value("${google.api.url}")
	private String googleApiUrl;
	
	/**
	 * Key of the Google API . 
	 */
	@Value("${google.api.apikey}")
	private String googleApiKey;
	
	/**
	 * URL of the FCM API. 
	 */
	@Value("${fcm.api.url}")
	private String fcmApiUrl;
	
	/**
	 * URL of the FCM API. 
	 */
	@Value("${fcm.api.key}")
	private String fcmApiKey;
	
	/**
	 * URL of the FCM API. 
	 */
	@Value("${fcm.api.projectKey}")
	private String fcmApiProjectKey;
	
	/**
	 * The mail host. 
	 */
	@Value("${spring.mail.host}")
	private String mailHost;
	
	/**
	 * Port of the Mail. 
	 */
	@Value("${spring.mail.port}")
	private String mailPort;
	
	/**
	 * User name of the mail. 
	 */
	@Value("${spring.mail.username}")
	private String mailUserName;
	
	/**
	 * Password of the mail. 
	 */
	@Value("${spring.mail.password}")
	private String mailPassword;
	
	/**
	 * URL of the FCM API. 
	 */
	@Value("${weather.alerts.url}")
	private String weatherAlertsUrl;
	
	/**
	 * @return configurations of the NOAA Weather Alerts API.
	 */
	@Bean
	public String getWeatherAlertsUrl() {
		return weatherAlertsUrl;
	}
	
	/**
	 * @return configurations of the Google API.
	 */
	@Bean
	public GoogleApiConfig getGoogleApiConfig() {
		return new GoogleApiConfig(googleApiUrl, googleApiKey);
	}
	
	/**
	 * @return configurations of the FCM API.
	 */
	@Bean
	public FcmConfig getFcmApiConfig() {
		return new FcmConfig(fcmApiUrl, fcmApiKey, fcmApiProjectKey);
	}
	
	/**
	 * @return the mail configurations.
	 */
	@Bean
	public MailConfig getMailConfig() {
		return new MailConfig(mailHost, mailPort, mailUserName, mailPassword);
	}

	/**
	 * @param args       the argument.
	 * @throws Exception If any exception occurred.
	 */
	public static void main(final String[] args) throws Exception {
		LOGGER.debug("Admin credentials: demo.crns@gmail.com/crnsadmin "
				+ "[" + new BCryptPasswordEncoder().encode("crnsadmin") + "]");
		LOGGER.debug("User credentials: crns.demouser@gmail.com/crnsuser "
				+ "[" + new BCryptPasswordEncoder().encode("crnsuser") + "]");
		SpringApplication.run(Application.class, args);
	}
	
	/**
	 * Docket bean to control the endpoints exposed by Swagger.
	 */
	@Bean
	public Docket api() {
	    return new Docket(DocumentationType.SWAGGER_2).select()
	            .apis(RequestHandlerSelectors.any())
	            .paths(PathSelectors.any())
	            .build()
	            .apiInfo(apiInfo());
	}

	/**
	 * {@code ApiInfo} class that contains custom information about the API.
	 * @return apiInfo.
	 */
	private ApiInfo apiInfo() {
	    ApiInfo apiInfo = new ApiInfo("California Residents Notification"
	    		+ " Service", "Description of APIs.", "API TOS", 
	    		"Terms of service", new Contact("InTimeTec", 
	    		"http://intimetec.com/", "In Time Tec@intimetec.com"),
	    		"License of API", "API license URL");
	    return apiInfo;
	}
}