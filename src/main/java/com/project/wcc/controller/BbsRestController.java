package com.project.wcc.controller;

import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.wcc.service.BbsService;

@RestController
@RequestMapping(value = "/bbs/")
public class BbsRestController {
Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	BbsService bbsService;
	
	@RequestMapping(value = "add",method = RequestMethod.POST)
	public String bbsAdd(@RequestBody Map<String,Object> map) {
		logger.debug("게시글 등록을 시작합니다.");
		try {
			logger.debug(map.get("sub")+" 게시글 등록을 시도합니다.");
			bbsService.bbsAdd(map);
			return "success";
		} catch (Exception e) {
			logger.debug(map.get("sub")+" 게시글 등록에 실패했습니다.");
			return "fail";
		}
	}
}
