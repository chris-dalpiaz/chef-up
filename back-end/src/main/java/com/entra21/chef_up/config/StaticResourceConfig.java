package com.entra21.chef_up.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // monta dinamicamente a pasta
        // ex: C:\Users\diogo.donner\Documents\GitHub\chef-up\back-end (intellij)\img\receitasUsuario\temp
        Path tempDir = Paths
                .get(System.getProperty("user.dir"), "img", "receitasUsuario", "temp")
                .toAbsolutePath();

        // registra todos os arquivos sob /img/receitasUsuario/temp/**
        // para serem servidos via HTTP em /img/receitasUsuario/temp/**
        registry
                .addResourceHandler("/img/receitasUsuario/temp/**")
                .addResourceLocations("file:" + tempDir.toString() + "/");
    }
}