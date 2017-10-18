package com.chi.makers.api.vo;

import java.util.List;

public class OptionListVO {
	private String name;
	private List<OptionsVO> options;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<OptionsVO> getOptions() {
		return options;
	}
	public void setOptions(List<OptionsVO> options) {
		this.options = options;
	}
}


/*
{
  "name": "디자인선택",
  "options": [
    {
      "name": "스윈팔찌",
      "price": 51000
    },
    {
      "name": "앤드팔찌",
      "price": 51000
    }
  ]
}
*/