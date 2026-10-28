package vn.edu.ntu.digital_library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.ntu.digital_library.entity.DocumentFile;
import java.util.List;
import java.util.Optional;

public interface DocumentFileRepository extends JpaRepository<DocumentFile, Long> {
    List<DocumentFile> findByDocumentId(Long documentId);
    Optional<DocumentFile> findByMinioObjectName(String minioObjectName);
}