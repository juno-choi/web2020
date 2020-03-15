package com.project.wcc;

import java.util.Locale;
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

import com.project.wcc.service.MainService;

@Controller
public class HomeController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	MainService mainService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("WCC에 오신걸 환영합니다! 접속 지역 : {}.", locale);
		mainService.weather(model);
		return "main";
	}
	
	@RequestMapping(value = "getReg", method = RequestMethod.POST, produces="application/json; charset=utf-8")
	@ResponseBody
	public String getReg(@RequestBody Map<String,Object> map) {
		String regId = mainService.getRegId(map);
		logger.debug("지역 정보 받아서 화면으로 넘깁니다 : "+regId);
		return regId;
	}
	
	@RequestMapping(value = "getParcel", method = RequestMethod.POST, produces="application/json; charset=utf-8")
	@ResponseBody
	public Map<String,Object> getParcel(@RequestBody Map<String,Object> map) throws Exception {
		Map<String,Object> parcel = mainService.getParcel(map);
		logger.debug("배송 정보 받아서 화면으로 넘깁니다 : "+parcel);
		return parcel;
	}
	
}
