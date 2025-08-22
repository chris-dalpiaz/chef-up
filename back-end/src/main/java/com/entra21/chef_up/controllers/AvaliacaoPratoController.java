package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.ReceitaUsuario.ReceitaUsuarioRequest;
import com.entra21.chef_up.dtos.ReceitaUsuario.ReceitaUsuarioResponse;
import com.entra21.chef_up.dtos.Receita.ReceitaResponse;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping
public class AvaliacaoPratoController {

    private final Path tempDir  = Paths.get(System.getProperty("user.dir"), "uploads", "temp");
    private final Path finalDir = Paths.get(System.getProperty("user.dir"), "uploads", "finais");

    private final ReceitaRepository       receitaRepository;
    private final UsuarioRepository       usuarioRepository;
    private final ReceitaUsuarioRepository receitaUsuarioRepository;
    private final ChatGptService          chatGptService;
    private final ModelMapper             modelMapper;
    private final ObjectMapper            objectMapper = new ObjectMapper();

    @Autowired
    public AvaliacaoPratoController(
            ReceitaRepository receitaRepository,
            UsuarioRepository usuarioRepository,
            ReceitaUsuarioRepository receitaUsuarioRepository,
            ChatGptService chatGptService,
            ModelMapper modelMapper
    ) throws IOException {
        this.receitaRepository        = receitaRepository;
        this.usuarioRepository        = usuarioRepository;
        this.receitaUsuarioRepository = receitaUsuarioRepository;
        this.chatGptService           = chatGptService;
        this.modelMapper              = modelMapper;
        Files.createDirectories(tempDir);
        Files.createDirectories(finalDir);
    }

    // ----------------------------------------------------------------
    // 1) Avaliar prato via multipart → devolve { comentario, nota, filename }
    // ----------------------------------------------------------------
    @PostMapping(
            path     = "/receitas/{idReceita}/avaliar-prato",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String, Object>> avaliarPrato(
            @PathVariable Integer idReceita,
            @RequestPart("file") MultipartFile file) {

        try {
            // 1. converter imagem em base64
            String base64 = Base64.getEncoder().encodeToString(file.getBytes());

            // 2. buscar receita e preparar DTO
            Receita receitaEntity = receitaRepository.findById(idReceita)
                    .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
            ReceitaResponse receitaDto = modelMapper.map(receitaEntity, ReceitaResponse.class);

            // 3. chamar ChatGPT
            String rawJson = chatGptService.avaliarPratoComImagem(base64, receitaDto);

            // 4. sanitizar eventual backticks (``` ou `) no início/fim
            String sanitized = rawJson
                    .trim()
                    .replaceAll("^```+", "")
                    .replaceAll("```+$", "")
                    .replaceAll("^`+", "")
                    .replaceAll("`+$", "");

            // 5. parse JSON
            JsonNode root = objectMapper.readTree(sanitized);
            String comentario = root.path("comentario").asText();
            int nota = root.path("nota").asInt();

            // 6. salvar imagem temporariamente
            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), tempDir.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);

            // 7. devolver JSON para frontend
            Map<String, Object> resp = new HashMap<>();
            resp.put("comentario", comentario);
            resp.put("nota", nota);
            resp.put("filename", filename);
            return ResponseEntity.ok(resp);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Falha ao avaliar prato"));
        }
    }

    // ----------------------------------------------------------------
    // 2) Salvar prato concluído em /usuarios/{idUsuario}/receitas
    // ----------------------------------------------------------------
    @PostMapping(
            path     = "/usuarios/{idUsuario}/receitas",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ReceitaUsuarioResponse> createUserRecipe(
            @PathVariable Integer idUsuario,
            @RequestBody ReceitaUsuarioRequest request) {

        try {
            // mover do temp para finais, se existir
            String fotoPath = request.getFotoPrato();
            String filename = Paths.get(fotoPath).getFileName().toString();
            Path source = tempDir.resolve(filename);
            Path target = finalDir.resolve(filename);
            if (Files.exists(source)) {
                Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            }

            // buscar entidades
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            Receita receita = receitaRepository.findById(request.getIdReceita())
                    .orElseThrow(() -> new RuntimeException("Receita não encontrada"));

            // montar e salvar associação
            ReceitaUsuario ru = new ReceitaUsuario();
            ru.setUsuario(usuario);
            ru.setReceita(receita);
            ru.setDataConclusao(Optional.ofNullable(request.getDataConclusao())
                    .orElse(LocalDateTime.now()));
            ru.setFotoPrato("/uploads/finais/" + filename);
            ru.setPontuacaoPrato(request.getPontuacaoPrato());
            ru.setTextoAvaliacao(request.getTextoAvaliacao());

            receitaUsuarioRepository.save(ru);

            // mapear para DTO de resposta
            ReceitaUsuarioResponse dto = modelMapper.map(ru, ReceitaUsuarioResponse.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
