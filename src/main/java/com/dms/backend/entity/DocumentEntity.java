package com.dms.backend.entity;

import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="files")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "documententity")
public class DocumentEntity {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY) 
 private int id;
 @Column(columnDefinition = "TEXT") 
 private String content;
 private String type;
 private String filename;
 private String author;


}
