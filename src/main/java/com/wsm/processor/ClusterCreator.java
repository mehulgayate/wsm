package com.wsm.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import com.evalua.entity.support.Repository;
import com.wsm.entity.Report;
import com.wsm.util.DateTimeUtil;

public class ClusterCreator {


	private Repository repository;

	private WSMConfiguration configuration;

	@Resource
	private DateTimeUtil dateTimeUtil;

	public void crateClusters(){
		List<String> clusterStrings=repository.getClusterStrings();

		File baseFolder= new File(configuration.getClusterBaseLocation()+"/conf.wsm");
		baseFolder.mkdirs();
		try {
			baseFolder.createNewFile();
			for (String string : clusterStrings) {
				new File(configuration.getClusterBaseLocation()+"/"+string).mkdir();
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void allocateCluster() throws ParseException, IOException{
		List<Report> reports=repository.listAllReports();

		for (Report report : reports) {
			String fileName=dateTimeUtil.provideDateString(report.getDate());
			File file=new File(configuration.getClusterBaseLocation()+"/"+report.getKlStringValue()+"/"+fileName+".xml");
			if(!file.exists()){
				file.createNewFile();
				FileWriter fileWritter = new FileWriter(file,true);
				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				bufferWritter.append("<weather>");
				bufferWritter.close();
			}
			
			File tempFile = new File(configuration.getClusterBaseLocation()+"/"+report.getKlStringValue()+"/temp.xml");

			BufferedReader reader = new BufferedReader(new FileReader(file));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String lineToRemove = "</weather>";
			String currentLine;

			while((currentLine = reader.readLine()) != null) {
			    // trim newline when comparing with lineToRemove
			    String trimmedLine = currentLine.trim();
			    if(trimmedLine.equals(lineToRemove)) continue;
			    writer.write(currentLine);
			}

			reader.close();
			writer.close();
			
			boolean successful = tempFile.renameTo(file);
			
			FileWriter fileWritter = new FileWriter(file,true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.append(report.getXmlString());
			bufferWritter.newLine();
			bufferWritter.append("</weather>");
			
			bufferWritter.close();

			
		}
		System.out.println("Data Clustring Completed !!!");
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public void setConfiguration(WSMConfiguration configuration) {
		this.configuration = configuration;
	}

	public void setDateTimeUtil(DateTimeUtil dateTimeUtil) {
		this.dateTimeUtil = dateTimeUtil;
	}
	



}
