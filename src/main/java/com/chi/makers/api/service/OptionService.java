package com.chi.makers.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chi.makers.api.domain.Option;
import com.chi.makers.api.repo.OptionRepository;

@Service
public class OptionService {

	@Autowired
	private OptionRepository optionRepository;
	
	public Option getOption(String id) {
		return optionRepository.findOne(id); // string 안됨
	}

}
