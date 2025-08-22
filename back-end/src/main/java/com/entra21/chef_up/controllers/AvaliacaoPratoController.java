package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Receita.ReceitaResponse;
import com.entra21.chef_up.dtos.ReceitaUsuario.ReceitaUsuarioRequest;
import com.entra21.chef_up.dtos.ReceitaUsuario.ReceitaUsuarioResponse;
import com.entra21.chef_up.entities.Receita;
import com.entra21.chef_up.entities.ReceitaUsuario;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.ReceitaRepository;
import com.entra21.chef_up.repositories.ReceitaUsuarioRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import com.entra21.chef_up.services.ChatGptService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping
public class AvaliacaoPratoController {

    private static final Logger log = LoggerFactory.getLogger(AvaliacaoPratoController.class);

    private final Path tempDir  = Paths.get(System.getProperty("user.dir"), "uploads", "temp");
    private final Path finalDir = Paths.get(System.getProperty("user.dir"), "uploads", "finais");

    private final ReceitaRepository receitaRepo;
    private final UsuarioRepository usuarioRepo;
    private final ReceitaUsuarioRepository receitaUsuarioRepo;
    private final ChatGptService chatGptService;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AvaliacaoPratoController(
            ReceitaRepository receitaRepo,
            UsuarioRepository usuarioRepo,
            ReceitaUsuarioRepository receitaUsuarioRepo,
            ChatGptService chatGptService,
            ModelMapper mapper
    ) throws Exception {
        this.receitaRepo         = receitaRepo;
        this.usuarioRepo         = usuarioRepo;
        this.receitaUsuarioRepo  = receitaUsuarioRepo;
        this.chatGptService      = chatGptService;
        this.mapper              = mapper;
        Files.createDirectories(tempDir);
        Files.createDirectories(finalDir);
    }

    @PostMapping(
            path     = "/receitas/{idReceita}/avaliar-prato",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String,Object>> avaliarPrato(
            @PathVariable Integer idReceita,
            @RequestPart("file") MultipartFile file
    ) {
        try {
            // converter imagem em Base64
            String base64 = Base64.getEncoder().encodeToString(file.getBytes());

            // buscar receita e mapear DTO
            Receita entity = receitaRepo.findById(idReceita)
                    .orElseThrow(() -> new NoSuchElementException("Receita não encontrada"));
            ReceitaResponse dto = mapper.map(entity, ReceitaResponse.class);

            // chamar ChatGPT
            String rawJson = chatGptService.avaliarPratoComImagem(base64, dto);

            // remover backticks do JSON
            String sanitized = rawJson
                    .trim()
                    .replaceAll("^```+", "")
                    .replaceAll("```+$", "")
                    .replaceAll("^`+", "")
                    .replaceAll("`+$", "");

            // parse JSON
            JsonNode root     = objectMapper.readTree(sanitized);
            String comentario = root.path("comentario").asText();
            int nota          = root.path("nota").asInt();

            // salvar imagem em temp
            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), tempDir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);

            // resposta
            return ResponseEntity.ok(Map.of(
                    "comentario", comentario,
                    "nota",       nota,
                    "filename",   filename
            ));

        } catch (Exception ex) {
            // loga a stacktrace completa no servidor
            log.error("Erro ao avaliar prato id={}: {}", idReceita, ex.getMessage(), ex);

            // retorna também a mensagem de exceção para facilitar o debug no cliente
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error",     "Falha ao avaliar prato",
                            "details",   ex.getMessage()
                    ));
        }
    }

    @PostMapping(
            path     = "/usuarios/{idUsuario}/receitas",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ReceitaUsuarioResponse> createUserRecipe(
            @PathVariable Integer idUsuario,
            @RequestBody ReceitaUsuarioRequest request
    ) {
        try {
            // move arquivo de temp para finais
            String fotoPath = request.getFotoPrato();
            String filename = Paths.get(fotoPath).getFileName().toString();
            Path source = tempDir.resolve(filename);
            Path target = finalDir.resolve(filename);
            if (Files.exists(source)) {
                Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            }

            // buscar entidades
            Usuario usuario = usuarioRepo.findById(idUsuario)
                    .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
            Receita receita = receitaRepo.findById(request.getIdReceita())
                    .orElseThrow(() -> new NoSuchElementException("Receita não encontrada"));

            // montar entidade de associação
            ReceitaUsuario ru = new ReceitaUsuario();
            ru.setUsuario(usuario);
            ru.setReceita(receita);
            ru.setDataConclusao(Optional.ofNullable(request.getDataConclusao()).orElse(LocalDateTime.now()));
            ru.setFotoPrato("/uploads/finais/" + filename);
            ru.setPontuacaoPrato(request.getPontuacaoPrato());
            ru.setTextoAvaliacao(request.getTextoAvaliacao());

            // salvar e retornar DTO
            receitaUsuarioRepo.save(ru);
            ReceitaUsuarioResponse respDto = mapper.map(ru, ReceitaUsuarioResponse.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(respDto);

        } catch (Exception ex) {
            log.error("Erro ao salvar prato concluído idUsuario={}: {}", idUsuario, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
