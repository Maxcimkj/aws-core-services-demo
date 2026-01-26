package com.example.note_manager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")      // All endpoints
                        .allowedOriginPatterns("*") // All domains
                        .allowedMethods("*")    // GET, POST, PUT, DELETE, etc.
                        .allowedHeaders("*")    // All headers
                        .allowCredentials(false); // MUST be false if using "*"
            }
        };
    }
}