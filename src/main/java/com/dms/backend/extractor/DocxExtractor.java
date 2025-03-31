package com.dms.backend.extractor;

import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.web.multipart.MultipartFile;

public class DocxExtractor implements DocumentExtractor{

	@Override
	public String extractContent(MultipartFile file) throws IOException {
		
		try (XWPFDocument docx = new XWPFDocument(file.getInputStream())) {
            StringBuilder content = new StringBuilder();
            List<XWPFParagraph> paragraphs = docx.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                content.append(paragraph.getText()).append("\n");
            }
            return content.toString();
        }
	}

}
