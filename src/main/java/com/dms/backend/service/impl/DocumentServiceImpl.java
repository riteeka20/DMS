package com.dms.backend.service.impl;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dms.backend.dto.DocumentDTO;
import com.dms.backend.entity.DocumentEntity;
import com.dms.backend.extractor.DocumentExtractor;
import com.dms.backend.factory.DocumentExtractorFactory;
import com.dms.backend.mapper.DocumentMapper;
import com.dms.backend.repository.DocumentRepository;
import com.dms.backend.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService{
	@Autowired
	private DocumentRepository docRepo;
	
	 @Autowired
	 private ElasticsearchOperations elasticsearchOperations;
	
	 
	 @Override
	public DocumentDTO processDocument(MultipartFile file, String author) throws IOException {
		
		DocumentExtractor docExtractor = getDocumentExtractor(file);
		String content = docExtractor.extractContent(file);
		DocumentDTO entity = new DocumentDTO(0, content, file.getContentType(), file.getOriginalFilename(), author);
		
		saveDocument(entity);
		return entity;
	}
	
	private DocumentExtractor getDocumentExtractor(MultipartFile file) {
		return DocumentExtractorFactory.getExtractor(file.getContentType());
	}
	
	@Async
	@Override
	public void saveDocument(DocumentDTO document) {
		DocumentEntity documentEntity = DocumentMapper.dtoToEntity(document);
		
		CompletableFuture.completedFuture(docRepo.save(documentEntity));
		CompletableFuture.completedFuture(elasticsearchOperations.save(documentEntity));
	}
	
	public List<DocumentDTO> searchDocuments(String searchTerm) {
        Criteria criteria = Criteria.where("content").matches(searchTerm);
        CriteriaQuery query = new CriteriaQuery(criteria);
        SearchHits<DocumentEntity> searchHits = elasticsearchOperations.search(query, DocumentEntity.class);
        
        List<DocumentEntity> searchedEntity = searchHits.stream()
                         .map(hit -> hit.getContent())
                         .collect(Collectors.toList());
        
        List<DocumentDTO> resultList =  searchedEntity.stream()
                .map(DocumentMapper::entityToDto) 
                .collect(Collectors.toList());
        return resultList;
    }
	
	@Override
	public List<DocumentDTO> findDocumentsByKeyword(String query) {
		return searchDocuments(query);
	}

	@Override
	public Map<String, String> getDocumentsSnippet(List<DocumentDTO> documents) {
		return documents.stream().collect(Collectors.toMap(DocumentDTO::getFilename,doc->extractSnip(doc.getContent())));
	}
	
	private String extractSnip(String s) {
		String s1= s.chars().limit(Math.min(s.length(), 100))
				.mapToObj(c -> (char) c)         
                .map(String::valueOf) 
                .collect(Collectors.joining("")); 
		return s1;
	}

	@Override
	public Page<DocumentDTO> filterDocuments(String author, String type,Pageable pageable) {
		
		Page<DocumentEntity> reultEntity = null;
		
		if(author != null && type != null ) {
			reultEntity = docRepo.findByAuthorAndType(author, type, pageable);
        }else if(author != null ) {
        	reultEntity = docRepo.findByAuthor(author,pageable);
        }else if(type != null ) {
        	reultEntity = docRepo.findByType(type,pageable);
        }
        
		reultEntity = docRepo.findAll(pageable);
		
		List<DocumentDTO> documentDTOList = reultEntity.stream()
                .map(DocumentMapper::entityToDto)  
                .collect(Collectors.toList());

        return new PageImpl<>(documentDTOList, reultEntity.getPageable(), reultEntity.getTotalElements());
		
		
	}

	@Override
	public List<DocumentDTO> findDocumentsByKeywordDB(String query) {
		List<DocumentEntity> resultEntity = docRepo.findByContentContainingIgnoreCase(query);
		
		List<DocumentDTO> resultDTO =  resultEntity.stream()
                .map(DocumentMapper::entityToDto) 
                .collect(Collectors.toList());
		return resultDTO;
	}

	
}
