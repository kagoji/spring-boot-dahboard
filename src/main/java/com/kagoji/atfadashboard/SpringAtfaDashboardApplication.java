package com.kagoji.atfadashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kagoji.atfadashboard.service.FilesStorageService;

import jakarta.annotation.Resource;

@SpringBootApplication
public class SpringAtfaDashboardApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringAtfaDashboardApplication.class, args);
	}


}
