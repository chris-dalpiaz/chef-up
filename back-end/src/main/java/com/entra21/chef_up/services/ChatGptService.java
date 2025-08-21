package com.entra21.chef_up.services;

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

    public String avaliarPratoComImagem(String base64Image) {
        RestTemplate restTemplate = new RestTemplate();

        // 🔹 Monta payload no formato esperado pela API
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o");

        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");

        List<Map<String, Object>> content = new ArrayList<>();

        // Texto
        content.add(Map.of(
                "type", "text",
                "text", "Avalie este prato de comida e dê uma nota de 0 a 5."
        ));

        // Imagem (base64)
        content.add(Map.of(
                "type", "image_url",
                "image_url", Map.of(
                        "url", "data:image/jpeg;base64," + base64Image
                )
        ));

        userMessage.put("content", content);

        requestBody.put("messages", List.of(userMessage));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            Map responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    if (message != null && message.containsKey("content")) {
                        return message.get("content").toString();
                    }
                }
            }
            return "Erro: resposta inválida do ChatGPT";

        } catch (HttpClientErrorException e) {
            System.err.println(" Erro HTTP: " + e.getStatusCode());
            System.err.println(" Corpo da resposta: " + e.getResponseBodyAsString());
            return "Erro ao comunicar com a API do ChatGPT: " + e.getMessage();
        } catch (Exception e) {
            System.err.println(" Erro inesperado: " + e.getMessage());
            return "Erro inesperado ao avaliar o prato.";
        }
    }
}