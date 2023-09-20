package com.kagoji.atfadashboard.service;

import java.util.ArrayList;

import com.kagoji.atfadashboard.entity.Album;
import com.kagoji.atfadashboard.model.AlbumModel;

public interface AlbumService {
	
	Album saveAlbum(AlbumModel categoryModel);
	Album findByAlbumName(String albumName);
	ArrayList<Album> findAllAlbumList();
}
