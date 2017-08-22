package com.chi.makers.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chi.makers.api.dao.MakersDAO;
import com.chi.makers.api.vo.MakersBestVO;
import com.chi.makers.api.vo.MakersVO;

@Service
public class MakersService {
	
	@Autowired
	private MakersDAO makersDAO;
	
	private List<MakersVO> casheMakersVO = new ArrayList<>();
	
	public List<MakersVO> getMakers() {
		if (!casheMakersVO.isEmpty()) {
			return casheMakersVO;
		}
		
		List<MakersVO> makers = makersDAO.getMakers();
		if (makers == null) {
			return null;
		}
		for (MakersVO makersVO : makers) { // 그림 1개만 필요
			makersVO.setImgs(makersDAO.getImg(makersVO.getId()));
		}
		
		casheMakersVO = makers;
		
		return makers;
	}

	public MakersVO getMakers(int makersId) {
		MakersVO makersVO = makersDAO.getMakers(makersId);
		makersVO.setImgs(makersDAO.getImgs(makersId));
		makersVO.setInfos(makersDAO.getInfos(makersId));
		makersVO.setSchedules(makersDAO.getSchedules(makersId));
		makersVO.setOptions(makersDAO.getOptions(makersId));
		return makersVO;
	}

	public List<MakersBestVO> getBestMakers() {
		return makersDAO.selectBestMakers();
	}

	public void invalidateCache() {
		if (!casheMakersVO.isEmpty()) {
			casheMakersVO.clear();
		}
	}

}
