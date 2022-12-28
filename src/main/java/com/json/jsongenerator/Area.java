package com.json.jsongenerator;

public class Area {
	
	private String plotArea;
	private String buildupArea;
	private String facing;

	
	public Area(String plotArea, String buildupArea, String facing) {
		this.plotArea = plotArea;
		this.buildupArea = buildupArea;
		this.facing = facing;
	}
	public String getPlotArea() {
		return plotArea;
	}
	public void setPlotArea(String plotArea) {
		this.plotArea = plotArea;
	}
	public String getBuildupArea() {
		return buildupArea;
	}
	public void setBuildupArea(String buildupArea) {
		this.buildupArea = buildupArea;
	}
	public String getFacing() {
		return facing;
	}
	public void setFacing(String facing) {
		this.facing = facing;
	}
	
	

}
