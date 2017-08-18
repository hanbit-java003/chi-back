package com.chi.makers.api.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.chi.makers.api.admin.service.AdminMkService;
import com.chi.makers.api.admin.vo.AdminMkVO;
import com.chi.makers.api.vo.MakersVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/admin/makers")
public class AdminMkController {
	
	@Autowired
	private AdminMkService adminMkService;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping("/list")
	public List<AdminMkVO> listMakers() {
		return adminMkService.listMakers();
	}
	
	@RequestMapping(value="/list/{id}", method=RequestMethod.POST)
	public MakersVO  getMaksers(@PathVariable("id") int id) {
		return adminMkService.getMakers(id);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public Map modifyMakers(@PathVariable("id") String id, MultipartHttpServletRequest request) throws Exception {
		String json = request.getParameter("json");
		MakersVO makers = mapper.readValue(json, MakersVO.class);
		
		List<MultipartFile> imgs = request.getFiles("imgs"); // request에 같은 이름(imgs)로 보내서 리스트로 받는다.
		
		adminMkService.modifyMakers(makers, imgs);
		
		Map result = new HashMap();
		result.put("ok", true);
		
		return result;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public Map addMakers(MultipartHttpServletRequest request) throws Exception {
		String json = request.getParameter("json");
		MakersVO makers = mapper.readValue(json, MakersVO.class);
		
		List<MultipartFile> imgs = request.getFiles("imgs");
		
		adminMkService.addMakers(makers, imgs);
		
		Map result = new HashMap();
		result.put("id", makers.getId());
		
		return result;
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteMakers(@PathVariable("id") int id) {
		adminMkService.deleteMakers(id);
		return "OK";
	}
	
}
