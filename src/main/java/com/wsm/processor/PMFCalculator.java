package com.wsm.processor;

import org.hibernate.SessionFactory;

import com.wsm.entity.Report;

public class PMFCalculator {	

	private WSMConfiguration configuration;
	private SessionFactory sessionFactory;

	public void calculatePFM(Report report){

		String klStringValue="";

		if(report.getRain()!=null){
			if(report.getRain()>=configuration.getRainMaxThreshold()){
				klStringValue=klStringValue+configuration.getMaxRainPmfStrings();
			}else if (report.getRain()<=configuration.getRainMinThreshold()) {
				klStringValue=klStringValue+configuration.getMinRainPmfStrings();
			}		
		}
		if(report.getSnow()!=null){
			if(report.getSnow()>=configuration.getSnowMaxThreshold()){
				klStringValue=klStringValue+configuration.getMaxSnowPmfStrings();
			}else if(report.getSnow()<=configuration.getSnowMinThreshold()){
				klStringValue=klStringValue+configuration.getMinSnowPmfStrings();
			}
		}
		
		report.setKlStringValue(klStringValue);

	}
}
