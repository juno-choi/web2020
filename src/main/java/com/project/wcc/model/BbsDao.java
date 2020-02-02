package com.project.wcc.model;

import java.util.List;
import java.util.Map;

import com.project.wcc.model.entity.BbsVo;

public interface BbsDao {

	List<BbsVo> bbsList();

	void bbsAdd(Map<String, Object> map);

}
