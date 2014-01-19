package com.wsm.web;

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
	public ModelAndView mineCluesteredData(HttpServletRequest request,@ModelAttribute(MiningFilterForm.key) MiningFilterForm miningFilterForm){
		ModelAndView mv=new ModelAndView("mining-result");
		String xmlString=miningService.mineInClusteredData(miningFilterForm);
		return mv;
	}

}
