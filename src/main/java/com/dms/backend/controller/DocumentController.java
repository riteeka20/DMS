package com.dms.backend.controller;


import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.dms.backend.dto.DocumentDTO;
import com.dms.backend.service.DocumentService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/document")
public class DocumentController {

	@Autowired
	private DocumentService documentService;
	
	@Operation(summary = "Get filtered documents", description = "Fetches a list of all documents with pagination based on filter provided")
	 @ApiResponses(value = {
		        @ApiResponse(responseCode = "200", description = "List of documents retrieved successfully"),
		    })
	@GetMapping("/filter")
    public ResponseEntity<Page<DocumentDTO>> filterDocuments(
        @RequestParam(required = false) String author,
        @RequestParam(required = false) String type,
        Pageable pageable) {
        
        Page<DocumentDTO> documents = documentService.filterDocuments(author, type, pageable);
        return ResponseEntity.ok(documents);
    }
	
	 @Operation(summary = "Upload a document", description = "Uploads a new document to the system")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "201", description = "Document uploaded successfully"),
	        @ApiResponse(responseCode = "400", description = "Invalid file format")
	    })
	 @PostMapping("/upload")
	 public ResponseEntity<DocumentDTO> uploadDocument(@RequestParam MultipartFile file,
	                                                   @RequestParam(required = false) String author) throws IOException {
            DocumentDTO document = documentService.processDocument(file,author);
            return ResponseEntity.ok(document);
    }
}
