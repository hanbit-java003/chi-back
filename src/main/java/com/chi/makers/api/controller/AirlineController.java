package com.chi.makers.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chi.makers.api.domain.Airline;
import com.chi.makers.api.service.AirlineService;

@RestController
public class AirlineController {
	
	@Autowired
	private AirlineService airlineService;
	
	@RequestMapping("/airline/add")
	public Airline addAirline(@RequestParam("id") int id) {
		return airlineService.addAirline(id);
	}
	
	@RequestMapping("/airline/{id}")
	public Airline getAirline(@PathVariable("id") String id) {
		return airlineService.getAirline(id);
	}
	
	@RequestMapping("/airlines")
	public List<Airline> getAirlines() {
		return airlineService.getAirlines();
	}
	
	@RequestMapping("/airlinesUS")
	public List<Airline> getAirlinesUS() {
		return airlineService.getAirlinesUS();
	}
	
}
