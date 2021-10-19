package com.ss.utopia.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.utopia.api.dao.AirplaneRepository;
import com.ss.utopia.api.pojo.Airplane;

@Service
public class AirplaneService {

	@Autowired
	AirplaneRepository airplane_repository;

	public List<Airplane> findAllAirplanes() {
		return airplane_repository.findAll();
	}

	public Airplane getAirplaneById(Integer airplane_id) {
		return airplane_repository.findById(airplane_id).get();
	}

	public Airplane save(Airplane airplane) {

			return airplane_repository.save(airplane);

	}

	public Airplane update(Airplane airplane) {
		if (airplane_repository.existsById(airplane.getId())) {
			airplane_repository.save(airplane);
			return airplane;
		}
		return null;
	}

	public void deleteAirplane(Integer airplane_id) {
		
		airplane_repository.deleteById(airplane_id);
	}

	// Special Queries

	public List<Airplane> findAirplaneByType(Integer type_id) {

			List<Airplane> airplanes = airplane_repository.findByType(type_id);
			return airplanes;
		

	}

}
