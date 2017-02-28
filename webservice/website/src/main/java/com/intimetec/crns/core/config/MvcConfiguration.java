package com.intimetec.crns.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author shiva.dixit.
 */
@Configuration
@EnableWebMvc
@CrossOrigin
public class MvcConfiguration extends WebMvcConfigurerAdapter {
	
	@Override
	public final void addResourceHandlers(
			final ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/view/**")
          .addResourceLocations("/resources/", "WEB-INF/views/");    
        registry
         .addResourceHandler("/css/**")
         .addResourceLocations("/resources/", "WEB-INF/css/");
        registry
          .addResourceHandler("/lib/**")
          .addResourceLocations("/resources/", "WEB-INF/lib/");
        registry
         .addResourceHandler("/js/**")
         .addResourceLocations("/resources/", "WEB-INF/js/");
        registry
        .addResourceHandler("/fonts/**")
        .addResourceLocations("/resources/", "WEB-INF/fonts/");
        registry
        .addResourceHandler("/images/**")
        .addResourceLocations("/resources/", "WEB-INF/images/");
        
        /* Added resource handler location for Swagger UI */
        registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

	@Override
	public final void configureViewResolvers(
			final ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = 
        		new InternalResourceViewResolver();
        resolver.setPrefix("/view/");
        resolver.setSuffix(".html");
        registry.viewResolver(resolver);
    }

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowCredentials(true);
	}
	
	/*@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/api/**")
	        .allowedOrigins("http://domain2.com")
	        .allowedMethods("PUT", "DELETE")
	        .allowedHeaders("header1", "header2", "header3")
	        .exposedHeaders("header1", "header2")
	        .allowCredentials(false).maxAge(3600);
	}*/
}