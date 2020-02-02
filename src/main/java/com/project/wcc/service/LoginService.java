package com.project.wcc.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

public interface LoginService {
	String login();
	void loginUser(String id, String pw,HttpSession session) throws Exception;
	void logout(HttpSession session);
	Boolean idCheck(String id) throws Exception;
	Boolean joinUser(Map<String, Object> map) throws Exception;
}
