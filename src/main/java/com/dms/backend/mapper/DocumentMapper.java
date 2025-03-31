package com.dms.backend.mapper;

import org.springframework.stereotype.Component;

import com.dms.backend.dto.DocumentDTO;
import com.dms.backend.entity.DocumentEntity;

@Component
public class DocumentMapper {

    // Convert DocumentEntity to DocumentDTO
    public static DocumentDTO entityToDto(DocumentEntity documentEntity) {
        if (documentEntity == null) {
            return null;
        }
        return new DocumentDTO(
                documentEntity.getId(),
                documentEntity.getContent(),
                documentEntity.getType(),
                documentEntity.getFilename(),
                documentEntity.getAuthor()
        );
    }

    // Convert DocumentDTO to DocumentEntity
    public static DocumentEntity dtoToEntity(DocumentDTO documentDTO) {
        if (documentDTO == null) {
            return null;
        }
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setId(documentDTO.getId());
        documentEntity.setContent(documentDTO.getContent());
        documentEntity.setType(documentDTO.getType());
        documentEntity.setFilename(documentDTO.getFilename());
        documentEntity.setAuthor(documentDTO.getAuthor());
        return documentEntity;
    }
}
