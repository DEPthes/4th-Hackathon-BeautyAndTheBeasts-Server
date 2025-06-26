package hackerton.demo.util.gemini;

import hackerton.demo.util.gemini.DTO.GeminiRequest;
import hackerton.demo.util.gemini.DTO.GeminiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<GeminiResponse> askGemini(@RequestBody @Valid GeminiRequest request){
        GeminiResult result = geminiService.getGeminiResponse(request.getPrompt());
        GeminiResponse response = GeminiResponse.from(result);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Gemini 재응답",
            description = "재생성을 누르면 uuid를 기반으로 사진과 GEMINI응답값을 재생성합니다."
    )
    @PutMapping("/{uuid}/regenerate")
    public ResponseEntity<GeminiResponse> regenerateGemini(@PathVariable String uuid) {
        GeminiResult updated = geminiService.regenerate(uuid);
        return ResponseEntity.ok(GeminiResponse.from(updated));
    }

    @Operation(
            summary = "Gemini 응답값 조회",
            description = "uuid를 기반으로 조회"
    )
    @GetMapping("/{uuid}")
    public ResponseEntity<GeminiResponse> getGeminiResult(@PathVariable String uuid) {
        GeminiResult result = geminiService.findByUuid(uuid);
        return ResponseEntity.ok(GeminiResponse.from(result));
    }
}
