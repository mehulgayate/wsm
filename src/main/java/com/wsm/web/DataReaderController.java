package com.wsm.web;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.evalua.entity.support.DataStoreManager;
import com.wsm.entity.Report;
import com.wsm.processor.ClusterCreator;
import com.wsm.processor.PMFCalculator;
import com.wsm.util.XMLParser;

@Controller
public class DataReaderController {

	@Autowired
	ServletContext servletContext;

	@Resource
	private XMLParser xmlParser;
	
	@Resource
	private PMFCalculator pmfCalculator;
	
	@Resource
	private DataStoreManager dataStoreManager;
	
	@Resource
	private ClusterCreator clusterCreator;

	@RequestMapping("/readData")
	public ModelAndView readData()throws Exception{
		ModelAndView mv=new ModelAndView("json-string");
		try {

			File fXmlFile = new File(servletContext.getRealPath("WEB-INF/classes/data.xml"));
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put(doc.getDocumentElement().getNodeName(), xmlParser.parseXML(doc));
			pmfCalculator.JsontoReport(jsonObject);
			
			clusterCreator.crateClusters();
			clusterCreator.allocateCluster();
			mv.addObject("json",jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return mv;
	}
}
