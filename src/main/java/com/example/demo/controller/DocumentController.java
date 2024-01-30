package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Document;
import com.example.demo.service.DocumentService;

@RestController
public class DocumentController {
	
	@Autowired
	DocumentService documentService;
	
	@GetMapping("getData")
	public List<String> getDocuments() {
		return documentService.getAll()
				.stream()
				.map(x -> x.getDocumentName()).toList();
	}
	
	
	 public  String pdfByteArrayToHex(byte[] pdfByteArray) {
	        StringBuilder result = new StringBuilder();
	        for (byte b : pdfByteArray) {
	            result.append(String.format("%02X", b));
	        }
	        return result.toString();
	    }

}
