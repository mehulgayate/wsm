package com.wsm.web.support;

import java.util.ArrayList;
import java.util.List;

import com.wsm.entity.Cluster;
import com.wsm.entity.support.Repository;
import com.wsm.form.MiningFilterForm;
import com.wsm.processor.WSMConfiguration;
import com.wsm.util.DateTimeUtil;

public class MiningService {

	private Repository repository;
	private DateTimeUtil dateTimeUtil;
	private WSMConfiguration configuration;
	
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	public void setDateTimeUtil(DateTimeUtil dateTimeUtil) {
		this.dateTimeUtil = dateTimeUtil;
	}
	public void setConfiguration(WSMConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public String mineInClusteredData(MiningFilterForm miningFilterForm){
		StringBuilder stringBuilder=new StringBuilder("<weather>");
		List<String> clustersToSearch=new ArrayList<String>();
		
		if(miningFilterForm.getHumidity().equals("max")){
			clustersToSearch.add(configuration.getMaxHumidityPmfStrings());
		}else if(miningFilterForm.getHumidity().equals("min")){
			clustersToSearch.add(configuration.getMinHumidiityPmfStrings());
		}
		
		if(miningFilterForm.getRain().equals("max")){
			clustersToSearch.add(configuration.getMaxRainPmfStrings());			
		}else if(miningFilterForm.getRain().equals("min")){
			clustersToSearch.add(configuration.getMinRainPmfStrings());
		}
		
		if(miningFilterForm.getSnow().equals("max")){
			clustersToSearch.add(configuration.getMaxSnowPmfStrings());
		}else if(miningFilterForm.getSnow().equals("min")){
			clustersToSearch.add(configuration.getMinSnowPmfStrings());
		}
		
		if(miningFilterForm.getTemp().equals("max")){
			clustersToSearch.add(configuration.getMaxTempPmfStrings());
		}else if(miningFilterForm.getTemp().equals("min")){
			clustersToSearch.add(configuration.getMinTempPmfStrings());
		}
		
		if(miningFilterForm.getWindSpeed().equals("max")){
			clustersToSearch.add(configuration.getMaxWindSpeedPmfStrings());
		}else if(miningFilterForm.getWindSpeed().equals("min")){
			clustersToSearch.add(configuration.getMinWindSpeedPmfStrings());		
		}
		
		if(miningFilterForm.getWindDir().equals("e2w")){
			clustersToSearch.add(configuration.getE2WWindDirPmfStrings());
		}else if(miningFilterForm.getWindDir().equals("w2e")){
			clustersToSearch.add(configuration.getW2EWindDirPmfStrings());
		}
		
		List<Cluster> allClusters=repository.listAllClusters();
		
		return stringBuilder.toString();
	}
	
	
}
