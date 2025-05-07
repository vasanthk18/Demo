package com.ozone.smart.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	
//	@Bean
//	public WebMvcConfigurer configure() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry reg) {
//				reg.addMapping("/**").allowedOrigins("*");
//			}
//		};
//		
//	}
	
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	      registry.addMapping("/**")
        .allowedOrigins("http://localhost:5173")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*");
//	        		.exposedHeaders("X-Message");
	    }
	   
}
