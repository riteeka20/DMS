package com.dms.backend.extractor;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentExtractor {
	String extractContent(MultipartFile file) throws IOException;
}
