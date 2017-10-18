package com.chi.makers.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chi.makers.api.dao.MakersDAO;
import com.chi.makers.api.vo.MakersBestVO;
import com.chi.makers.api.vo.MakersVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MakersService {
	
	@Autowired
	private MakersDAO makersDAO;
	
	private String casheMakersVO;
	
	public String getMakers() {
		if (casheMakersVO != null) {
			return casheMakersVO;
		}
		
		List<MakersVO> makers = makersDAO.getMakers();
		if (makers == null) {
			return null;
		}
		
		saveCache(makers);
		
		return loadCache();
	}

	public MakersVO getMakers(int makersId) {
		MakersVO makersVO = makersDAO.getMakers(makersId);
		makersVO.setImgs(makersDAO.getImgs(makersId));
		makersVO.setInfos(makersDAO.getInfos(makersId));
		makersVO.setSchedules(makersDAO.getSchedules(makersId));
		//makersVO.setOptions(makersDAO.getOptions(makersId));
		return makersVO;
	}

	public List<MakersBestVO> getBestMakers() {
		return makersDAO.selectBestMakers();
	}
	
	private void saveCache(List<MakersVO> makers) {
		ObjectMapper mapper = new ObjectMapper(); // java 타입을 JSON 타입으로 만든다.
		try {
			casheMakersVO = mapper.writeValueAsString(makers);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e); // 받는 쪽에서 try catch 안해도 됨 - 어짜피 에러는 나있는 상태
		}
	}
	
	private String loadCache() {
		return casheMakersVO;
	}

	public void invalidateCache() {
		casheMakersVO = null;
	}

}
