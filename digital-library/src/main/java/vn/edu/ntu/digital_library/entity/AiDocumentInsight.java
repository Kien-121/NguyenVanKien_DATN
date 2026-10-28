package vn.edu.ntu.digital_library.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_document_insights")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AiDocumentInsight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false, unique = true)
    private Document document;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(name = "extracted_keywords")
    private String extractedKeywords;

    @UpdateTimestamp
    @Column(name = "generated_at")
    private LocalDateTime generatedAt;
}