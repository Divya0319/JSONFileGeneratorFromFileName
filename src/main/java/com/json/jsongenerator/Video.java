package com.json.jsongenerator;

public class Video {
	
	private String title;
	private String vidId;
	private String url;
	
	public Video(String title, String vidId, String url) {
		this.title = title;
		this.vidId = vidId;
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVidId() {
		return vidId;
	}
	public void setVidId(String vidId) {
		this.vidId = vidId;
	}

	
	
	

}
