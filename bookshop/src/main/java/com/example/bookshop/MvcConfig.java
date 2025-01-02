package com.example.bookshop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/accessDenied").setViewName("errors/accessDenied");
        registry.addViewController("/default").setViewName("default");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Додаємо ресурсний обробник для bower_components
        registry.addResourceHandler("/bower_components/**")
                .addResourceLocations("file:./bower_components/");
    }
}