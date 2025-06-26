package hackerton.demo.util.gemini;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${gemini.api-key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://generativelanguage.googleapis.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    public String getGeminiResponse(String prompt) {
        if (apiKey == null || apiKey.isBlank()) {
            return "API 키가 설정되어 있지 않습니다.";
        }

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt + "(1. 2~3줄로 출력할 것 , 2.소재를 과장하거나 익살스럽게 풍자하는 유머로 응답할 것(블랙코미디). 3. 존댓말 쓰지 말것. )")
                        ))

                ),
                "generationConfig", Map.of(
                        "maxOutputTokens", 100, // 출력되는 토큰 수 제어
                        "temperature", 0.7 // 1에 가까울 수록 창의적 답변
                )
        );

        try {
            return webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1beta/models/gemini-2.0-flash:generateContent")
                            .queryParam("key", apiKey)
                            .build())
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            return "Gemini API 호출 실패: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "Gemini API 호출 중 오류 발생: " + e.getMessage();
        }
    }
}