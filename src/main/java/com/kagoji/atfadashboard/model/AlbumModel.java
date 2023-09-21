package com.kagoji.atfadashboard.model;



public class AlbumModel {
	
	private String albumName;
	private String albumSlug;
	
	
	public AlbumModel(String albumName, String albumSlug) {
		super();
		this.albumName = albumName;
		this.albumSlug = albumSlug;
	}


	public String getAlbumName() {
		return albumName;
	}


	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}


	public String getAlbumSlug() {
		return albumSlug;
	}


	public void setAlbumSlug(String albumSlug) {
		this.albumSlug = albumSlug;
	}
	
	
	

	
	
}
