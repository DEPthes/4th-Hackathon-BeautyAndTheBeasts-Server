package hackerton.demo.util.gemini.Image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class GeminiImageService {

    private final GeminiImageRepository imageRepository;
    private final Random random = new Random();

    public String getRandomImageUrl() {
        List<GeminiImage> images = imageRepository.findAll();
        if (images.isEmpty()) {
            throw new IllegalStateException("등록된 이미지가 없습니다.");
        }
        GeminiImage selected = images.get(random.nextInt(images.size()));
        return selected.getImageUrl();
    }
}