package com.kagoji.atfadashboard.service;



import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
	public Album saveAlbum(AlbumModel categoryModel) {
		Album category = new Album();
		category.setAlbumName(categoryModel.getAlbumName());
		String slug = StringUtils.safeTrim(categoryModel.getAlbumName()).toLowerCase().replace(" ", "-");
		category.setAlbumSlug(slug);
		category.setAlbumSlug(slug);
		category.setCreatedAt(new Date());
		albumRepository.save(category);
		
		return category;
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
	
	
}
