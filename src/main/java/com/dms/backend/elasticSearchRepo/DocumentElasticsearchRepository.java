package com.dms.backend.elasticSearchRepo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.dms.backend.entity.DocumentEntity;

@Qualifier("documentElasticsearchRepository")
@Repository
public interface DocumentElasticsearchRepository extends ElasticsearchRepository<DocumentEntity, Integer> {
	 Page<DocumentEntity> findByContentContaining(String keyword, Pageable pageable);
}
