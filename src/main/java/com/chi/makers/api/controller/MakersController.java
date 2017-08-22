package com.chi.makers.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chi.makers.api.service.MakersService;
import com.chi.makers.api.vo.MakersBestVO;
import com.chi.makers.api.vo.MakersVO;

@RestController
@RequestMapping("/api/makers")
public class MakersController {
	
	@Autowired
	private MakersService makersService;
	
	@RequestMapping("/")
	public List<MakersVO> getMakers() {
		List<MakersVO> makers = makersService.getMakers(); 
		
		return makers; // spring이 json 만들어 보내준다. java에서는 java만
	}
	
	@RequestMapping(value="/detail/{id}", method=RequestMethod.POST)
	public MakersVO getMakers(@PathVariable(value="id", required=true) int makersId) {
		// 파라미터 String 아니고 int값으로 받을 수 있다.
		return makersService.getMakers(makersId);
	}
	
	@RequestMapping("/best")
	public List<MakersBestVO> getBestMakers() {
		return makersService.getBestMakers();
	}

}
