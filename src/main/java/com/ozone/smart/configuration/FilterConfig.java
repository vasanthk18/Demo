//package com.ozone.smart.configuration;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FilterConfig {
//
//    @Bean
//    public FilterRegistrationBean<CustomCorsFilter> customCorsFilter() {
//        FilterRegistrationBean<CustomCorsFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new CustomCorsFilter());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(1); // Ensure the filter is executed before other filters
//        return registrationBean;
//    }
//}
//
