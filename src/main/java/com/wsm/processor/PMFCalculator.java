package com.wsm.processor;

import java.text.ParseException;
import java.util.Iterator;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static Logger logger=LoggerFactory.getLogger(PMFCalculator.class);

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

		logger.info("**** Calculating PMF for report id : "+ report.getId() +", temperature : "+report.getTemprature()+", Humidity : "+report.getHumidity());

		Long reportIndex=report.getId();

		double bandWidth= 1.06 * (1) * (Math.pow(10 , (-1/reportIndex))) ;

		logger.info(" bandwidth for current report calculated : "+bandWidth);

		logger.info("calculating basic pfm ");

		double pmfval= 1 / ( 10 * (Math.sqrt( 2 * 3.13 * bandWidth ) ) ) ;

		logger.info("current pfm calculated  using ******1 / ( 10 * (Math.sqrt( 2 * 3.13 * bandWidth ) ) ) *******: "+pmfval);



		/*	for(int i=0;i<numberOfReports;i++){

			double tempVar=Math.pow(reportIndex-numberOfElements, (2   /  ( -2  * bandWidth  * bandWidth )));
			pmfval=pmfval * Math.pow(reportIndex, tempVar);
		}*/

		logger.info("calculating pmf for every property in current report");

		if(report.getRain()!=null){
			if(report.getRain()>=configuration.getRainMaxThreshold()){
				pmfval++;
			}else if (report.getRain()<=configuration.getRainMinThreshold()) {
				pmfval--;
			}		
			logger.info("current pfm calculated for ongoing report : "+pmfval);
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
				pmfval=pmfval+report.getTemprature();
			}else if (report.getTemprature()<=configuration.getTempMinThreshold()) {
				pmfval=pmfval-report.getTemprature();
			}
			logger.info("current pfm calculated for ongoing report : "+pmfval);
		}
		if(report.getHumidity()!=null){
			if(report.getHumidity()>=configuration.getHumidityMaxThreshold()){
				pmfval=pmfval+report.getHumidity();
			}else if (report.getHumidity()<=configuration.getHumidityMinThreshold()) {
				pmfval=pmfval-report.getHumidity();
			}
			logger.info("current pfm calculated for ongoing report : "+pmfval);
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

		logger.info("**** Calculating PMF for report id : "+ report.getId() +", temperature : "+report.getTemprature()+", Humidity : "+report.getHumidity());

		logger.info("****** couting properties");
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

		logger.info("****** Properties found : "+propertiesCount);


		logger.info("****** Calculating PMF : ");


		double pmfval=propertiesCount / numberOfReports;	

		logger.info("****** Calculating PMF current pmf: "+pmfval);


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
				pmfval=pmfval+report.getTemprature();
			}else if (report.getTemprature()<=configuration.getTempMinThreshold()) {
				pmfval=pmfval+report.getTemprature();
			}
			logger.info("****** Calculating PMF current pmf: "+pmfval);
		}
		if(report.getHumidity()!=null){
			if(report.getHumidity()>=configuration.getHumidityMaxThreshold()){
				pmfval=pmfval+report.getHumidity();
			}else if (report.getHumidity()<=configuration.getHumidityMinThreshold()) {
				pmfval=pmfval-report.getHumidity();
			}
			logger.info("****** Calculating PMF current pmf: "+pmfval);
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
			Double temprature=report.getTemprature();
			if(temprature>15 && temprature<35 && report.getHumidity()!=null){
				GraphData graphData=new GraphData();
				graphData.setTemperature(temprature);
				graphData.setHumidity(report.getHumidity());
				graphData.setDate(report.getDate());
				graphData.setGraphType(GraphType.TROPICAL);
				dataStoreManager.save(graphData);
			}
			//if(temprature>10 && temprature<35 && report.getHumidity()!=null && report.getRain()!=null && report.getRain()<1){
			if(temprature>10 && temprature<35 && report.getHumidity()!=null){
				GraphData graphData=new GraphData();
				graphData.setTemperature(temprature);
				graphData.setHumidity(report.getHumidity());
				graphData.setDate(report.getDate());
				graphData.setGraphType(GraphType.DRY);
				dataStoreManager.save(graphData);

			}
			if(temprature>-5 && temprature<15 && report.getHumidity()!=null){
				GraphData graphData=new GraphData();
				graphData.setTemperature(temprature);
				graphData.setHumidity(report.getHumidity());
				graphData.setDate(report.getDate());
				graphData.setGraphType(GraphType.TEMPERATE);
				dataStoreManager.save(graphData);
			}
			if(temprature>-10 && temprature<15 && report.getHumidity()!=null){
				GraphData graphData=new GraphData();
				graphData.setTemperature(temprature);
				graphData.setHumidity(report.getHumidity());
				graphData.setDate(report.getDate());
				graphData.setGraphType(GraphType.CONTINENTAL);
				dataStoreManager.save(graphData);
			}			
			if(temprature>-25 && temprature<5 && report.getHumidity()!=null){
				GraphData graphData=new GraphData();
				graphData.setTemperature(temprature);
				graphData.setHumidity(report.getHumidity());
				graphData.setDate(report.getDate());
				graphData.setGraphType(GraphType.POLAR);
				dataStoreManager.save(graphData);
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

		Iterator<String> temIterator=reports.keys();

		int iteratorSize=0;

		do{
			temIterator.next();
			iteratorSize++;
		}while(temIterator.hasNext());



		do{
			String reportKey=iterator.next();



			JSONObject reportObject=reports.getJSONObject(reportKey);
			Iterator<String> innIterator=reportObject.keys();


			Report report=new Report();
			report.setReportId(new Integer(reportKey));

			do{
				String key=innIterator.next();				
				if(key.equals("rain")){					
					report.setRain(reportObject.getDouble(key));
				}else if(key.equals("snow")){
					report.setSnow(reportObject.getDouble(key));
				}else if(key.equals("temperature")){
					report.setTemprature(reportObject.getDouble(key));
				}else if(key.equals("humidity")){
					report.setHumidity(reportObject.getDouble(key));
				}else if(key.equals("wdirection")){
					report.setWindDirection(WindDirection.valueOf(reportObject.getString(key)));
				}else if (key.equals("wspeed")) {
					report.setWspeed(reportObject.getDouble(key));
				}else if(key.equals("date")){
					report.setDate(dateTimeUtil.provideDate(reportObject.getString(key)));
				}
				else if(key.equals("xml")){
					report.setXmlString(reportObject.getString(key));
				}		

			}while(innIterator.hasNext());
			calculatePFM(report);
			dataStoreManager.save(report);			

			if(report.getHumidity()!=null && report.getTemprature()!=null){
				calcPfmForContinuous(report, 2, iteratorSize);				
			}else{
				calcPfmForDiscrete(report, iteratorSize);
			}
		}while(iterator.hasNext());
	}
}
