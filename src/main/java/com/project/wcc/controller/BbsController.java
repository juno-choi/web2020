package com.project.wcc.controller;

import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.wcc.service.BbsService;

@Controller
@RequestMapping(value = "/bbs/")
public class BbsController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	BbsService bbsService;
	
	@RequestMapping(value = "bbs")
	public String bbs(Model model) {
		try {
			bbsService.bbsList(model);
		} catch (Exception e) {
			
		}
		return "bbs/bbs";
	}
	
}
