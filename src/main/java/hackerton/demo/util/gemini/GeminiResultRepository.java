package hackerton.demo.util.gemini;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeminiResultRepository extends JpaRepository<GeminiResult, String> {
    Optional<GeminiResult> findByUuid(String uuid);
}
