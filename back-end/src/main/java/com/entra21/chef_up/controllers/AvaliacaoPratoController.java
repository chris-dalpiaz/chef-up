package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.ReceitaUsuario;
import com.entra21.chef_up.repositories.ReceitaUsuarioRepository;
import com.entra21.chef_up.services.ChatGptService;
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

    private final Path tempDir = Paths.get("uploads/temp");
    private final Path finalDir = Paths.get("uploads/finais");

    @Autowired
    private ReceitaUsuarioRepository receitaUsuarioRepository;

    // 🔸 Injetando o ChatGptService
    @Autowired
    private ChatGptService chatGptService;

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
            return ResponseEntity.ok(Map.of("tempUrl", tempUrl));
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

    /**
     * 🔸 Novo endpoint que usa ChatGptService para avaliar a imagem
     */
    @PostMapping("/avaliar-prato")
    public ResponseEntity<Map<String, Object>> avaliarPrato(@RequestParam("file") MultipartFile file) {
        try {
            // 🔸 Converte a imagem para Base64
            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

            // 🔸 Chama o ChatGPT Service com a imagem
            String avaliacaoTexto = chatGptService.avaliarPratoComImagem(base64Image);

            // 🔹 Extração simples de pontuação (melhorar regex conforme resposta)
            String match = avaliacaoTexto.replaceAll("\\D+", "");
            int pontuacao = match.isEmpty() ? 0 : Integer.parseInt(match);

            return ResponseEntity.ok(Map.of(
                    "avaliacaoTexto", avaliacaoTexto,
                    "pontuacao", pontuacao
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao avaliar prato"));
        }
    }

    /**
     * 🔹 Salvar no banco após avaliação
     */
    @PostMapping("/salvar-prato")
    public ResponseEntity<Map<String, String>> salvarPrato(@RequestBody Map<String, String> body) {
        try {
            String avaliacaoTexto = body.get("avaliacaoTexto");
            String pontuacaoStr = body.get("pontuacao");
            String tempUrl = body.get("imageUrl");
            String usuarioIdStr = body.get("usuarioId");
            String receitaIdStr = body.get("receitaId");

            String filename = tempUrl.substring(tempUrl.lastIndexOf("/") + 1);
            Path tempFile = tempDir.resolve(filename);
            Path finalFile = finalDir.resolve(filename);

            Files.move(tempFile, finalFile, StandardCopyOption.REPLACE_EXISTING);

            ReceitaUsuario receitaUsuario = new ReceitaUsuario();
            receitaUsuario.setFotoPrato("/uploads/finais/" + filename);
            receitaUsuario.setPontuacaoPrato(Integer.parseInt(pontuacaoStr));
            receitaUsuario.setDataConclusao(java.time.LocalDateTime.now());

            // 🔹 Aqui você pode setar Usuario e Receita vindos do banco
            // receitaUsuario.setUsuario(usuarioRepository.findById(...))
            // receitaUsuario.setReceita(receitaRepository.findById(...))

            receitaUsuarioRepository.save(receitaUsuario);

            return ResponseEntity.ok(Map.of("message", "Prato salvo com sucesso!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao salvar prato"));
        }
    }
}