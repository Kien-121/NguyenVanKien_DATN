package vn.edu.ntu.digital_library.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "document_files")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DocumentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "minio_object_name", unique = true, nullable = false)
    private String minioObjectName;

    @Column(name = "file_format", length = 20)
    private String fileFormat;

    @Column(name = "file_size")
    private Long fileSize;

    @CreationTimestamp
    @Column(name = "uploaded_at", updatable = false)
    private LocalDateTime uploadedAt;
}