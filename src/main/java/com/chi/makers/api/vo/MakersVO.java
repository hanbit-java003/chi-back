package com.chi.makers.api.vo;

import java.util.List;

public class MakersVO {

	private int id;
	private String name;
	private String title;
	private int price;
	private String content;
	private int orders;
	private String orderDays;
	private int likes;
	private List<ImgVO> imgs;
	private List<InfoVO> infos;
	private List<ScheduleVO> schedules;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getOrders() {
		return orders;
	}
	public void setOrders(int orders) {
		this.orders = orders;
	}
	public String getOrderDays() {
		return orderDays;
	}
	public void setOrderDays(String orderDays) {
		this.orderDays = orderDays;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public List<ImgVO> getImgs() {
		return imgs;
	}
	public void setImgs(List<ImgVO> imgs) {
		this.imgs = imgs;
	}
	public List<InfoVO> getInfos() {
		return infos;
	}
	public void setInfos(List<InfoVO> infos) {
		this.infos = infos;
	}
	public List<ScheduleVO> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<ScheduleVO> schedules) {
		this.schedules = schedules;
	}
	
}
