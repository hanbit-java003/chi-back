package com.chi.makers.api.controller;

import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chi.makers.api.service.FileService;
import com.chi.makers.api.vo.FileVO;

@RestController
@RequestMapping("/api/makers/file")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping("/{fileId}")
	public void getFile(@PathVariable("fileId") String fileId, HttpServletResponse response) throws Exception {
		FileVO fileVO = fileService.getFile(fileId);
		
		String filePath = fileVO.getFilePath();
		String contentType = fileVO.getContentType();
		long contentLength = fileVO.getContentLength();
		String fileName = fileVO.getFileName();
		
		OutputStream outputStream = response.getOutputStream();
		response.setContentType("application/octet-stream");
		response.setContentLengthLong(contentLength);
		response.setHeader("Content-Disposition", "filename=" + fileName);
		
		try(FileInputStream inputStream = new FileInputStream(filePath)) {
			
			IOUtils.copyLarge(inputStream, outputStream);
		} 
	}
	
}
