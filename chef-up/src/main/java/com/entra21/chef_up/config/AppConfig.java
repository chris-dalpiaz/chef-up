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
@Configuration // Informa ao Spring que esta classe contém definições de beans
public class AppConfig {

    /**
     * Cria e configura um bean do tipo ModelMapper.
     * O ModelMapper é usado para converter objetos de um tipo para outro (ex: DTO para entidade).
     *
     * @return instância configurada de ModelMapper
     */
    @Bean // Indica que este método retorna um bean gerenciado pelo Spring
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Configura o mapeamento entre ColecaoRequest e Colecao
        // Ignora o campo 'id' ao fazer a conversão, para evitar sobrescrever IDs existentes
        mapper.typeMap(ColecaoRequest.class, Colecao.class)
                .addMappings(m -> m.skip(Colecao::setId));

        return mapper;
    }
}
