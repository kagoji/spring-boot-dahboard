package com.kagoji.atfadashboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kagoji.atfadashboard.entity.Image;
import com.kagoji.atfadashboard.model.ImageModel;
import com.kagoji.atfadashboard.repository.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService{
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Override
	public Image saveImage(ImageModel imageModel) {
		
		Image image = new Image();
		image.setImageName(imageModel.getImageName());
		image.setImageUrl(imageModel.getImageUrl());
		
		imageRepository.save(image);
		return image;
	}

	@Override
	public Page<Image> findAllAlbumListWithPagination(Pageable pageable) {
		// TODO Auto-generated method stub
		return imageRepository.findAll(pageable);
	}

	@Override
	public Page<Image> searchAlbumsByKeyword(String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return imageRepository.findByImageNameContainingIgnoreCase(keyword, pageable);
	}


}
