package com.dms.backend.controller;

import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dms.backend.dto.DocumentDTO;
import com.dms.backend.service.DocumentService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DocumentControllerTest.class)
public class QNAContollerTest {
	@Autowired
    private MockMvc mockMvc;

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private QNAController qnaController; 

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(qnaController).build();
    }

    @Test
    void testSearchDocuments() throws Exception {
        String query = "CSS";

        DocumentDTO document1 = new DocumentDTO();
        document1.setFilename("doc1.pdf");
        document1.setAuthor("John Doe");
        document1.setContent("CSS basics tutorial");

        DocumentDTO document2 = new DocumentDTO();
        document2.setFilename("doc2.pdf");
        document2.setAuthor("Jane Doe");
        document2.setContent("Advanced CSS techniques");

        List<DocumentDTO> documents = List.of(document1, document2);

        when(documentService.findDocumentsByKeyword(query)).thenReturn(documents);

        Map<String, String> snippets = new HashMap<>();
        snippets.put("doc1.pdf", "CSS basics tutorial");
        snippets.put("doc2.pdf", "Advanced CSS techniques");

        when(documentService.getDocumentsSnippet(documents)).thenReturn(snippets);

        mockMvc.perform(get("/qna/search")
                        .param("query", query)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testExactSearchDocuments() throws Exception {
        String query = "Java";

        DocumentDTO document1 = new DocumentDTO();
        document1.setFilename("doc1.pdf");
        document1.setAuthor("John Doe");
        document1.setContent("Java programming language tutorial");

        DocumentDTO document2 = new DocumentDTO();
        document2.setFilename("doc2.pdf");
        document2.setAuthor("Jane Doe");
        document2.setContent("Advanced Java techniques");

        List<DocumentDTO> documents = List.of(document1, document2);

        when(documentService.findDocumentsByKeywordDB(query)).thenReturn(documents);

        Map<String, String> snippets = new HashMap<>();
        snippets.put("doc1.pdf", "Java programming language tutorial");
        snippets.put("doc2.pdf", "Advanced Java techniques");

        when(documentService.getDocumentsSnippet(documents)).thenReturn(snippets);

        mockMvc.perform(get("/qna/searchExact")
                        .param("query", query)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}