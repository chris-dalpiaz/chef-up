package com.entra21.chef_up.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StaticResourceConfig extends WebConfig {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ◼ ALTERAÇÃO: path relativo a partir de user.dir
        Path uploads = Paths
                .get(System.getProperty("user.dir"), "img", "receitasUsuario", "finais")
                .toAbsolutePath();

        registry
                .addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploads.toString() + "/");
    }
}