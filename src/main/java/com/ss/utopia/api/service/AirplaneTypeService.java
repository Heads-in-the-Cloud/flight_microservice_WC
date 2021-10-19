package com.ss.utopia.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.utopia.api.dao.AirplaneTypeRepository;
import com.ss.utopia.api.pojo.AirplaneType;

@Service
public class AirplaneTypeService {

	@Autowired
	AirplaneTypeRepository airplane_type_repository;

	public List<AirplaneType> findAllAirplaneTypes() {
		return airplane_type_repository.findAll();
	}

	public AirplaneType getAirplaneTypeById(Integer airplane_type_id) {
		return airplane_type_repository.findById(airplane_type_id).get();
	}

	public AirplaneType save(AirplaneType airplane_type) {

		return airplane_type_repository.save(airplane_type);
	}

	public AirplaneType update(AirplaneType airplane_type) {

		if (airplane_type_repository.existsById(airplane_type.getId())) {
			airplane_type_repository.save(airplane_type);
			return airplane_type;
		}
		return null;
	}

	public void deleteAirplaneType(Integer airplane_type_id) {
		airplane_type_repository.deleteById(airplane_type_id);
	}

}
