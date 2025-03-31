package com.dms.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.dms.backend.factory.DocumentExtractorFactory;

@Configuration
@EnableJpaRepositories("com.dms.backend.repository")
@EnableElasticsearchRepositories("com.dms.backend.elasticSearchRepo")
public class DocumentConfiguration {

    @Bean
    DocumentExtractorFactory getDocumentExtractorFactory() {
		return new DocumentExtractorFactory();
	}

}
