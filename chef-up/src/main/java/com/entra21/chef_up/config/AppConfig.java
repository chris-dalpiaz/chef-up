package com.entra21.chef_up.config;

import com.entra21.chef_up.dtos.Colecao.ColecaoRequest;
import com.entra21.chef_up.entities.Colecao;
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

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.typeMap(ColecaoRequest.class, Colecao.class)
                .addMappings(m -> m.skip(Colecao::setId));
        return mapper;
    }

}
