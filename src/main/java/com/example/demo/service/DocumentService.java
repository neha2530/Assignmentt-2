package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.entity.Document;
import com.example.demo.repository.DocumentRepository;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository pdfDocumentRepository;


    
    public void processCsv(List<List<String>> data) {
    	for(int i =1 ; i < data.size(); i++) {
    		Document doc = new Document();
    		doc.setId(Long.valueOf(data.get(i).get(0)));
    		doc.setFirstName(data.get(i).get(1));
    		doc.setLastName(data.get(i).get(2));
    		doc.setEmail(data.get(i).get(3));
    		doc.setPhone(data.get(i).get(4));
    		pdfDocumentRepository.save(doc);
    	}
    }
    
    public void updateFromCsv(List<List<String>> data) {
    	for(int i =1 ; i < data.size(); i++) {
    	
    		Long id = Long.valueOf(data.get(i).get(0));
    		String firstName = data.get(i).get(1);
    		String lastName = data.get(i).get(2);
    		String email = data.get(i).get(3);
    		String phone = data.get(i).get(4);
    		
    		pdfDocumentRepository.findById(id).ifPresent(doc -> {
    			if (!StringUtils.isEmpty(firstName)) {
    				doc.setFirstName(firstName);
    			}
    			if (!StringUtils.isEmpty(lastName)) {
    				doc.setLastName(lastName);
    			}
    			if (!StringUtils.isEmpty(email)) {
    				doc.setEmail(email);
    			}
    			if (!StringUtils.isEmpty(phone)) {
    				doc.setPhone(phone);
    			}
    			pdfDocumentRepository.save(doc);
    		});
    		
    		
    		
    	}
    }
    
    public void deleteFromCsv(List<List<String>> data) {
    	for(int i =1 ; i < data.size(); i++) {
    		Long id = Long.valueOf(data.get(i).get(0));
  
    		pdfDocumentRepository.deleteById(id);
    	}
    }

    public List<Document> getAll () {
        return pdfDocumentRepository.findAll();
    }
    
    public Optional<Document> getById(Long id) {
        return pdfDocumentRepository.findById(id);
    }
}

    // Additional methods as needed

