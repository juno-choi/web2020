package com.project.wcc.controller;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.wcc.service.LoginService;

@Controller
public class LoginController {

	@Inject
	LoginService loginService;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	//로그인
	@RequestMapping (value = "/user/login", method = RequestMethod.POST)
	public String loginUser(@RequestParam (value = "id") String id, @RequestParam (value = "pw") String pw, HttpSession session) {
		logger.debug(id+" 회원으로 로그인을 시도합니다");
		try {
			logger.debug("로그인을 실시합니다.");
			loginService.loginUser(id,pw,session);
			return "redirect:/";
		} catch (Exception e) {
			logger.debug("로그인에 실패했습니다.");
			return "redirect:/";
		}
	}
	
	//로그아웃
	@PostMapping(value = "/user/logout")
	public String logoutUser(HttpSession session) {
		logger.debug(session.getAttribute("id")+" 회원으로 로그아웃을 시도합니다.");
		loginService.logout(session);
		return "redirect:/";
	}
	
	//회원가입 페이지
	@GetMapping(value = "/join")
	public String join(HttpSession session) {
		logger.debug("회원가입을 시도합니다.");
		if(session.getAttribute("id")!=null) {
			logger.debug(session.getAttribute("id")+" 로그인한 유저이므로 돌려보냅니다");
			return "redirect:/";
		}else {
			logger.debug("회원가입을 시작합니다");
			return "login/join";
		}
	}
	
	//아이디 중복검사
	@PostMapping(value = "/user/check")
	@ResponseBody
	public String userCheck(@RequestParam(value = "id")String id) {
		logger.debug(id+" 중복검사 실시합니다.");
		try {
			if(loginService.idCheck(id)) {
				logger.debug(id+" 아이디가 중복되어집니다.");
			}
			return "fail";
		} catch (Exception e) {
			logger.debug(id+" 아이디는 사용 가능합니다.");
			return "success";
		}
	}
	
	//회원가입
	@PostMapping(value = "/user/join")
	@ResponseBody
	public String userJoin(@RequestBody Map<String, Object> map) {
		logger.debug("회원가입을 실시합니다.");
		try {
			loginService.joinUser(map);
			logger.debug(map.get("id").toString()+" 회원가입 성공");
			return "success";
		} catch (Exception e) {
			logger.debug("회원가입 실패");
			return "fail";
		}
	}
	/////////////////////
	
	
}



