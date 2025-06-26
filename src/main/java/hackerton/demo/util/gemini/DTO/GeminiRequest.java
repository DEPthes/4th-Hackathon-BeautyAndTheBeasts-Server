package hackerton.demo.util.gemini.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GeminiRequest {
    @Schema(description = "Gemini에게 보낼 프롬프트", example = "오늘 버스를 놓쳤는데, 마침 아빠가 차를 태워주셔서 너무 감사했어. 회시에 10분정도 지각해서 부장님한테 엄청 혼났어.")
    private String prompt;
}
