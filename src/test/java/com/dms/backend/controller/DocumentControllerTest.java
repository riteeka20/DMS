package com.dms.backend.controller;

import com.dms.backend.dto.DocumentDTO;
import com.dms.backend.service.DocumentService;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.io.IOException;
import java.util.Arrays;

@WebMvcTest(DocumentControllerTest.class)
public class DocumentControllerTest {
	 
	@Mock 
	 private DocumentService documentService; 

	@Rule
    public ExpectedException thrown= ExpectedException.none();
	
    @InjectMocks
    private DocumentController documentController; 
    
    @Autowired
    private MockMvc mockMvc;

    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(documentController).build();
    }
  

    @Test
    void testUploadDocument() throws Exception {
        String author = "John Doe";
        String content = "This is a test document content.";
        
        DocumentDTO mockDocumentDTO = new DocumentDTO();
        mockDocumentDTO.setFilename("testdocument.txt");
        mockDocumentDTO.setAuthor(author);
        mockDocumentDTO.setContent(content);
        mockDocumentDTO.setType("text/plain");

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "testdocument.txt", "text/plain", "This is a test document.".getBytes()
        );

        when(documentService.processDocument(any(), any())).thenReturn(mockDocumentDTO);

        mockMvc.perform(multipart("/document/upload")
                        .file(mockFile)
                        .param("author", author)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.filename").value("testdocument.txt"))
                .andExpect(jsonPath("$.author").value("John Doe"))
                .andExpect(jsonPath("$.content").value("This is a test document content."))
                .andExpect(jsonPath("$.type").value("text/plain"));
    }
    
    @Test
    void testUploadDocumentWithInvalidFile(){
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "invalidfile.xyz", "application/octet-stream", "Invalid content".getBytes()
        );
       
		try {
			 when(documentService.processDocument(any(), any())).thenThrow(new IOException("Invalid file format"));

			 ResultActions andExpect = mockMvc.perform(multipart("/document/upload")
			                .file(mockFile)
			                .contentType(MediaType.MULTIPART_FORM_DATA));
		} catch (Exception e) {
			 thrown.expect(IOException.class);
			 
		}
    }
    
    @Test
    void testFilterDocuments() throws Exception {
        String author = "John Doe";
        String type = "pdf";
        DocumentDTO document1 = new DocumentDTO();
        document1.setFilename("doc1.pdf");
        document1.setAuthor("John Doe");
        document1.setType("application/pdf");

        DocumentDTO document2 = new DocumentDTO();
        document2.setFilename("doc2.pdf");
        document2.setAuthor("John Doe");
        document2.setType("application/pdf");

        Page<DocumentDTO> mockPage = new PageImpl<>(Arrays.asList(document1, document2));
        when(documentService.filterDocuments(any(), any(), any())).thenReturn(mockPage);

        mockMvc.perform(get("/document/filter")
                        .param("author", author)
                        .param("type", type)
                        .param("page", "0")
                        .param("size", "1")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

    }
}

