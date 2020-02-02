package com.project.wcc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.wcc.model.BbsDao;
import com.project.wcc.model.entity.BbsVo;

@Service
public class BbsServiceImpl implements BbsService {

	@Inject
	SqlSession sqlSession;
	
	@Override
	public void bbsList(Model model) {
		List<BbsVo> bbsList = new ArrayList<BbsVo>();
		bbsList = sqlSession.getMapper(BbsDao.class).bbsList();
		model.addAttribute("bbsList",bbsList);
	}

	@Override
	public void bbsAdd(Map<String, Object> map) throws Exception {
		sqlSession.getMapper(BbsDao.class).bbsAdd(map);
	}

}
