package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Receita.ReceitaResponse;
import com.entra21.chef_up.entities.ReceitaUsuario;
import com.entra21.chef_up.entities.Receita;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.ReceitaUsuarioRepository;
import com.entra21.chef_up.repositories.ReceitaRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import com.entra21.chef_up.services.ChatGptService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoPratoController {

    private final Path tempDir = Paths.get(System.getProperty("user.dir"), "uploads", "temp");
    private final Path finalDir = Paths.get(System.getProperty("user.dir"), "uploads", "finais");

    @Autowired
    private ReceitaUsuarioRepository receitaUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private ChatGptService chatGptService;

    @Autowired
    private ModelMapper modelMapper;

    private final ObjectMapper mapper = new ObjectMapper();

    public AvaliacaoPratoController() throws IOException {
        Files.createDirectories(tempDir);
        Files.createDirectories(finalDir);
    }

    @PostMapping("/upload-temp")
    public ResponseEntity<Map<String, String>> uploadTemp(@RequestParam("file") MultipartFile file) {
        try {
            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path filePath = tempDir.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String tempUrl = "http://localhost:8080/avaliacao/temp/" + filename;
            return ResponseEntity.ok(Map.of("tempUrl", tempUrl, "filename", filename));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Falha ao salvar arquivo"));
        }
    }

    @GetMapping("/temp/{filename:.+}")
    public ResponseEntity<Resource> getTempFile(@PathVariable String filename) throws IOException {
        Path filePath = tempDir.resolve(filename);
        Resource resource = new UrlResource(filePath.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/avaliar-prato")
    public ResponseEntity<Map<String, Object>> avaliarPrato(
            @RequestParam("file") MultipartFile file,
            @RequestParam("receitaId") Integer receitaId) {
        try {
            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

            // 🔹 Busca receita e converte para DTO usando ModelMapper
            Receita receitaEntity = receitaRepository.findById(receitaId)
                    .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
            ReceitaResponse receita = modelMapper.map(receitaEntity, ReceitaResponse.class);

            // 🔹 Avalia prato com ChatGPT
            String avaliacaoJson = chatGptService.avaliarPratoComImagem(base64Image, receita);

            // 🔹 Parse do JSON retornado pelo GPT
            JsonNode root = mapper.readTree(avaliacaoJson);
            String comentario = root.path("comentario").asText();
            int nota = root.path("nota").asInt();

            // 🔹 Salva arquivo temporário
            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path tempFile = tempDir.resolve(filename);
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok(Map.of(
                    "comentario", comentario,
                    "nota", nota,
                    "filename", filename
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao avaliar prato"));
        }
    }

    @PostMapping("/salvar-prato")
    public ResponseEntity<Map<String, String>> salvarPrato(@RequestBody Map<String, String> body) {
        try {
            String comentario = body.get("comentario");
            int nota = Integer.parseInt(body.get("nota"));
            String filename = body.get("filename");
            Integer usuarioId = Integer.valueOf(body.get("usuarioId"));
            Integer receitaId = Integer.valueOf(body.get("receitaId"));

            Path tempFile = tempDir.resolve(filename);
            Path finalFile = finalDir.resolve(filename);
            Files.move(tempFile, finalFile, StandardCopyOption.REPLACE_EXISTING);

            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
            Receita receita = receitaRepository.findById(receitaId).orElseThrow();

            ReceitaUsuario receitaUsuario = new ReceitaUsuario();
            receitaUsuario.setFotoPrato("/uploads/finais/" + filename);
            receitaUsuario.setPontuacaoPrato(nota);
            receitaUsuario.setDataConclusao(java.time.LocalDateTime.now());
            receitaUsuario.setUsuario(usuario);
            receitaUsuario.setReceita(receita);

            receitaUsuarioRepository.save(receitaUsuario);

            return ResponseEntity.ok(Map.of("message", "Prato salvo com sucesso!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao salvar prato"));
        }
    }
}
