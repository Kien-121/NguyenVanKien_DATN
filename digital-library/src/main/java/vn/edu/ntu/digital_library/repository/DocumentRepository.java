package vn.edu.ntu.digital_library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.ntu.digital_library.entity.Document;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByCategoryId(Integer categoryId);
    List<Document> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
}