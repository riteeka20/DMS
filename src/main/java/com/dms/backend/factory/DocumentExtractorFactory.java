package com.dms.backend.factory;

import com.dms.backend.extractor.DocumentExtractor;
import com.dms.backend.extractor.DocxExtractor;
import com.dms.backend.extractor.ExcelExtractor;
import com.dms.backend.extractor.PdfExtractor;
import com.dms.backend.extractor.TextExtractor;


public class DocumentExtractorFactory {
	
	public static DocumentExtractor getExtractor(String fileType) {
        if (fileType == null) {
            throw new IllegalArgumentException("File type cannot be null");
        }
        
        switch (fileType) {
            case "application/pdf":
                return new PdfExtractor();
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                return new DocxExtractor();
            case "application/vnd.ms-excel":
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                return new ExcelExtractor();
            case "text/plain":
                return new TextExtractor();
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }
	
}
