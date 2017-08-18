package com.chi.makers.api.admin.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.chi.makers.api.admin.dao.AdminMkDAO;
import com.chi.makers.api.admin.vo.AdminMkVO;
import com.chi.makers.api.service.FileService;
import com.chi.makers.api.service.MakersService;
import com.chi.makers.api.vo.FileVO;
import com.chi.makers.api.vo.ImgVO;
import com.chi.makers.api.vo.MakersVO;

@Service
public class AdminMkService {

	@Autowired
	private AdminMkDAO adminMkDAO;
	
	@Autowired
	private MakersService makersService;
	
	@Autowired
	private FileService fileService;
	
	public List<AdminMkVO> listMakers() {
		return adminMkDAO.selectListMakers();
	}

	@Transactional
	public MakersVO getMakers(int id) {
		MakersVO makersVO = adminMkDAO.selectMakers(id);
		makersVO.setImgs(adminMkDAO.selectListImgs(id));
		makersVO.setInfos(adminMkDAO.selectListInfos(id));
		makersVO.setSchedules(adminMkDAO.selectListSchedules(id));
		return makersVO;
	}
	
	@Transactional
	public void modifyMakers(MakersVO makers, List<MultipartFile> imgs) {
		List<String> imgsID = new ArrayList<>();
		
		makers.getImgs().clear();
		if (imgs != null) {
			for (int i=0; i < imgs.size(); i++) {
				ImgVO img = new ImgVO();
				img.setId(makers.getId());
				img.setItemId(i);
				String name = "makers-" + makers.getId() + "-" + img.getItemId();
				imgsID.add(name);
				img.setImg("/chi_makers/api/makers/file/" + name); // 다른 PC에서 사용시 반드시 수정 필요할지도
				makers.getImgs().add(img);
				System.out.println("imgs modify :" + name);
			}
		}
		
		adminMkDAO.updateMakers(makers);
		
		
		String backgroundFileId = "makers-" + makers.getId();
		int removeFiles = adminMkDAO.deleteImgs(makers.getId());
		for (int i=0; i<removeFiles; i++) {
			fileService.removeFile(backgroundFileId + "-" + i);
		}
		if (makers.getImgs().size() > 0) {
			adminMkDAO.insertImgs(makers);
		}
		
		adminMkDAO.deleteInfos(makers.getId());
		if (makers.getInfos().size() > 0) {
			adminMkDAO.insertInfos(makers);
		}
		
		adminMkDAO.deleteSchedules(makers.getId());
		if (makers.getSchedules().size() > 0) {
			adminMkDAO.insertSchedules(makers);
		}
		
		if (imgs != null) {
			String folderPath = "/hanbit2/webpack/chi-front/src/img/" + makers.getId() + "/"; // 다른 PC에서 사용시 반드시 수정
			
			File dest = new File(folderPath);
			if (!dest.exists()){
				dest.mkdirs();
			}
			else {
				File[] destroy = dest.listFiles();
				for (File file : destroy) {
					file.delete(); 
				}
			}
			
			for (int i=0; i<imgs.size(); i++) {
				MultipartFile mf = imgs.get(i);
				FileVO fileVO = new FileVO();
				ImgVO imgVO = makers.getImgs().get(i);
				
				fileVO.setFileId(imgsID.get(i));
				String fileExt = FilenameUtils.getExtension(mf.getOriginalFilename());
				String fileName = imgVO.getId() + "-" + imgVO.getItemId() + "." + fileExt;
				
				fileVO.setFilePath(folderPath + fileName);
				fileVO.setFileName(fileName);
				fileVO.setContentType(mf.getContentType());
				fileVO.setContentLength(mf.getSize());
				
				try {
					fileService.addFile(fileVO, mf.getInputStream()); // file을 전부 삭제하고 다시 넣는 것이니 modifyFile이 아니고 addFile
				} catch (IOException e) {
					throw new RuntimeException();
				}
			}
		}

		makersService.invalidateCache();
	}

	@Transactional
	public void addMakers(MakersVO makers, List<MultipartFile> imgs) {
		List<String> imgsID = new ArrayList<>();
		makers.setId(adminMkDAO.generateId());
		
		if (imgs != null) {
			for (int i=0; i<imgs.size(); i++) {
				ImgVO img = new ImgVO();
				img.setId(makers.getId());
				img.setItemId(i);
				String name = "makers-" + makers.getId() + "-" + img.getItemId();
				imgsID.add(name);
				img.setImg("/chi_makers/api/makers/file/" + name); // 다른 PC에서 사용시 반드시 수정 필요할지도
				makers.getImgs().add(img);
				System.out.println("imgs add :" + name);
			}
		}
		else {
			throw new RuntimeException("이미지 필요");
		}
		
		adminMkDAO.insertMakers(makers);
		
		if (makers.getImgs().size() > 0) {
			adminMkDAO.insertImgs(makers);
		}
		
		if (makers.getInfos().size() > 0) {
			adminMkDAO.insertInfos(makers);
		}

		if (makers.getSchedules().size() > 0) {
			adminMkDAO.insertSchedules(makers);
		}
		
		if (imgs != null) {
			String folderPath = "/hanbit2/webpack/chi-front/src/img/" + makers.getId() + "/"; // 다른 PC에서 사용시 반드시 수정
			
			File dest = new File(folderPath);
			if (!dest.exists()){
				dest.mkdirs();
			}
			else {
				File[] destroy = dest.listFiles();
				for (File file : destroy) {
					file.delete(); 
				}
			}
			
			for (int i=0; i<imgs.size(); i++) {
				MultipartFile mf = imgs.get(i);
				FileVO fileVO = new FileVO();
				ImgVO imgVO = makers.getImgs().get(i);
				
				fileVO.setFileId(imgsID.get(i));
				String fileExt = FilenameUtils.getExtension(mf.getOriginalFilename());
				String fileName = imgVO.getId() + "-" + imgVO.getItemId() + "." + fileExt;
				
				fileVO.setFilePath(folderPath + fileName);
				fileVO.setFileName(fileName);
				fileVO.setContentType(mf.getContentType());
				fileVO.setContentLength(mf.getSize());
				
				try {
					fileService.addFile(fileVO, mf.getInputStream());
				} catch (Exception e) {
					throw new RuntimeException();
				}
			}
		}
		
		makersService.invalidateCache();
	}
	
	@Transactional
	public String deleteMakers(int id) {
		String backgroundFileId = "makers-" + id;
		
		adminMkDAO.deleteInfos(id);
		int removeFiles = adminMkDAO.deleteImgs(id);
		adminMkDAO.deleteSchedules(id);
		adminMkDAO.deleteMakers(id);
		
		for (int i=0; i<removeFiles; i++) {
			fileService.removeFile(backgroundFileId + "-" + i);
			System.out.println("remove file : " + backgroundFileId + "-" + i);
		}
		
		makersService.invalidateCache();
		
		return "OK";
	}

}
