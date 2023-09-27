package com.kagoji.atfadashboard.model;

import lombok.Data;


public class ImageModel {
	
	private String imageName;
    private String imageUrl;
    
	public ImageModel(String imageName, String imageUrl) {
		super();
		this.imageName = imageName;
		this.imageUrl = imageUrl;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
    
}
