package com.intimetec.crns.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
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
        .addResourceHandler("/images/**")
        .addResourceLocations("/resources/", "WEB-INF/images/");
        
        /* Added resource handler location for Swagger UI */
        registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

	@Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/view/");
        resolver.setSuffix(".html");
        registry.viewResolver(resolver);
    }

}