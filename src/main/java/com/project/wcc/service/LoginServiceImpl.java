package com.project.wcc.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.project.wcc.model.LoginDao;
import com.project.wcc.model.entity.LoginVo;

@Service
public class LoginServiceImpl implements LoginService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	SqlSession sqlSession;
	
	@Override
	public String login() {
		return sqlSession.getMapper(LoginDao.class).login();
	}

	@Override
	public void loginUser(String id, String pw, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		LoginVo bean = new LoginVo();
		
		logger.debug("로그인요청");
		map.put("id", id);
		bean=sqlSession.getMapper(LoginDao.class).loginUser(map);
		
		if(bean.getId()!=null) {
			logger.debug("비밀번호를 조회합니다.");
			String salt = bean.getSalt();
			String pw1 = SHA256Util.getEncrypt(pw, salt);
			
			String pw2 = bean.getPw();
			logger.debug(pw1+"비교중"+pw2);
			
			if(pw1.equals(pw2)) {
				logger.debug("로그인에 성공하였습니다.");
				session.setAttribute("user", bean);
			}else {
				logger.debug("비밀번호가 다릅니다!");
			}
			
		}
		
	}

	//로그아웃 시 session 초기화
	@Override
	public void logout(HttpSession session) {
		session.invalidate();
	}

	//id 중복검사
	@Override
	public Boolean idCheck(String id) {
		return sqlSession.getMapper(LoginDao.class).idCheck(id);
	}

	//회원가입
	@Override
	public Boolean joinUser(Map<String, Object> map) throws Exception {
		//SHA256을 사용하여 비밀번호 암호화
		String salt = SHA256Util.generateSalt();
		map.put("salt", salt);
		
		String pw = map.get("pw").toString();
		pw = SHA256Util.getEncrypt(pw, salt);
		map.replace("pw", pw);
		
		return sqlSession.getMapper(LoginDao.class).joinUser(map);
	}

}
