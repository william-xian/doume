package com.doume.max.entity;


public class SearchItem extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private String keyWord;
	private String abstracting;
	private String mediaId;
	private Integer weight;
	private String targetType;
	private Long targetId;
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public Long getTargetId() {
		return targetId;
	}
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getAbstracting() {
		return abstracting;
	}
	public void setAbstracting(String abstracting) {
		this.abstracting = abstracting;
	}
	
}
