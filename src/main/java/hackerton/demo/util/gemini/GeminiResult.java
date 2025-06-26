package hackerton.demo.util.gemini;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
}