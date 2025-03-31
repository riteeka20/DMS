package com.dms.backend.extractor;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

public class PdfExtractor implements DocumentExtractor {

	@Override
	public String extractContent(MultipartFile file) throws IOException {
		 PDDocument document = PDDocument.load(file.getInputStream());
	     PDFTextStripper stripper = new PDFTextStripper();
	     String content = stripper.getText(document);
	     document.close();
	     return content;
	}

}
