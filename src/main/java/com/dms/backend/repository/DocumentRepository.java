package com.dms.backend.repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dms.backend.entity.DocumentEntity;

@Primary
@Qualifier("documentRepository")
@Repository
public interface DocumentRepository  extends JpaRepository<DocumentEntity, Integer>{

	List<DocumentEntity> findByContentContainingIgnoreCase(String query);
	
	Page<DocumentEntity> findAll(Pageable pageable);

	Page<DocumentEntity> findByAuthorAndType(String author, String type, Pageable pageable);

	Page<DocumentEntity> findByAuthor(String author, Pageable pageable);

	Page<DocumentEntity> findByType(String type, Pageable pageable);
	
}
