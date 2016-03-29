package com.doume.max.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
public class BHomePage extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Long userId;
	@Transient
	private CommonsMultipartFile media;
	private String mediaId = "0/home.png";
	public CommonsMultipartFile getMedia() {
		return media;
	}
	public void setMedia(CommonsMultipartFile media) {
		this.media = media;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setWithoutId(BHomePage bhp){
		if(bhp!=null){
			this.mediaId = bhp.getMediaId();
			this.media = bhp.getMedia();
		}
	}
}
