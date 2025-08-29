package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Receita.ReceitaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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
     * Avalia um prato de comida comparando com a receita e retorna JSON estruturado.
     * @param base64Image imagem do prato em base64
     * @param receita objeto ReceitaResponse com todos os dados da receita
     * @return JSON estruturado em string: {"comentario": "...", "nota": 0-5}
     */
    public String avaliarPratoComImagem(String base64Image, ReceitaResponse receita) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o");

        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");

        List<Map<String, Object>> content = new ArrayList<>();

        // Prompt dinâmico e detalhado
        StringBuilder prompt = new StringBuilder();
        prompt.append("Você é uma inteligência artificial simpática e acessível que avalia pratos feitos por pessoas comuns. ");
        prompt.append("Compare o prato da imagem com os dados da receita fornecida e escreva uma avaliação leve e amigável em terceira pessoa. ");
        prompt.append("Valorize o esforço da pessoa, destacando pontos positivos como apresentação, cor, textura e ingredientes visíveis. ");
        prompt.append("Evite ser técnico ou exigente demais. Não diga que faltam informações ou que a avaliação está prejudicada. ");
        prompt.append("Se o prato estiver claramente diferente da receita, você pode dar a nota 0, mas sempre com gentileza. ");
        prompt.append("Forneça uma nota definitiva de 0 a 5 (número inteiro), sem decimais. ");
        prompt.append("Retorne apenas um JSON válido, sem texto explicativo antes ou depois. Use o seguinte formato:\n");
        prompt.append("{\"comentario\": \"avaliação completa, referindo-se a si mesmo como inteligência artificial, com tom leve e encorajador\", \"nota\": numero}\n\n");

        prompt.append("=== Dados da Receita ===\n");
        prompt.append("Nome: ").append(receita.getNome()).append("\n");
        prompt.append("Descrição: ").append(receita.getDescricao()).append("\n");
        prompt.append("Tempo de preparo (s): ").append(receita.getTempoPreparoSegundos()).append("\n");
        prompt.append("Dificuldade: ").append(receita.getDificuldade()).append("\n");
        prompt.append("XP ganho: ").append(receita.getXpGanho()).append("\n");
        prompt.append("Categoria: ").append(receita.getCategoria() != null ? receita.getCategoria().getNome() : "N/A").append("\n");
        prompt.append("Ingredientes: ").append(receita.getIngredientes() != null ? receita.getIngredientes().toString() : "[]").append("\n");
        prompt.append("Utensílios: ").append(receita.getUtensilios() != null ? receita.getUtensilios().toString() : "[]").append("\n");
        prompt.append("Etapas: ").append(receita.getEtapas() != null ? receita.getEtapas().toString() : "[]").append("\n\n");

        prompt.append("Exemplo de resposta JSON:\n");
        prompt.append("{\"comentario\": \"Nossa inteligência artificial achou o prato bem feito! A cor está vibrante, a textura parece ótima e os ingredientes parecem estar todos ali. Uma ótima execução!\", \"nota\": 5}");

        // Adiciona o prompt como mensagem de texto
        content.add(Map.of(
                "type", "text",
                "text", prompt.toString()
        ));

        // Adiciona a imagem
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
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>() {}
            );

            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    if (message != null && message.containsKey("content")) {
                        return message.get("content").toString(); // JSON estruturado
                    }
                }
            }
            return "{\"comentario\": \"Erro: resposta inválida do ChatGPT\", \"nota\": 0}";

        } catch (HttpClientErrorException e) {
            System.err.println("Erro HTTP: " + e.getStatusCode());
            System.err.println("Corpo da resposta: " + e.getResponseBodyAsString());
            return "{\"comentario\": \"Erro ao comunicar com a API do ChatGPT: " + e.getMessage() + "\", \"nota\": 0}";
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            return "{\"comentario\": \"Erro inesperado ao avaliar o prato.\", \"nota\": 0}";
        }
    }
}
