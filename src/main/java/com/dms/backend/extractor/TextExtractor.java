package com.dms.backend.extractor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.web.multipart.MultipartFile;

public class TextExtractor implements DocumentExtractor {

	@Override
	public String extractContent(MultipartFile file) throws IOException {
		 return new String(file.getBytes(), StandardCharsets.UTF_8);
	}

}
