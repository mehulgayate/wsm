package com.wsm.processor;

import java.util.Iterator;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.evalua.entity.support.DataStoreManager;
import com.wsm.entity.Report;

public class PMFCalculator {	

	private WSMConfiguration configuration;	
	
	@Resource
	private DataStoreManager dataStoreManager;

	public void setDataStoreManager(DataStoreManager dataStoreManager) {
		this.dataStoreManager = dataStoreManager;
	}

	public void setConfiguration(WSMConfiguration configuration) {
		this.configuration = configuration;
	}

	public Report calculatePFM(Report report){

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
		return report;
	}

	public void JsontoReport(JSONObject jsonObject){

		JSONObject reports=jsonObject.getJSONObject("weather");
		Iterator<String> iterator=reports.keys();

		do{
			String reportKey=iterator.next();
			JSONObject reportObject=reports.getJSONObject(reportKey);
			Iterator<String> innIterator=reportObject.keys();
			do{
				Report report=new Report();
				String key=innIterator.next();
				System.out.println("Key "+key);
				if(key.equals("rain")){
					System.out.println(reportObject.getString(key));
					report.setRain(reportObject.getInt(key));
				}else if(key.equals("snow")){
					report.setSnow(reportObject.getInt(key));
				}else if(key.equals("xml")){
					report.setXmlString(reportObject.getString(key));
				}
				calculatePFM(report);
				dataStoreManager.save(report);
				
			}while(innIterator.hasNext());
		}while(iterator.hasNext());

	}
}
