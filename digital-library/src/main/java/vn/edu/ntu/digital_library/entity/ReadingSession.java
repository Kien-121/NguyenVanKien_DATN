package vn.edu.ntu.digital_library.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "reading_sessions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReadingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @CreationTimestamp
    @Column(name = "start_time", updatable = false)
    private LocalDateTime startTime;

    @UpdateTimestamp
    @Column(name = "last_ping_time")
    private LocalDateTime lastPingTime;

    @Column(name = "presigned_url", columnDefinition = "TEXT")
    private String presignedUrl;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}