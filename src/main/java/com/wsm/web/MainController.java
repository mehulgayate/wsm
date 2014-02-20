package com.wsm.web;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.evalua.entity.support.DataStoreManager;
import com.wsm.entity.GraphData;
import com.wsm.entity.GraphData.GraphType;
import com.wsm.entity.support.Repository;

@Controller
public class MainController {

	@Resource
	private Repository repository;
	
	@Resource
	private DataStoreManager dataStoreManager;

	@RequestMapping("/")
	public ModelAndView startup(){
		ModelAndView mv=new ModelAndView("new/index");
		return mv;
	}
	@RequestMapping("/upload-new")
	public ModelAndView uploadNew(){
		ModelAndView mv=new ModelAndView("new/upload");
		return mv;
	}
	@RequestMapping("/graphs")
	public ModelAndView showGraphs(){
		ModelAndView mv=new ModelAndView("new/graph");
		return mv;
	}
	@RequestMapping("/tables")
	public ModelAndView showtables(){
		ModelAndView mv=new ModelAndView("new/table");
		return mv;
	}

	@RequestMapping("/graph-data")
	public ModelAndView getGrapgData(@RequestParam String type){
		ModelAndView mv=new ModelAndView("json-string");

		GraphData graphData=repository.findGraphData(GraphType.valueOf(type));
		if(graphData==null || graphData.getHumidata().trim().matches("\\,*") || graphData.getTempdata().trim().matches("\\,*")){
			JSONObject jsonObjectOuter=new JSONObject();
			JSONObject jsonObject1=new JSONObject();
			JSONObject jsonObject2=new JSONObject();

			jsonObject1.put(0,0);
			jsonObject2.put(0,0);
			
			jsonObjectOuter.put("temp", jsonObject1);
			jsonObjectOuter.put("humi", jsonObject2);
			mv.addObject("data", jsonObjectOuter);
			return mv;
		}
		String humi=graphData.getHumidata();
		if(humi.length()>331){
			humi=StringUtils.mid(humi, humi.length()-310, humi.length());
		}
		String[] spiltedHumi=humi.split(",");
		graphData.setHumidata(humi);

		String temp=graphData.getTempdata();
		if(StringUtils.countMatches(temp, ",")>110){
			temp=StringUtils.mid(temp, temp.length()-330, temp.length());
		}
		String[] spiltedTemp=temp.split(",");
		graphData.setTempdata(temp);
		
		dataStoreManager.save(graphData);

		JSONObject jsonObjectOuter=new JSONObject();
		JSONObject jsonObject1=new JSONObject();
		JSONObject jsonObject2=new JSONObject();

		for(int i=0;i<spiltedHumi.length;i++){
			if(StringUtils.isNotBlank(spiltedHumi[i]) && StringUtils.isNotBlank(spiltedTemp[i])){
				jsonObject1.put(i, spiltedTemp[i]);
				jsonObject2.put(i, spiltedHumi[i]);
			}
		}
		jsonObjectOuter.put("temp", jsonObject1);
		jsonObjectOuter.put("humi", jsonObject2);
		mv.addObject("data", jsonObjectOuter);
		return mv;
	}
}
