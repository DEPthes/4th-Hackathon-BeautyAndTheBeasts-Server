package hackerton.demo.util.gemini.DTO;

import hackerton.demo.util.gemini.GeminiResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "GPT 응답 결과")
public class GeminiResponse {
    @Schema(description = "요청 식별자 UUID")
    private String uuid;
    @Schema(description = "사용자가 쓴 글")
    private String prompt;
    private String gptResponse;
    private String imageUrl;
    private LocalDateTime createdAt;

    public static GeminiResponse from(GeminiResult result) {
        return GeminiResponse.builder()
                .uuid(result.getUuid())
                .prompt(result.getRequestPrompt())
                .gptResponse(result.getGptResponse())
                .imageUrl(result.getImageUrl())
                .createdAt(result.getCreatedAt())
                .build();
    }
}