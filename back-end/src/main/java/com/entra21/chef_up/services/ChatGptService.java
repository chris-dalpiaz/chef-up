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

    /**
     * Envia a mensagem para o ChatGPT e retorna a resposta textual
     */
    public String avaliarPrato(String mensagem) {
        RestTemplate restTemplate = new RestTemplate();

        // 🔸 Monta payload com mensagem do usuário
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o"); // Use "gpt-4o" ou "gpt-3.5-turbo" se necessário

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "user", "content", mensagem));
        requestBody.put("messages", messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey); // ✅ Cabeçalho manualmente definido

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
                    return (String) message.get("content");
                }
            }
            return "Erro: resposta inválida do ChatGPT";

        } catch (HttpClientErrorException e) {
            System.err.println("❌ Erro HTTP: " + e.getStatusCode());
            System.err.println("❌ Corpo da resposta: " + e.getResponseBodyAsString());
            return "Erro ao comunicar com a API do ChatGPT: " + e.getMessage();
        } catch (Exception e) {
            System.err.println("❌ Erro inesperado: " + e.getMessage());
            return "Erro inesperado ao avaliar o prato.";
        }
    }
}
