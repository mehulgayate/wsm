package com.wsm.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wsm.form.MiningFilterForm;
import com.wsm.web.support.MiningService;

@Controller
public class MiningController {
	
	@Resource
	private MiningService miningService;
	
	@RequestMapping("/mining")
	public ModelAndView showFilerPage(HttpServletRequest request){
		ModelAndView mv=new ModelAndView("mining-filter");
		return mv;
	}
	
	@RequestMapping("/mine-clusetred-data")
	public ModelAndView mineCluesteredData(HttpServletRequest request,@ModelAttribute(MiningFilterForm.key) MiningFilterForm miningFilterForm) throws ParseException, IOException{
		ModelAndView mv=new ModelAndView("mining-result");
		Date startBeforeClusMining=new Date();
		String clusteredXmlString=miningService.mineInClusteredData(miningFilterForm);
		mv.addObject("clustredtakenTime",(new Date().getTime()-startBeforeClusMining.getTime()));
		
		Date startNonClusData=new Date();
		String nonClusteredData=miningService.mineFromNonClustredData(miningFilterForm);
		mv.addObject("nonClustredtakenTime",(new Date().getTime()-startNonClusData.getTime()));
		mv.addObject("nonClusteredData",nonClusteredData);		
		mv.addObject("clusteredXmlResult",clusteredXmlString);
		return mv;
	}

}
