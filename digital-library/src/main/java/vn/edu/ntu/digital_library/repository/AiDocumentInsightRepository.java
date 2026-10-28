package vn.edu.ntu.digital_library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.ntu.digital_library.entity.AiDocumentInsight;
import java.util.Optional;

public interface AiDocumentInsightRepository extends JpaRepository<AiDocumentInsight, Long> {
    Optional<AiDocumentInsight> findByDocumentId(Long documentId);
}