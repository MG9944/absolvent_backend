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


    /*
    @Bean
    public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        // AuthFilter authFilter = new AuthFilter();
        //registrationBean.setFilter(authFilter);
        //registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
    */

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
