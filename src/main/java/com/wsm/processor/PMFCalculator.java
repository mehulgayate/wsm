package com.wsm.processor;

import java.text.ParseException;
import java.util.Iterator;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.evalua.entity.support.DataStoreManager;
import com.wsm.entity.Report;
import com.wsm.entity.Report.WindDirection;
import com.wsm.util.DateTimeUtil;

public class PMFCalculator {	

	private WSMConfiguration configuration;	

	@Resource
	private DataStoreManager dataStoreManager;

	@Resource
	private DateTimeUtil dateTimeUtil;



	public void setDateTimeUtil(DateTimeUtil dateTimeUtil) {
		this.dateTimeUtil = dateTimeUtil;
	}

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
		if(report.getTemprature()!=null){
			if(report.getTemprature()>=configuration.getTempMaxThreshold()){
				klStringValue=klStringValue+configuration.getMaxTempPmfStrings();
			}else if (report.getTemprature()<=configuration.getTempMinThreshold()) {
				klStringValue=klStringValue+configuration.getMinTempPmfStrings();
			}
		}
		if(report.getHumidity()!=null){
			if(report.getHumidity()>=configuration.getHumidityMaxThreshold()){
				klStringValue=klStringValue+configuration.getMaxHumidityPmfStrings();				
			}else if (report.getHumidity()<=configuration.getHumidityMinThreshold()) {
				klStringValue=klStringValue+configuration.getMinHumidiityPmfStrings();
			}
		}
		if(report.getWspeed()!=null){
			if(report.getWspeed()>=configuration.getWindSpeedMaxThreshold()){
				klStringValue=klStringValue+configuration.getMaxWindSpeedPmfStrings();
			}else if(report.getWspeed()<=configuration.getWindSpeedMinThreshold()){
				klStringValue=klStringValue+configuration.getMinWindSpeedPmfStrings();
			}
		}
		if(report.getWindDirection()!=null){
			if(report.getWindDirection()==WindDirection.EAST2WEST){
				klStringValue=klStringValue+configuration.getE2WWindDirPmfStrings();	
			}else if (report.getWindDirection()==WindDirection.WEST2EAST) {
				klStringValue=klStringValue+configuration.getW2EWindDirPmfStrings();
			}
		}
		report.setKlStringValue(klStringValue);
		return report;
	}

	public void JsontoReport(JSONObject jsonObject) throws ParseException{

		JSONObject reports=jsonObject.getJSONObject("weather");
		Iterator<String> iterator=reports.keys();

		do{
			String reportKey=iterator.next();
			JSONObject reportObject=reports.getJSONObject(reportKey);
			Iterator<String> innIterator=reportObject.keys();
			Report report=new Report();
			report.setReportId(new Integer(reportKey));

			do{
				String key=innIterator.next();				
				if(key.equals("rain")){					
					report.setRain(reportObject.getInt(key));
				}else if(key.equals("snow")){
					report.setSnow(reportObject.getInt(key));
				}else if(key.equals("temperature")){
					report.setTemprature(reportObject.getInt(key));
				}else if(key.equals("humidity")){
					report.setHumidity(reportObject.getInt(key));
				}else if(key.equals("wdirection")){
					report.setWindDirection(WindDirection.valueOf(reportObject.getString(key)));
				}else if (key.equals("wspeed")) {
					report.setWspeed(reportObject.getInt(key));
				}else if(key.equals("date")){
					report.setDate(dateTimeUtil.provideDate(reportObject.getString(key)));
				}
				else if(key.equals("xml")){
					report.setXmlString(reportObject.getString(key));
				}		

			}while(innIterator.hasNext());
			calculatePFM(report);
			dataStoreManager.save(report);
		}while(iterator.hasNext());
	}
}
