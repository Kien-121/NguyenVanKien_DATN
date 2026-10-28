package vn.edu.ntu.digital_library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.ntu.digital_library.entity.ReadingSession;
import java.util.List;
import java.util.Optional;

public interface ReadingSessionRepository extends JpaRepository<ReadingSession, Long> {
    List<ReadingSession> findByUserId(Long userId);
    Optional<ReadingSession> findByUserIdAndDocumentId(Long userId, Long documentId);
}