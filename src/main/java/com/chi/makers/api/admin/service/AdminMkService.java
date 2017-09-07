package com.chi.makers.api.admin.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.chi.makers.api.admin.dao.AdminMkDAO;
import com.chi.makers.api.admin.vo.AdminMkVO;
import com.chi.makers.api.dao.MakersDAO;
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
	private MakersDAO makersDAO;
	
	@Autowired
	private MakersService makersService;
	
	@Autowired
	private FileService fileService;
	
	public List<AdminMkVO> listMakers() {
		return adminMkDAO.selectListMakers();
	}

	public MakersVO getMakers(int id) {
		MakersVO makersVO = makersService.getMakers(id);
		return makersVO;
	}
	
	@Transactional
	public void modifyMakers(MakersVO makers, MultipartFile mainImg, List<MultipartFile> imgs) throws Exception{
		if (mainImg != null) {
			String name = "makers-" + makers.getId();
			makers.setMainImg("/chi_makers/api/makers/file/" + name); // 다른 PC에서 사용시 반드시 수정 필요할지도
			System.out.println("main img modify :" + name);
		}
		
		adminMkDAO.updateMakers(makers);
		
		adminMkDAO.deleteInfos(makers.getId());
		if (makers.getInfos().size() > 0) {
			adminMkDAO.insertInfos(makers);
		}
		
		adminMkDAO.deleteSchedules(makers.getId());
		if (makers.getSchedules().size() > 0) {
			adminMkDAO.insertSchedules(makers);
		}
		
		adminMkDAO.deleteOptions(makers.getId());
		if (makers.getOptions().size() > 0) {
			adminMkDAO.insertOptions(makers);
		}
		
		int lastIndex = makers.getImgs().get(makers.getImgs().size()-1).getItemId() + 1;
		for (int i=makers.getImgs().size() - 1; i > -1; i--) { // index 정리를 위해 뒤에서 부터 검색
			ImgVO imgVO = makers.getImgs().get(i);
			if (!"_removed_".equals(imgVO.getImg())) { // == != "_removed_" 안된다. %주의%
				continue;
			}
			fileService.removeFile("makers-" + imgVO.getId() + "-" + imgVO.getItemId());
			makers.getImgs().remove(imgVO); // foreach는 지우는 동작때문에 에러난다.
		}
		// 마지막 인덱스 부터 추가된 파일을 넣자
		for (int i=0; i < imgs.size(); i++) {
			ImgVO img = new ImgVO();
			img.setId(makers.getId());
			img.setItemId(lastIndex + i);
			String name = "makers-" + makers.getId() + "-" + img.getItemId();
			img.setImg("/chi_makers/api/makers/file/" + name); // 다른 PC에서 사용시 반드시 수정 필요할지도
			
			makers.getImgs().add(img);
			System.out.println("main img modify :" + name);
		}
		
		adminMkDAO.deleteImgs(makers.getId());
		adminMkDAO.insertImgs(makers);
		
		String folderPath = "/hanbit2/webpack/chi-front/src/img/" + makers.getId() + "/"; // 다른 PC에서 사용시 반드시 수정
		if (mainImg != null) {
			// 수정이니까 폴더는 생략 - 이식 할때 문제 일을 수 도 있음
			String fileName = String.valueOf(makers.getId());
			String fileExt = FilenameUtils.getExtension(mainImg.getOriginalFilename());
			String fileId = "makers-" + fileName;
			String filePath = folderPath + fileName + "." + fileExt;
			
			FileVO fileVO = new FileVO();
			fileVO.setFileId(fileId);
			fileVO.setFilePath(filePath);
			fileVO.setFileName(mainImg.getOriginalFilename());
			fileVO.setContentType(mainImg.getContentType());
			fileVO.setContentLength(mainImg.getSize());

			fileService.modifyFile(fileVO, mainImg.getInputStream());
		}
		
		if (!imgs.isEmpty()) {	
			for (int i=0; i<imgs.size(); i++) {
				MultipartFile mf = imgs.get(i);
				
				String fileName = makers.getId() + "-" + (lastIndex + i);
				String fileExt = FilenameUtils.getExtension(mf.getOriginalFilename());
				String fileId = "makers-" + fileName;
				String filePath = folderPath + fileName + "." + fileExt;

				FileVO fileVO = new FileVO();
				fileVO.setFileId(fileId);
				fileVO.setFilePath(filePath);
				fileVO.setFileName(mf.getOriginalFilename());
				fileVO.setContentType(mf.getContentType());
				fileVO.setContentLength(mf.getSize());
				
				fileService.modifyFile(fileVO, mf.getInputStream());
			}
		}

		makersService.invalidateCache();
	}

	@Transactional
	public void addMakers(MakersVO makers, MultipartFile mainImg, List<MultipartFile> imgs) {
		makers.setId(adminMkDAO.generateId());
		
		if (mainImg != null) {
			String name = "makers-" + makers.getId();
			makers.setMainImg("/chi_makers/api/makers/file/" + name); // 다른 PC에서 사용시 반드시 수정 필요할지도
			System.out.println("main img add :" + name);
		}
		else {
			throw new RuntimeException("메인 이미지 필요");
		}
		
		if (!imgs.isEmpty()) {
			for (int i=0; i<imgs.size(); i++) {
				ImgVO img = new ImgVO();
				img.setId(makers.getId());
				img.setItemId(i);
				String name = "makers-" + makers.getId() + "-" + img.getItemId();
				img.setImg("/chi_makers/api/makers/file/" + name); // 다른 PC에서 사용시 반드시 수정 필요할지도
				makers.getImgs().add(img);
			}
		}
		else {
			throw new RuntimeException("슬라이드 이미지 필요");
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
		
		if (makers.getOptions().size() > 0) {
			adminMkDAO.insertOptions(makers);
		}
		
		if (mainImg != null) {
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
			
			String fileName = String.valueOf(makers.getId());
			String fileExt = FilenameUtils.getExtension(mainImg.getOriginalFilename());
			String fileId = "makers-" + fileName;
			String filePath = folderPath + fileName + "." + fileExt;
			
			FileVO mFileVO = new FileVO();
			mFileVO.setFileId(fileId);
			mFileVO.setFilePath(filePath);
			mFileVO.setFileName(mainImg.getOriginalFilename());
			mFileVO.setContentType(mainImg.getContentType());
			mFileVO.setContentLength(mainImg.getSize());
			
			try {
				fileService.addFile(mFileVO, mainImg.getInputStream());
				System.out.println("main file add :" + mFileVO.getFileId());
			} catch (Exception e) {
				throw new RuntimeException();
			}
			
			if (!imgs.isEmpty()) {
				for (int i=0; i<imgs.size(); i++) {
					MultipartFile mf = imgs.get(i);
					FileVO sFileVO = new FileVO();
					
					fileName = makers.getId() + "-" + i;
					fileExt = FilenameUtils.getExtension(mf.getOriginalFilename());
					fileId = "makers-" + fileName;
					filePath = folderPath + fileName + "." + fileExt;
					
					sFileVO.setFileId(fileId);
					sFileVO.setFilePath(filePath);
					sFileVO.setFileName(mf.getOriginalFilename());
					sFileVO.setContentType(mf.getContentType());
					sFileVO.setContentLength(mf.getSize());
					
					try {
						fileService.addFile(sFileVO, mf.getInputStream());
						System.out.println("slide file add :" + sFileVO.getFileId());
					} catch (Exception e) {
						throw new RuntimeException();
					}
				}
			}
		}
		
		makersService.invalidateCache();
	}
	
	@Transactional
	public String deleteMakers(int id) {
		adminMkDAO.deleteInfos(id);
		adminMkDAO.deleteSchedules(id);
		adminMkDAO.deleteOptions(id);
		List<ImgVO> imgs = makersDAO.getImgs(id);
		adminMkDAO.deleteImgs(id);
		adminMkDAO.deleteMakers(id); // 순서중요 - tbl_item이 id를 외래키로 사용하니까
		
		String mainImg = "makers-" + id;
		fileService.removeFile(mainImg);
		System.out.println("remove file : " + id);
		
		for (ImgVO imgVO : imgs) {
			fileService.removeFile(mainImg + "-" + imgVO.getItemId());
			System.out.println("remove file : " + id + "-" + imgVO.getItemId());
		}
		
		String folderPath = "/hanbit2/webpack/chi-front/src/img/" + id + "/";
		File dest = new File(folderPath);
		if (dest.exists()){
			dest.delete();
		}
		
		makersService.invalidateCache();
		
		return "OK";
	}

}
