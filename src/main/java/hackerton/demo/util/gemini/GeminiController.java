package hackerton.demo.util.gemini;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gemini")
public class GeminiController {
    private final GeminiService geminiService;

    @PostMapping
    public ResponseEntity<String> askGemini(@RequestBody Map<String, String> request){
        String prompt = request.get("prompt");
        String response = geminiService.getGeminiResponse(prompt);
        return ResponseEntity.ok(response);

    }
}
