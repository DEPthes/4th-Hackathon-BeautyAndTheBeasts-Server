package hackerton.demo.util.gemini;

import hackerton.demo.util.gemini.DTO.GeminiRequest;
import hackerton.demo.util.gemini.DTO.GeminiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gemini")
@Tag(name = "Gemini API")
public class GeminiController {
    private final GeminiService geminiService;

    @Operation(
            summary = "Gemini 응답 받기",
            description = "사용자가 보낸 prompt를 기반으로 Gemini에서 생성된 응답을 반환합니다."
    )
    @PostMapping
    public ResponseEntity<GeminiResponse> askGemini(@RequestBody GeminiRequest request){
        GeminiResult result = geminiService.getGeminiResponse(request.getPrompt());
        GeminiResponse response = GeminiResponse.from(result);
        return ResponseEntity.ok(response);
    }
}
