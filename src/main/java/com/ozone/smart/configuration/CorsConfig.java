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
	                .allowedOrigins("https://vby.vercel.app", "http://localhost:5173") // Replace with your React frontend URL
	                // .allowedOriginPatterns("*")
					.allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
	                .allowedHeaders("*")
	                .allowCredentials(true);
//	        		.exposedHeaders("X-Message");
	    }
	   
}
