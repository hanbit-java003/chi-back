package com.chi.makers.api.domain;

import java.util.List;

import org.springframework.data.couchbase.core.mapping.Document;

import com.chi.makers.api.vo.OptionListVO;
import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

@Document
public class Option {
	
	@Id
	private String key;
	@Field
	private int id;
	@Field
	private int deliveryPrice;
	@Field
	private List<OptionListVO> list;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDeliveryPrice() {
		return deliveryPrice;
	}
	public void setDeliveryPrice(int deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}
	public List<OptionListVO> getList() {
		return list;
	}
	public void setList(List<OptionListVO> list) {
		this.list = list;
	}
}


/*
{
  "id": 614330,
  "deliveryPrice": 0,
  "list": [
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
    },
    {
      "name": "사이즈선택",
      "options": [
        {
          "name": "1cm",
          "price": 100
        },
        {
          "name": "2cm",
          "price": 200
        },
        {
          "name": "3cm",
          "price": 300
        }
      ]
    },
    {
      "name": "색상선택",
      "options": [
        {
          "name": "파랑",
          "price": 0
        },
        {
          "name": "빨강",
          "price": 0
        }
      ]
    }
  ]
}
 */