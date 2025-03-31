package com.dms.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDTO {

    private int id;
    private String content;
    private String type;
    private String filename;
    private String author;


}
