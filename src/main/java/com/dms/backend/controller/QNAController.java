package com.dms.backend.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dms.backend.dto.DocumentDTO;
import com.dms.backend.service.DocumentService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/qna")
public class QNAController {
	
	@Autowired
	DocumentService documentService;
	
	 @Operation(summary = "Search documents", description = "Search for documents based on keyword(may include partial matches)")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Documents found successfully"),
	    })
	@GetMapping("/search")
	public ResponseEntity<Map<String, String> > searchDocuments(@RequestParam String query) {
		List<DocumentDTO> documents = documentService.findDocumentsByKeyword(query);
        Map<String, String> snippets = documentService.getDocumentsSnippet(documents);
        return ResponseEntity.ok(snippets);
	}
	
	@Operation(summary = "Search documents by exact match keyword", description = "Search for documents using an exact match keyword")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Documents found successfully"),
    })
	@GetMapping("/searchExact")
	public ResponseEntity<Map<String, String>> ExactSearchDocuments(@RequestParam String query) {
		List<DocumentDTO> documents = documentService.findDocumentsByKeywordDB(query);
		Map<String, String> snippets = documentService.getDocumentsSnippet(documents);
        return ResponseEntity.ok(snippets);
	}
	
}
