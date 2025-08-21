package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Avaliacao.AvaliacaoResponseDTO;
import com.entra21.chef_up.entities.ReceitaUsuario;
import com.entra21.chef_up.repositories.ReceitaUsuarioRepository;
import com.entra21.chef_up.services.ChatGptService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AvaliacaoPratoController() throws IOException {
        Files.createDirectories(tempDir);
        Files.createDirectories(finalDir);
    }

    @PostMapping("/avaliar-prato")
    public ResponseEntity<Map<String, Object>> avaliarPrato(
            @RequestParam("file") MultipartFile file) {
        try {
            // ### 1) Salvando em temp
            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path tempFile = tempDir.resolve(filename);
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            // ### 2) Converter para Base64 e chamar ChatGPT
            String base64 = java.util.Base64.getEncoder().encodeToString(file.getBytes());
            AvaliacaoResponseDTO dto = chatGptService.avaliarPratoComImagem(base64);

            // ### 3) Validar nota (0–5)
            if (dto.getNota() == null
                    || dto.getNota() < 0
                    || dto.getNota() > 5) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "Nota fora do intervalo esperado (0–5). Recebido=" + dto.getNota()
                );
            }

            // ### 4) Retornar JSON com avaliação, nota e filename
            return ResponseEntity.ok(Map.of(
                    "avaliacao", dto.getAvaliacao(),
                    "nota", dto.getNota(),
                    "filename", filename
            ));
        } catch (IOException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Falha ao processar arquivo", ex
            );
        }
    }

    @PostMapping("/salvar-prato")
    public ResponseEntity<Map<String, String>> salvarPrato(
            @RequestBody Map<String, String> body) {
        try {
            String avaliacao = body.get("avaliacao");
            Integer nota       = Integer.valueOf(body.get("nota"));
            String filename    = body.get("filename");

            // ### 1) Mover de temp → final
            Path tempFile  = tempDir.resolve(filename);
            Path finalFile = finalDir.resolve(filename);
            Files.move(tempFile, finalFile, StandardCopyOption.REPLACE_EXISTING);

            // ### 2) Persistir no banco
            ReceitaUsuario ru = new ReceitaUsuario();
            ru.setFotoPrato("/uploads/finais/" + filename);
            ru.setPontuacaoPrato(nota);
            ru.setDataConclusao(LocalDateTime.now());
            // TODO: ru.setUsuario(...); ru.setReceita(...);

            receitaUsuarioRepository.save(ru);

            return ResponseEntity.ok(Map.of("message", "Prato salvo com sucesso!"));
        } catch (IOException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Arquivo não encontrado: " + ex.getMessage(), ex
            );
        }
    }
}