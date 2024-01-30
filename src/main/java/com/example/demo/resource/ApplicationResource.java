package com.example.demo.resource;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.minio.MinioConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.service.DocumentService;
@Component
public class ApplicationResource extends RouteBuilder {

	@Autowired private DocumentService service;

	@Override
	public void configure() throws Exception {
		//read
		  from("minio:input-test?autoCreateBucket=true")
		  .log("Received a message ") .process(exchange -> { byte[] bytes =
		  exchange.getIn().getBody(byte[].class);
		  String fileName = exchange.getIn().getHeader("CamelMinioObjectName", String.class);
		  service.savePdfDocument(fileName, bytes);
		  System.out.println(exchange); });
		  from("minio:input-test")
		  .log("deleted")
		  .to("minio://delete-test?accessKey=minioadmin&secretKey=minioadmin");
		  
		 	 
		  from("minio:update-test?autoCreateBucket=true")
		  .log("Received a message from Minio") .process(exchange -> { byte[] bytes =
		  exchange.getIn().getBody(byte[].class);
		  service.savePdfDocument("Resume"+System.currentTimeMillis()+".pdf", bytes);
		  System.out.println(exchange); });
		  
		  //.to("minio://delete-test?accessKey=minioadmin&secretKey=minioadmin");
		 
         
		   
	   from("minio:delete-test?autoCreateBucket=true")
           .log("Received a message from delete")
           .process(exchange -> {
        	   byte[] bytes = exchange.getIn().getBody(byte[].class);
        	   service.savePdfDocument("Resume"+System.currentTimeMillis()+".pdf", bytes);
        	   System.out.println(exchange);
           })
           
         .to("log:MinioLog");
		   
			//upload
			  from("file:src/main/resources/input?recursive=true")
			  .log("Uploading file to MinIO") 
		  .setHeader(MinioConstants.OBJECT_NAME,constant("file"+System.currentTimeMillis()+".pdf"))
			 .to("minio://input-test?accessKey=minioadmin&secretKey=minioadmin");
			  
			  //updated
			  from("file:src/main/resources/updated?recursive=true")
			  .log("Updated") 
		  .setHeader(MinioConstants.OBJECT_NAME,constant("file"+System.currentTimeMillis()+".pdf"))
			 .to("minio://update-test?accessKey=minioadmin&secretKey=minioadmin");
			
	}
	   
	}	



				/*
	}			 * from("direct:getData") .process(ex -> { List<Document> docs
	}			 * =service.getAll(); ex.getMessage().setBody(docs); });
				 */
			 
		 
			
			// TODO Auto-generated method stub
				
			
	    
			
	
		
	


