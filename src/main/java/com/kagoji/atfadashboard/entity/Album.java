package com.kagoji.atfadashboard.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Album {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String albumUuid;
	private String albumName;
	private String albumSlug;
	private int albumStatus = 0;

	@Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

	public String getAlbumUuid() {
		return albumUuid;
	}

	public void setAlbumUuid(String albumUuid) {
		this.albumUuid = albumUuid;
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

	public int getAlbumStatus() {
		return albumStatus;
	}

	public void setAlbumStatus(int albumStatus) {
		this.albumStatus = albumStatus;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	

}
