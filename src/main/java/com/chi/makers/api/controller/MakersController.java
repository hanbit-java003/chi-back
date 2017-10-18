package com.chi.makers.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chi.makers.api.domain.Option;
import com.chi.makers.api.service.MakersService;
import com.chi.makers.api.service.OptionService;
import com.chi.makers.api.vo.MakersBestVO;
import com.chi.makers.api.vo.MakersVO;

@RestController
@RequestMapping("/api/makers")
public class MakersController {
	
	@Autowired
	private MakersService makersService;
	
	@Autowired
	private OptionService optionService;
	
	@RequestMapping(value="/", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getMakers() { // 스트링으로 보낼때 이 데이터는 JSON이라고 명시해야됨 - 안그러면 html로 받아서 해석을 못함(ex: length를 string만큼 찍힘)
		String makers = makersService.getMakers(); 
		
		return makers; // content-type - application/json; charset=UTF-8 이어야한다.
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

	@RequestMapping("/option/{id}")
	public Option getOption(@PathVariable("id") String id) {
		return optionService.getOption(id);
	}
}
