package com.json.jsongenerator;

public class ForSaleData {
	String title;
	String description;
	String size;
	String rate;
	String bestFor;
	String latitude;
	String longitude;
	String youtubeVideoId;
	
	public ForSaleData(String title, String description, String size, String rate, String bestFor, String latitude,
			String longitude, String youtubeVideoId) {
		this.title = title;
		this.description = description;
		this.size = size;
		this.rate = rate;
		this.bestFor = bestFor;
		this.latitude = latitude;
		this.longitude = longitude;
		this.youtubeVideoId = youtubeVideoId;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getSize() {
		return size;
	}

	public String getRate() {
		return rate;
	}

	public String getBestFor() {
		return bestFor;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getYoutubeVideoId() {
		return youtubeVideoId;
	}
	

}
