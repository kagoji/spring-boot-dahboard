package com.kagoji.atfadashboard.repository;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kagoji.atfadashboard.entity.Album;



@Repository
public interface AlbumRepository extends JpaRepository<Album, String>{
	Album findByAlbumName(String albumName);
	Page<Album> findByAlbumNameContainingIgnoreCase(String keyword, Pageable pageable);
}
