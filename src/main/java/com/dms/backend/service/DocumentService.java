package com.dms.backend.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.dms.backend.dto.DocumentDTO;

public interface DocumentService {
	public DocumentDTO processDocument(MultipartFile file, String author) throws IOException;
	public void saveDocument(DocumentDTO document);
	public List<DocumentDTO> findDocumentsByKeyword(String query);
	public Map<String, String> getDocumentsSnippet(List<DocumentDTO> documents);
	public Page<DocumentDTO> filterDocuments(String author, String type, Pageable pageable);
	public List<DocumentDTO> findDocumentsByKeywordDB(String query);
}
