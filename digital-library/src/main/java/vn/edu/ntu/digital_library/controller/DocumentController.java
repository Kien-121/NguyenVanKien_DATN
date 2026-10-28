package vn.edu.ntu.digital_library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.ntu.digital_library.entity.Category;
import vn.edu.ntu.digital_library.entity.Document;
import vn.edu.ntu.digital_library.entity.DocumentFile;
import vn.edu.ntu.digital_library.repository.CategoryRepository;
import vn.edu.ntu.digital_library.repository.DocumentFileRepository;
import vn.edu.ntu.digital_library.repository.DocumentRepository;
import vn.edu.ntu.digital_library.service.MinioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final DocumentFileRepository documentFileRepository;
    private final CategoryRepository categoryRepository;
    private final MinioService minioService;

    @GetMapping
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<?> uploadDocument(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam(value = "publisher", required = false) String publisher,
            @RequestParam(value = "publishYear", required = false) Integer publishYear,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("file") MultipartFile file
    ) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyên ngành có ID: " + categoryId));

        // 1. Đẩy file PDF lên MinIO
        String minioObjectName = minioService.uploadFile(file, "pdfs");

        // 2. Lưu thông tin sách vào bảng documents (MySQL)
        Document document = Document.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .publishYear(publishYear)
                .category(category)
                .viewCount(0)
                .build();
        Document savedDoc = documentRepository.save(document);

        // 3. Lưu đường dẫn file vào bảng document_files (MySQL)
        DocumentFile docFile = DocumentFile.builder()
                .document(savedDoc)
                .fileName(file.getOriginalFilename())
                .minioObjectName(minioObjectName)
                .fileFormat("PDF")
                .fileSize(file.getSize())
                .build();
        documentFileRepository.save(docFile);

        // 4. Tạo link đọc thử nghiệm có hạn 15 phút
        String readUrl = minioService.getPresignedUrl(minioObjectName, 15);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Upload tài liệu thành công!");
        response.put("documentId", savedDoc.getId());
        response.put("minioObjectName", minioObjectName);
        response.put("presignedReadUrl", readUrl);

        return ResponseEntity.ok(response);
    }
}