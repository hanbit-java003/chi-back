package com.chi.makers.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chi.makers.api.domain.Airline;
import com.chi.makers.api.repo.AirlineRepository;
import com.mysql.fabric.xmlrpc.base.Array;

@Service
public class AirlineService {
	
	@Autowired
	private AirlineRepository airlineRepository;
	
	public Airline addAirline(int id) {
		Airline airline = new Airline();
		String key = "airline_" + id;
		airline.setKey(key);
		airline.setId(id);
		airline.setType("airline");
		airline.setName("KAL");
		airline.setCountry("Korea");
		
		return airlineRepository.save(airline);
	}
	
	public Airline getAirline(String id) {
		return airlineRepository.findOne(id);
	}
	
	public List<Airline> getAirlines() {
		List<Airline> all = new ArrayList<>();
		airlineRepository.findAll().forEach(all::add);
		return all;
	}
	
	public List<Airline> getAirlinesUS() {
		return airlineRepository.findUS();
	}
}
