package com.wsm.entity;

import javax.persistence.Entity;

import com.evalua.entity.support.EntityBase;

@Entity
public class GraphData extends EntityBase{
	
	public enum GraphType{
		TROPICAL,DRY,TEMPERATE,CONTINENTAL,POLAR;
	}
	
	private String humidata;
	private String tempdata;

	private GraphType graphType;	
	
	public GraphType getGraphType() {
		return graphType;
	}
	public void setGraphType(GraphType graphType) {
		this.graphType = graphType;
	}
	public String getHumidata() {
		return humidata;
	}
	public void setHumidata(String humidata) {
		this.humidata = humidata;
	}
	public String getTempdata() {
		return tempdata;
	}
	public void setTempdata(String tempdata) {
		this.tempdata = tempdata;
	}
	
	
}
