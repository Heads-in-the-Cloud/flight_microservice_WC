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
		try {

			return airplane_repository.save(airplane);

		} catch (IllegalArgumentException e) {

			return null;
		}
	}

	public Optional<Airplane> update(Airplane airplane) {
		if (airplane_repository.existsById(airplane.getId())) {
			airplane_repository.save(airplane);
			return Optional.of(airplane);
		}
		return Optional.empty();
	}

	public void deleteAirplane(Integer airplane_id) {
		airplane_repository.deleteById(airplane_id);
	}

	// Special Queries

	public Optional<List<Airplane>> findAirplaneByType(Integer type_id) {

		try {
			List<Airplane> airplanes = airplane_repository.findByType(type_id);
			return Optional.of(airplanes);
		} catch (Exception e) {
			return Optional.empty();
		}

	}

}
