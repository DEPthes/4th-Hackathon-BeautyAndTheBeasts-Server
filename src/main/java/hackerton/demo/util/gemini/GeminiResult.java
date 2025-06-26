package hackerton.demo.util.gemini;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GeminiResult {

    @Id
    private String uuid;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String requestPrompt;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String gptResponse;

    @Column(nullable = false)
    private String imageUrl;

    private LocalDateTime createdAt;

    public void regenerate(String newResponse, String newImageUrl) {
        this.gptResponse = newResponse;
        this.imageUrl = newImageUrl;
        this.createdAt = LocalDateTime.now();
    }
}