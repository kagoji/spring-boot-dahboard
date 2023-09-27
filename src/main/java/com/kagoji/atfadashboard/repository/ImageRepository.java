package com.kagoji.atfadashboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.kagoji.atfadashboard.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
	
	Page<Image> findByImageNameContainingIgnoreCase(String keyword, Pageable pageable);
}
