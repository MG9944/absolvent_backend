package com.umg.absolwentbackend;

import com.umg.absolwentbackend.filtres.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AbsolwentbackendApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(AbsolwentbackendApplication.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "*"
                )
                .allowedMethods(
                        "GET",
                        "PUT",
                        "POST",
                        "DELETE",
                        "PATCH",
                        "OPTIONS"
                );
    }
}
