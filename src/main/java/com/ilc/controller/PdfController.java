package com.ilc.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

import java.nio.file.Paths;

@Controller
public class PdfController {
	
	 private final Path rootLocation = Paths.get("C:\\Users\\Admin\\Documents\\upload-dir");


	 @GetMapping("/pdf-viewer")
	 public void showPdf(@RequestParam("filePath") String filePath, HttpServletResponse response) {
	     try {
	         Path path = Paths.get(filePath);
	         if (Files.exists(path)) {
	             response.setContentType("application/pdf");
	             response.addHeader("Content-Disposition", "inline; filename=" + path.getFileName().toString());
	             Files.copy(path, response.getOutputStream());
	             response.getOutputStream().flush();
	         } else {
	             throw new FileNotFoundException("File not found: " + filePath);
	         }
	     } catch (IOException ex) {
	         ex.printStackTrace();
	     }
	 }

	 
	  @PostMapping("/upload")
	    public String uploadPdf(@RequestParam("file") MultipartFile file, Model model) {
	        if (file.isEmpty()) {
	            model.addAttribute("message", "Please select a PDF file to upload.");
	            return "index";
	        }

	        try {
	            // Crear el directorio si no existe
	            if (!Files.exists(rootLocation)) {
	                Files.createDirectories(rootLocation);
	            }

	            // Guardar el archivo en la ubicación de destino
	            Path destinationFile = rootLocation.resolve(file.getOriginalFilename());
	            Files.copy(file.getInputStream(), destinationFile);
	            model.addAttribute("filename", file.getOriginalFilename());

	        } catch (IOException e) {
	            model.addAttribute("message", "An error occurred while processing the PDF file.");
	            e.printStackTrace();
	        }

	        return "result";
	    }

	    @GetMapping("/files/{filename:.+}")
	    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
	        try {
	            Path file = rootLocation.resolve(filename);
	            Resource resource = new UrlResource(file.toUri());
	            return ResponseEntity.ok().body(resource);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.badRequest().build();
	        }
	    
    }
}