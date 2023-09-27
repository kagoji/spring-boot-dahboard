package com.kagoji.atfadashboard.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kagoji.atfadashboard.entity.Image;
import com.kagoji.atfadashboard.model.ImageModel;

public interface ImageService {

	Image saveImage(ImageModel imageModel);

	Page<Image> findAllAlbumListWithPagination(Pageable pageable);

	Page<Image> searchAlbumsByKeyword(String keyword, Pageable pageable);

}
