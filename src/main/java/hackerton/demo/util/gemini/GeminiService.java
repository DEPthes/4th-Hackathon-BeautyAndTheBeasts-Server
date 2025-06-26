package hackerton.demo.util.gemini;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hackerton.demo.util.gemini.Image.GeminiImage;
import hackerton.demo.util.gemini.Image.GeminiImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${gemini.api-key}")
    private String apiKey;

    private final GeminiResultRepository geminiResultRepository;
    private final GeminiImageRepository geminiImageRepository;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://generativelanguage.googleapis.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();


    public String generateGptResponse(String prompt) {
        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt + "(1. 2~3줄로 출력할 것 , 2. 소재를 과장하거나 익살스럽게 풍자하는 유머로 응답할 것(블랙코미디). )")
                        ))
                ),
                "generationConfig", Map.of(
                        "maxOutputTokens", 100,
                        "temperature", 0.7
                )
        );

        String responseJson = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1beta/models/gemini-2.0-flash:generateContent")
                        .queryParam("key", apiKey)
                        .build())
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return extractTextFromGeminiResponse(responseJson);
    }

    public GeminiResult getGeminiResponse(String prompt) {
        String gptText = generateGptResponse(prompt);
        String imageUrl = getRandomImageUrlFromDB();
        String uuid = UUID.randomUUID().toString();

        GeminiResult result = GeminiResult.builder()
                .uuid(uuid)
                .requestPrompt(prompt)
                .gptResponse(gptText)
                .imageUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .build();

        geminiResultRepository.save(result);
        return result;
    }

    private String getRandomImageUrlFromDB() {
        List<GeminiImage> images = geminiImageRepository.findAll();
        if (images.isEmpty()) {
            throw new IllegalStateException("등록된 이미지가 없습니다.");
        }
        return images.get(new Random().nextInt(images.size())).getImageUrl();
    }

    private String extractTextFromGeminiResponse(String responseJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseJson);

            return root.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
        } catch (Exception e) {
            return "Gemini 응답 파싱 오류: " + e.getMessage();
        }
    }

    @Transactional
    public GeminiResult regenerate(String uuid) {
        GeminiResult result = geminiResultRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 결과가 존재하지 않습니다."));

        String prompt = result.getRequestPrompt();
        String newResponse = generateGptResponse(prompt);
        String newImageUrl = getRandomImageUrlFromDB();

        result.regenerate(newResponse, newImageUrl);

        return geminiResultRepository.save(result);
    }
}