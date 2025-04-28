//package com.ozone.smart.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity()
//public class SecurityConfig {
//	
//	@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.cors(cors -> cors.disable());
//        http.csrf(csrf -> csrf.disable());
//        /*
//        http.sessionManagement(sessionManager  -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(
//                (request, response, exception) -> {
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
//                }));
//        http.authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
//                .requestMatchers("/*").permitAll()
//                .anyRequest().authenticated()
//        );
//        */
//        http.addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class); // custom protocol Authorization
//        return http.build();
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new
//                UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        //config.setAllowedOriginPatterns(yamlConfig.getCorsAllowedList());
//        //LOGGER.info("Added CORS allowed patterns: '{}' ", String.join("', '", yamlConfig.getCorsAllowedList()));
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/", config);
//        return new CorsFilter(source);
//    }
//
//}
