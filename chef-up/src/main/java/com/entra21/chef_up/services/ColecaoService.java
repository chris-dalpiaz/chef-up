package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Colecao.ColecaoRequest;
import com.entra21.chef_up.dtos.Colecao.ColecaoResponse;
import com.entra21.chef_up.entities.Colecao;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.ColecaoRepository;
import com.entra21.chef_up.repositories.ReceitaColecaoRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ColecaoService {

    private final UsuarioRepository usuarioRepository;
    private final ReceitaColecaoRepository receitaColecaoRepository;
    private final ColecaoRepository colecaoRepository;
    private final ModelMapper modelMapper;

    public ColecaoService(UsuarioRepository usuarioRepository, ReceitaColecaoRepository receitaColecaoRepository, ColecaoRepository colecaoRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.receitaColecaoRepository = receitaColecaoRepository;
        this.colecaoRepository = colecaoRepository;
        this.modelMapper = modelMapper;
    }


    public List<ColecaoResponse> listarTodos() {
        return colecaoRepository.findAll().stream().map(u -> modelMapper.map(u, ColecaoResponse.class)).toList();
    }

    public ColecaoResponse buscar(Integer id) {
        Colecao colecao = colecaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleção não encontrada"));

        return modelMapper.map(colecao, ColecaoResponse.class);
    }

    public ColecaoResponse criar(ColecaoRequest request) {
        // Busca o usuário pelo ID
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Cria a entidade Colecao
        Colecao colecao = new Colecao();
        colecao.setNome(request.getNome());
        colecao.setUsuario(usuario); // associa o usuário

        // Salva no banco
        Colecao salvo = colecaoRepository.save(colecao);

        // Converte para DTO de resposta
        return modelMapper.map(salvo, ColecaoResponse.class);
    }


    public ColecaoResponse alterar(Integer id, ColecaoRequest request) {
        /// Busca pelo ID ou lança erro 404
        Colecao alterar = colecaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleção não encontrada"));

        /// Atualiza o nome com os dados do request
        alterar.setNome(request.getNome());

        /// Salva a alteração no banco
        Colecao salvo = colecaoRepository.save(alterar);

        /// Converte a entidade salva para DTO de resposta e retorna
        return modelMapper.map(salvo, ColecaoResponse.class);
    }

    @Transactional
    public ColecaoResponse remover(Integer id) {
        /// Busca pelo ID ou lança 404
        Colecao colecao = colecaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleção não encontrada"));

        /// removendo dados que usam a fk da receita em outras tabelas, para poder removê-la
        receitaColecaoRepository.removeByColecaoId(id);

        /// Deleta o adjetivo pelo ID
        colecaoRepository.deleteById(id);

        /// Retorna o DTO do adjetivo deletado
        return modelMapper.map(colecao, ColecaoResponse.class);
    }
}
