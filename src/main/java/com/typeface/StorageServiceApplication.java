package com.typeface;

import com.typeface.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@SpringBootApplication
@RestController
@RequestMapping("/files")
public class StorageServiceApplication {

	@Autowired
	private StorageService service;

	@PostMapping("/upload")
	public ResponseEntity<?> uploadImage(@RequestParam("file")MultipartFile file) {
		String fileId = service.uploadImage(file);
		return ResponseEntity.status(HttpStatus.OK)
				.body(fileId);
	}

	@GetMapping("/{fileId}")
	public ResponseEntity<?> downloadImage(@PathVariable Integer fileId) {
		byte[] imageData=service.downloadImage(fileId.toString());
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.parseMediaType(service.getMediaType(fileId.toString())))
				.body(imageData);
	}


	@PutMapping("/{fileId}")
	public ResponseEntity<?> updateFile(@PathVariable String fileId, @RequestParam("file")MultipartFile file) {
		String uploadImage = service.updateFile(Long.parseLong(fileId), file);
		return ResponseEntity.status(HttpStatus.OK)
				.body(uploadImage);
	}

	@DeleteMapping("/{fileId}")
	public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileId) {
		service.deleteFile(fileId);
		return ResponseEntity.ok("Success");

	}

	public static void main(String[] args) {
		SpringApplication.run(StorageServiceApplication.class, args);
	}

}
