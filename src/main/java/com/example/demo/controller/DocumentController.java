package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Document;
import com.example.demo.service.DocumentService;

@RestController
public class DocumentController {
	
	@Autowired
	DocumentService documentService;
	
	@GetMapping("getData")
	public List<Document> getDocuments() {
		return documentService.getAll();
	}
		@GetMapping("getData/{id}")
		public Optional<Document> getDocumentsById(@PathVariable Long id) {
			 Optional<Document> optionalDocument = documentService.getById(id);
			 return optionalDocument;
}
}
