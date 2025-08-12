package com.entra21.chef_up.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Indica que esta classe é uma classe de configuração do Spring.
 * Ela serve para definir beans que o Spring vai criar e gerenciar.
 * O que é um bean?
 * No Spring, um bean é um objeto que o próprio Spring gerencia durante o ciclo de vida da aplicação.
 * Ele cria, configura e injeta esse objeto onde for necessário, facilitando o reuso e a organização do código.
 */
@Configuration

public class AppConfig {
    /**
     * Marca este método para que o Spring crie e registre um bean no contexto da aplicação.
     * O bean é uma instância de ModelMapper, que poderá ser usada em outras partes do projeto.
     * Bean: objeto gerenciado pelo Spring. Pode ser qualquer classe (ex: ModelMapper, serviços, repositórios).
     * ModelMapper: biblioteca que converte um objeto em outro (ex: Entity → DTO).
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
