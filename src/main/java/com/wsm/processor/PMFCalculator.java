package com.wsm.processor;

import java.text.ParseException;
import java.util.Iterator;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.evalua.entity.support.DataStoreManager;
import com.wsm.entity.GraphData;
import com.wsm.entity.GraphData.GraphType;
import com.wsm.entity.Report;
import com.wsm.entity.Report.WindDirection;
import com.wsm.entity.support.Repository;
import com.wsm.util.DateTimeUtil;

public class PMFCalculator {	

	private WSMConfiguration configuration;	

	@Resource
	private DataStoreManager dataStoreManager;

	@Resource
	private DateTimeUtil dateTimeUtil;

	@Resource
	private Repository repository;

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public static StringBuilder tropicalTemp = new StringBuilder("");
	public static StringBuilder tropicalHumi =new StringBuilder("");
	public static StringBuilder dryTemp= new StringBuilder("");
	public static StringBuilder dryHumi=new StringBuilder("");
	public static StringBuilder TEMPERATE_TEMP = new StringBuilder("");
	public static StringBuilder TEMPERATE_HUMI =new StringBuilder("");
	public static StringBuilder CONTINENTAL_TEMP= new StringBuilder("");
	public static StringBuilder CONTINENTAL_HUMI=new StringBuilder("");
	public static StringBuilder POLAR_TEMP = new StringBuilder("");
	public static StringBuilder POLAR_HUMI =new StringBuilder("");


	public void setDateTimeUtil(DateTimeUtil dateTimeUtil) {
		this.dateTimeUtil = dateTimeUtil;
	}

	public void setDataStoreManager(DataStoreManager dataStoreManager) {
		this.dataStoreManager = dataStoreManager;
	}

	public void setConfiguration(WSMConfiguration configuration) {
		this.configuration = configuration;
	}

	public void calcPfmForContinuous(Report report, int numberOfElements, int numberOfReports){

		Long reportIndex=report.getId();

		double bandWidth= 1.06 * (1) * (10 ^ (-1/reportIndex)) ;

		double pmfval= 1 / ( 10 * (Math.sqrt( 2 * 3.13 * bandWidth ) ) ) ;

		for(int i=0;i<numberOfReports;i++){

			double tempVar=Math.pow(reportIndex-numberOfElements, (2   /  ( -2  * bandWidth  * bandWidth )));
			pmfval=pmfval * Math.pow(reportIndex, tempVar);
		}

		if(report.getRain()!=null){
			if(report.getRain()>=configuration.getRainMaxThreshold()){
				pmfval++;
			}else if (report.getRain()<=configuration.getRainMinThreshold()) {
				pmfval--;
			}		
		}
		if(report.getSnow()!=null){
			if(report.getSnow()>=configuration.getSnowMaxThreshold()){
				pmfval++;
			}else if(report.getSnow()<=configuration.getSnowMinThreshold()){
				pmfval--;
			}
		}
		if(report.getTemprature()!=null){
			if(report.getTemprature()>=configuration.getTempMaxThreshold()){
				pmfval++;
			}else if (report.getTemprature()<=configuration.getTempMinThreshold()) {
				pmfval--;
			}		
		}
		if(report.getHumidity()!=null){
			if(report.getHumidity()>=configuration.getHumidityMaxThreshold()){
				pmfval++;
			}else if (report.getHumidity()<=configuration.getHumidityMinThreshold()) {
				pmfval--;
			}
		}
		if(report.getWspeed()!=null){
			if(report.getWspeed()>=configuration.getWindSpeedMaxThreshold()){
				pmfval++;
			}else if(report.getWspeed()<=configuration.getWindSpeedMinThreshold()){
				pmfval--;
			}
		}
		if(report.getWindDirection()!=null){
			if(report.getWindDirection()==WindDirection.EAST2WEST){
				pmfval++;
			}else if (report.getWindDirection()==WindDirection.WEST2EAST) {
				pmfval--;
			}
		}


	}

	public void calcPfmForDiscrete(Report report,int numberOfReports){

		int propertiesCount=0;

		if(report.getRain()!=null){
			propertiesCount++;
		}
		if(report.getSnow()!=null){
			propertiesCount++;
		}
		if(report.getTemprature()!=null){
			propertiesCount++;
		}
		if(report.getHumidity()!=null){
			propertiesCount++;
		}
		if(report.getWspeed()!=null){
			propertiesCount++;
		}
		if(report.getWindDirection()!=null){
			propertiesCount++;
		}

		double pmfval=propertiesCount / numberOfReports;	

		if(report.getRain()!=null){
			if(report.getRain()>=configuration.getRainMaxThreshold()){
				pmfval++;
			}else if (report.getRain()<=configuration.getRainMinThreshold()) {
				pmfval--;
			}		
		}
		if(report.getSnow()!=null){
			if(report.getSnow()>=configuration.getSnowMaxThreshold()){
				pmfval++;
			}else if(report.getSnow()<=configuration.getSnowMinThreshold()){
				pmfval--;
			}
		}
		if(report.getTemprature()!=null){
			if(report.getTemprature()>=configuration.getTempMaxThreshold()){
				pmfval++;
			}else if (report.getTemprature()<=configuration.getTempMinThreshold()) {
				pmfval--;
			}
		}
		if(report.getHumidity()!=null){
			if(report.getHumidity()>=configuration.getHumidityMaxThreshold()){
				pmfval++;
			}else if (report.getHumidity()<=configuration.getHumidityMinThreshold()) {
				pmfval--;
			}
		}
		if(report.getWspeed()!=null){
			if(report.getWspeed()>=configuration.getWindSpeedMaxThreshold()){
				pmfval++;
			}else if(report.getWspeed()<=configuration.getWindSpeedMinThreshold()){
				pmfval--;
			}
		}
		if(report.getWindDirection()!=null){
			if(report.getWindDirection()==WindDirection.EAST2WEST){
				pmfval++;
			}else if (report.getWindDirection()==WindDirection.WEST2EAST) {
				pmfval--;
			}
		}

		report.setPmf(pmfval);

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
			int temprature=report.getTemprature();
			if(temprature>15 && temprature<35 && report.getHumidity()!=null){
				tropicalTemp.append(","+temprature);
				tropicalHumi.append(","+report.getHumidity());
			}
			if(temprature>10 && temprature<35 && report.getHumidity()!=null && report.getRain()!=null && report.getRain()<1){
				dryTemp.append(","+temprature);
				dryHumi.append(","+report.getHumidity());
			}
			if(temprature>-5 && temprature<15 && report.getHumidity()!=null){
				TEMPERATE_TEMP.append(","+temprature);
				TEMPERATE_HUMI.append(","+report.getHumidity());
			}
			if(temprature>-10 && temprature<15 && report.getHumidity()!=null){
				CONTINENTAL_TEMP.append(","+temprature);
				CONTINENTAL_HUMI.append(","+report.getHumidity());
			}
			if(temprature>-10 && temprature<15 && report.getHumidity()!=null){
				CONTINENTAL_TEMP.append(","+temprature);
				CONTINENTAL_HUMI.append(","+report.getHumidity());
			}
			if(temprature>-25 && temprature<5 && report.getHumidity()!=null){
				POLAR_TEMP.append(","+temprature);
				POLAR_HUMI.append(","+report.getHumidity());
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

	public Double calculateKL(Report report, double idealPmf){
		double klDiv= report.getPmf() * (report.getPmf()/ idealPmf);
		report.setKlIntValue(klDiv);
		return klDiv;
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
		
		GraphData tropical=repository.findGraphData(GraphType.TROPICAL);
		if(tropical==null){
			tropical=new GraphData();
			tropical.setHumidata("");
			tropical.setTempdata("");
			tropical.setGraphType(GraphType.TROPICAL);
		}
		GraphData dry=repository.findGraphData(GraphType.DRY);
		if(dry==null){
			dry=new GraphData();
			dry.setHumidata("");
			dry.setTempdata("");
			dry.setGraphType(GraphType.DRY);
		}
		
		GraphData temperate=repository.findGraphData(GraphType.TEMPERATE);
		if(temperate==null){
			temperate=new GraphData();
			temperate.setHumidata("");
			temperate.setTempdata("");
			temperate.setGraphType(GraphType.TEMPERATE);
		}
		GraphData continetal=repository.findGraphData(GraphType.CONTINENTAL);
		if(continetal==null){
			continetal=new GraphData();
			continetal.setHumidata("");
			continetal.setTempdata("");
			continetal.setGraphType(GraphType.CONTINENTAL);
		}
		GraphData polar=repository.findGraphData(GraphType.POLAR);
		if(polar==null){
			polar=new GraphData();
			polar.setHumidata("");
			polar.setTempdata("");
			polar.setGraphType(GraphType.POLAR);
		}
		
		
		tropical.setTempdata(tropical.getTempdata()+","+tropicalTemp.toString());
		tropicalTemp=new StringBuilder("");
		
		tropical.setHumidata(tropical.getHumidata()+","+tropicalHumi.toString());
		tropicalHumi=new StringBuilder("");
		
		dry.setTempdata(dry.getTempdata()+","+dryTemp.toString());
		dryTemp=new StringBuilder("");
		
		dry.setHumidata(dry.getHumidata()+","+dryHumi.toString());
		dryHumi=new StringBuilder("");
		
		temperate.setTempdata(temperate.getTempdata()+","+TEMPERATE_TEMP.toString());
		TEMPERATE_TEMP=new StringBuilder("");
		
		temperate.setHumidata(temperate.getHumidata()+","+TEMPERATE_HUMI.toString());
		TEMPERATE_HUMI=new StringBuilder("");
		
		continetal.setTempdata(continetal.getTempdata()+","+CONTINENTAL_TEMP.toString());
		CONTINENTAL_TEMP=new StringBuilder("");
		
		continetal.setHumidata(continetal.getHumidata()+","+CONTINENTAL_HUMI.toString());
		CONTINENTAL_HUMI=new StringBuilder("");
		
		polar.setTempdata(polar.getTempdata()+","+POLAR_TEMP.toString());
		POLAR_TEMP=new StringBuilder("");
		
		polar.setHumidata(polar.getHumidata()+","+POLAR_HUMI.toString());
		POLAR_HUMI=new StringBuilder("");
		
		dataStoreManager.save(tropical);
		dataStoreManager.save(dry);
		dataStoreManager.save(continetal);
		dataStoreManager.save(temperate);
		dataStoreManager.save(polar);

	}
}
