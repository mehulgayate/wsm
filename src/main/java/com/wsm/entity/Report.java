package com.wsm.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.evalua.entity.support.EntityBase;

@Entity
public class Report extends EntityBase{
	
	public enum WindDirection{
		EAST2WEST,WEST2EAST;
	}

	@Id
	@GeneratedValue
	private Long id;
	private String xmlString;
	private String klStringValue;
	private Integer rain;	
	private Integer snow;
	private Integer temprature;
	private Integer humidity;
	private WindDirection windDirection;
	private Integer wspeed;
	private Date date;
	private Integer reportId;
	private Double klIntValue;
	private Double pmf;	
	
	public Double getPmf() {
		return pmf;
	}
	public void setPmf(Double pmf) {
		this.pmf = pmf;
	}
	public Double getKlIntValue() {
		return klIntValue;
	}
	public void setKlIntValue(Double klIntValue) {
		this.klIntValue = klIntValue;
	}
	public WindDirection getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(WindDirection windDirection) {
		this.windDirection = windDirection;
	}
	public Integer getRain() {
		return rain;
	}
	public void setRain(Integer rain) {
		this.rain = rain;
	}
	public Integer getSnow() {
		return snow;
	}
	public void setSnow(Integer snow) {
		this.snow = snow;
	}
	public Integer getTemprature() {
		return temprature;
	}
	public void setTemprature(Integer temprature) {
		this.temprature = temprature;
	}
	public Integer getHumidity() {
		return humidity;
	}
	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}	
	public Integer getWspeed() {
		return wspeed;
	}
	public void setWspeed(Integer wspeed) {
		this.wspeed = wspeed;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getXmlString() {
		return xmlString;
	}
	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}
	public String getKlStringValue() {
		return klStringValue;
	}
	public void setKlStringValue(String klStringValue) {
		this.klStringValue = klStringValue;
	}
	
}
