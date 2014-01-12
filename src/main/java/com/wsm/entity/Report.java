package com.wsm.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.evalua.entity.support.EntityBase;

@Entity
public class Report extends EntityBase{

	@Id
	@GeneratedValue
	private Long id;
	private String xmlString;
	private String klStringValue;
	private Integer klIntValue;
	private Integer rain;	
	private Integer snow;
	private Integer temprature;
	private Integer humidity;
	private Integer wdirection;
	private Integer wspeed;
	private Date date;
	private Integer reportId;
	
	
	
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
	public Integer getWdirection() {
		return wdirection;
	}
	public void setWdirection(Integer wdirection) {
		this.wdirection = wdirection;
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
	public Integer getKlIntValue() {
		return klIntValue;
	}
	public void setKlIntValue(Integer klIntValue) {
		this.klIntValue = klIntValue;
	}
	
}
