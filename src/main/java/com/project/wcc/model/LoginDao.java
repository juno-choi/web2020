package com.project.wcc.model;

import java.util.Map;

import com.project.wcc.model.entity.LoginVo;

public interface LoginDao {

	String login();
	LoginVo loginUser(Map<String, String> map);
	Boolean idCheck(String id);
	Boolean joinUser(Map<String, Object> map);

}
