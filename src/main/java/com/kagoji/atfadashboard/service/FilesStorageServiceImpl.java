package com.kagoji.atfadashboard.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;



@Service
public class FilesStorageServiceImpl implements FilesStorageService{
	
	private static final String LOG_DIR = "src/main/resources/static/uploads"; 
	private final Path root = Paths.get("src/main/resources/static/uploads");
	
	@Override
	public void init() {
		try {
		      Files.createDirectories(root);
		      System.out.println("FIleStireage Init");
			} catch (IOException e) {
		      throw new RuntimeException("Could not initialize folder for upload!");
		    }
		
	}

	@Override
	public String save(MultipartFile file) {
		try {
			
	        if (!new File(LOG_DIR).exists()) {
	            new File(LOG_DIR).mkdirs();
	            System.out.println("LOG DIR Created"+LOG_DIR);
	        }
			
			Path savedFilePath = this.root.resolve(file.getOriginalFilename());
	        Files.copy(file.getInputStream(), savedFilePath);
	        System.out.println("FIleStireage Save");

	        // Convert the Path to a String (absolute path) and return it
	        
	        
	       
	        //absolute--> savedFilePath.toAbsolutePath().toString();
	        return "/uploads/" + savedFilePath.getFileName();
	        
	    } catch (Exception e) {
	      if (e instanceof FileAlreadyExistsException) {
	        throw new RuntimeException("A file of that name already exists.");
	      }

	      throw new RuntimeException(e.getMessage());
	    }
		
	}

	@Override
	public Resource load(String filename) {
		try {
		      Path file = root.resolve(filename);
		      Resource resource = new UrlResource(file.toUri());

		      if (resource.exists() || resource.isReadable()) {
		        return resource;
		      } else {
		        throw new RuntimeException("Could not read the file!");
		      }
		    } catch (MalformedURLException e) {
		      throw new RuntimeException("Error: " + e.getMessage());
		    }
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
		
	}

	@Override
	public Stream<Path> loadAll() {
		try {
		      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		    } catch (IOException e) {
		      throw new RuntimeException("Could not load the files!");
		    }
	}

}
