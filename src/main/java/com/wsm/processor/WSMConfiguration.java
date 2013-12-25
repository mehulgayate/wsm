package com.wsm.processor;

public class WSMConfiguration {
	
	private Integer rainMinThreshold;
	private Integer rainMaxThreshold;
	private Integer snowMinThreshold;
	private Integer snowMaxThreshold;
	
	
	
	private String maxRainPmfStrings;
	private String minRainPmfStrings;
	private String maxSnowPmfStrings;
	private String minSnowPmfStrings;
	
	
	
	public String getMaxRainPmfStrings() {
		return maxRainPmfStrings;
	}
	public void setMaxRainPmfStrings(String maxRainPmfStrings) {
		this.maxRainPmfStrings = maxRainPmfStrings;
	}
	public String getMinRainPmfStrings() {
		return minRainPmfStrings;
	}
	public void setMinRainPmfStrings(String minRainPmfStrings) {
		this.minRainPmfStrings = minRainPmfStrings;
	}
	public String getMaxSnowPmfStrings() {
		return maxSnowPmfStrings;
	}
	public void setMaxSnowPmfStrings(String maxSnowPmfStrings) {
		this.maxSnowPmfStrings = maxSnowPmfStrings;
	}
	public String getMinSnowPmfStrings() {
		return minSnowPmfStrings;
	}
	public void setMinSnowPmfStrings(String minSnowPmfStrings) {
		this.minSnowPmfStrings = minSnowPmfStrings;
	}
	public Integer getRainMinThreshold() {
		return rainMinThreshold;
	}
	public void setRainMinThreshold(Integer rainMinThreshold) {
		this.rainMinThreshold = rainMinThreshold;
	}
	public Integer getRainMaxThreshold() {
		return rainMaxThreshold;
	}
	public void setRainMaxThreshold(Integer rainMaxThreshold) {
		this.rainMaxThreshold = rainMaxThreshold;
	}
	public Integer getSnowMinThreshold() {
		return snowMinThreshold;
	}
	public void setSnowMinThreshold(Integer snowMinThreshold) {
		this.snowMinThreshold = snowMinThreshold;
	}
	public Integer getSnowMaxThreshold() {
		return snowMaxThreshold;
	}
	public void setSnowMaxThreshold(Integer snowMaxThreshold) {
		this.snowMaxThreshold = snowMaxThreshold;
	}
	
	

}
