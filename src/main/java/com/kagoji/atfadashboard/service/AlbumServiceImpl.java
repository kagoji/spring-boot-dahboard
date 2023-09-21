package com.kagoji.atfadashboard.service;



import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.kagoji.atfadashboard.entity.Album;
import com.kagoji.atfadashboard.model.AlbumModel;
import com.kagoji.atfadashboard.repository.AlbumRepository;
import com.mysql.cj.util.StringUtils;

@Service
public class AlbumServiceImpl implements AlbumService{
	
	@Autowired
	private AlbumRepository albumRepository;
	
	@Override
	public Album saveAlbum(AlbumModel albumModel) {
		Album album = new Album();
		album.setAlbumName(albumModel.getAlbumName());
		String slug = StringUtils.safeTrim(albumModel.getAlbumName()).toLowerCase().replace(" ", "-");
		album.setAlbumSlug(slug);
		album.setAlbumSlug(slug);
		album.setCreatedAt(new Date());
		albumRepository.save(album);
		
		return album;
	}
	
	@Override
	public Album findByAlbumName(String albumName) {
		return albumRepository.findByAlbumName(albumName);
	}

	@Override
	public ArrayList<Album> findAllAlbumList() {
		
		return  (ArrayList<Album>) albumRepository.findAll();
	}

	@Override
	public Page<Album> findAllAlbumListWithPagination(Pageable pageable) {
		// TODO Auto-generated method stub
		return albumRepository.findAll(pageable);
	}

	@Override
	public Page<Album> searchAlbumsByKeyword(String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return albumRepository.findByAlbumNameContainingIgnoreCase(keyword, pageable);
	}

	@Override
	public void deleteById(String id) {
		albumRepository.deleteById(id);
		
	}

	@Override
	public Optional<Album> findAlbum(String id) {
		// TODO Auto-generated method stub
		return albumRepository.findById(id);
	}

	@Override
	public Album updateAlbum(String id, AlbumModel albumModel) {
		Album updateAlbum = albumRepository.getById(id);
		updateAlbum.setAlbumName(albumModel.getAlbumName());
		String slug = StringUtils.safeTrim(albumModel.getAlbumName()).toLowerCase().replace(" ", "-");
		updateAlbum.setAlbumSlug(slug);
		
		albumRepository.save(updateAlbum);
		
		return updateAlbum;
	}

	@Override
	public void updateAlbumStatus(String id, Integer status) {
		Album updateAlbum = albumRepository.getById(id);
		updateAlbum.setAlbumStatus(status);
		albumRepository.save(updateAlbum);
		
	}

	
	
}
