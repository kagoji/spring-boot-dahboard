package com.kagoji.atfadashboard.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kagoji.atfadashboard.entity.Album;
import com.kagoji.atfadashboard.model.AlbumModel;

public interface AlbumService {
	
	Album saveAlbum(AlbumModel albumModel);
	Album findByAlbumName(String albumName);
	ArrayList<Album> findAllAlbumList();
	Page<Album> findAllAlbumListWithPagination(Pageable pageable); 
	Page<Album> searchAlbumsByKeyword(String keyword, Pageable pageable);
	void deleteById(String id);
	Optional<Album> findAlbum(String id);
	Album updateAlbum(String id, AlbumModel albumModel);
	public void updateAlbumStatus(String id, Integer status);
}
