package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Document;
import com.example.demo.repository.DocumentRepository;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository pdfDocumentRepository;

    public void savePdfDocument(String documentName,byte[] pdfData) throws IOException {
        Document pdfDocument = new Document(documentName, pdfData);
        pdfDocumentRepository.save(pdfDocument);
        this.writeInputStreamToFile(pdfData, documentName);
        
    }

    public List<Document> getAll () {
        return pdfDocumentRepository.findAll();
    }
    
    
    public  void writeInputStreamToFile(byte[] pdfData, String fileName) throws IOException {
        // Create the file
    	InputStream inputStream = new ByteArrayInputStream(pdfData);
	 Path outputPath = Paths.get("src/main/resources/output");
        outputPath.toFile().mkdirs();
        File file = new File(outputPath.toFile(), fileName);

        // Ensure parent directories exist
        file.getParentFile().mkdirs();

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            // Read from the InputStream and write to the FileOutputStream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    // Additional methods as needed
}
