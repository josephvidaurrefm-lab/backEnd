package com.Proyecto.backEnd.controller;


import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Proyecto.backEnd.service.StorageService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("media")
@AllArgsConstructor
public class MediaController {

	private final StorageService storageService;
	
	private final HttpServletRequest request;
	
	@PostMapping("upload")
	public Map<String, String> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
	    String path = storageService.store(multipartFile);
	    String host = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	    String url = ServletUriComponentsBuilder
	        .fromHttpUrl(host)
	        .path("/media/")
	        .path(path)
	        .toUriString();
	    

	    return Map.of(
	        "url", url,
	        "message", "Se guardó la foto correctamente"
	    );
	}

	
	@GetMapping("{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException{
		
		Resource file = storageService.loadAsResourese(filename);
		String contentType = Files.probeContentType(file.getFile().toPath());
		
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_TYPE, contentType)
				.body(file);
		
	}
	
}
