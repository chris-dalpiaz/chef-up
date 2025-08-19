package com.entra21.chef_up.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatGptService {

    // 🔸 Chave da API vinda da variável de ambiente
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
        requestBody.put("model", "gpt-4o-mini");
        requestBody.put("messages", new Object[]{
                Map.of("role", "user", "content", mensagem)
        });

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 🔸 Autorização usando API key do ambiente
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                Map.class
        );

        Map responseBody = response.getBody();
        if (responseBody != null && responseBody.containsKey("choices")) {
            var choices = (java.util.List<Map<String, Object>>) responseBody.get("choices");
            if (!choices.isEmpty()) {
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                return (String) message.get("content");
            }
        }
        return "Erro: resposta inválida do ChatGPT";
    }
}