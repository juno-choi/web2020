package com.project.wcc.service;

import java.util.Map;

import org.springframework.ui.Model;

public interface BbsService {

	void bbsList(Model model) throws Exception;

	void bbsAdd(Map<String, Object> map) throws Exception;

}
