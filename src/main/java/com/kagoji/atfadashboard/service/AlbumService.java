package com.kagoji.atfadashboard.service;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kagoji.atfadashboard.entity.Album;
import com.kagoji.atfadashboard.model.AlbumModel;

public interface AlbumService {
	
	Album saveAlbum(AlbumModel categoryModel);
	Album findByAlbumName(String albumName);
	ArrayList<Album> findAllAlbumList();
	Page<Album> findAllAlbumListWithPagination(Pageable pageable); 
	Page<Album> searchAlbumsByKeyword(String keyword, Pageable pageable);
}
