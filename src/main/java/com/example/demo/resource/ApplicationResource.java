package com.example.demo.resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.minio.MinioConstants;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.service.DocumentService;
@Component
public class ApplicationResource extends RouteBuilder {

	@Autowired private DocumentService service;

	@Override
	public void configure() throws Exception {
		CsvDataFormat csvDataFormat = new CsvDataFormat();
        csvDataFormat.setUseMaps(false); // 
		//read
		  from("minio:input-test?autoCreateBucket=true")
		  .log("Received a message ") 
		
		  .unmarshal(csvDataFormat)
		  .process(ex -> {
			  List<List<String>> l =  (List<List<String>>) ex.getIn().getBody(List.class);
			  
			  service.processCsv(l);
			 
		  })
		  .log("Recieved");
		
		  //update
		  from("minio:update-test?autoCreateBucket=true")
		  .log("updated ") 
		
		  .unmarshal(csvDataFormat)
		  .process(ex -> {
			  List<List<String>> l =  (List<List<String>>) ex.getIn().getBody(List.class);
			  
			  service.updateFromCsv(l);
			 
		  })
		  .log("Recieved");
		  
		  //delete
		  from("minio:delete-test?autoCreateBucket=true")
		  .log("deleted ") 
		
		  .unmarshal(csvDataFormat)
		  .process(ex -> {
			  List<List<String>> l =  (List<List<String>>) ex.getIn().getBody(List.class);
	
			  service.deleteFromCsv(l);
			 
		  })
		  .log("Recieved");
	
		  //upload 
		     from("file:src/main/resources/input?recursive=true")
			 .log("Uploading file to MinIO")
			 .setHeader(MinioConstants.OBJECT_NAME,constant("file"+System.currentTimeMillis()+".pdf"))
			 .to("minio://input-test?accessKey=minioadmin&secretKey=minioadmin");
//			  
//			  //updated
//			 from("file:src/main/resources/updated?recursive=true")
//			 .log("Updated") 
//			 .setHeader(MinioConstants.OBJECT_NAME,constant("file"+System.
//			 currentTimeMillis()+".pdf"))
//			 .to("minio://update-test?accessKey=minioadmin&secretKey=minioadmin");
	
			
		}
	     
	}
	   
		

		 
			
			// TODO Auto-generated method stub
				
			
	    
			
	
		
	


