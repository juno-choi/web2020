package com.project.wcc.service;

import java.util.Map;

import org.springframework.ui.Model;

public interface MainService {

	void weather(Model model);

	String getRegId(Map<String, Object> map);

	Map<String,Object> getParcel(Map<String, Object> map) throws Exception;

}
