package com.wsm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

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
}
