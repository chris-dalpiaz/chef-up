package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Avaliacao.AvaliacaoResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.*;

@Service
public class ChatGptService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public AvaliacaoResponseDTO avaliarPratoComImagem(String base64Image) {
        // ### 1) Forçar JSON estrito via mensagem de sistema
        Map<String, Object> systemMsg = Map.of(
                "role", "system",
                "content", "Retorne estritamente um JSON no formato: " +
                        "{\"avaliacao\":\"<texto>\",\"nota\":<inteiro de 0 a 5>} sem nenhum outro texto."
        );

        // ### 2) Mensagem do usuário com texto e imagem
        Map<String, Object> userMsg = Map.of(
                "role", "user",
                "content", List.of(
                        Map.of("type", "text",
                                "text", "Avalie este prato de comida e dê uma nota de 0 a 5."),
                        Map.of("type", "image_url",
                                "image_url", Map.of("url", "data:image/jpeg;base64," + base64Image))
                )
        );

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o");                                 // modelo explícito
        body.put("messages", List.of(systemMsg, userMsg));           // ordem: sistema → usuário

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> resp = restTemplate.exchange(
                    apiUrl, HttpMethod.POST, request, Map.class
            );

            // ### 3) Extrair content bruto
            List<Map<String, Object>> choices = (List<Map<String, Object>>) resp.getBody()
                    .get("choices");
            String raw = ((Map<String,Object>)choices.get(0).get("message"))
                    .get("content").toString();

            // ### 4) Desserializar para DTO
            AvaliacaoResponseDTO dto = objectMapper.readValue(raw, AvaliacaoResponseDTO.class);
            return dto;

        } catch (Exception e) {
            throw new RuntimeException("Falha ao chamar API ChatGPT: " + e.getMessage(), e);
        }
    }
}