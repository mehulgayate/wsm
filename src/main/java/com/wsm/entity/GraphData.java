package com.wsm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.evalua.entity.support.EntityBase;

@Entity
public class GraphData extends EntityBase{
	
	public enum GraphType{
		TROPICAL,DRY,TEMPERATE,CONTINENTAL,POLAR;
	}
	
	private Long id;

	private String humidata;
	private String tempdata;

	private GraphType graphType;	
	
	public GraphType getGraphType() {
		return graphType;
	}
	public void setGraphType(GraphType graphType) {
		this.graphType = graphType;
	}
	
	@Column(length=1000000)
	@Lob
	public String getHumidata() {
		return humidata;
	}
	public void setHumidata(String humidata) {
		this.humidata = humidata;
	}
	
	@Column(length=1000000)
	@Lob
	public String getTempdata() {
		return tempdata;
	}
	public void setTempdata(String tempdata) {
		this.tempdata = tempdata;
	}
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
