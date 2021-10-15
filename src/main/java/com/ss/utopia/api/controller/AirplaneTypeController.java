package com.ss.utopia.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ss.utopia.api.pojo.AirplaneType;
import com.ss.utopia.api.service.AirplaneTypeService;

@RestController
public class AirplaneTypeController {

	@Autowired
	AirplaneTypeService airplane_type_service;

	@RequestMapping(path = "read/airplane_types/id={airplane_type_id}", method = RequestMethod.GET)
	public ResponseEntity<AirplaneType> getAirplaneTypeById(@PathVariable Integer airplane_type_id) {
		return ResponseEntity.ok().body(airplane_type_service.getAirplaneTypeById(airplane_type_id));
	}

	@RequestMapping(path = "read/airplane_types", method = RequestMethod.GET)
	public ResponseEntity<List<AirplaneType>> findAllAirplaneTypes() {
		return ResponseEntity.ok().body(airplane_type_service.findAllAirplaneTypes());
	}

	@PostMapping(path = "/add/airplane_type")
	public ResponseEntity<AirplaneType> saveAirplaneType(@RequestBody AirplaneType airplane_type) {

		URI uri = URI
				.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/airplane_types").toUriString());
		return ResponseEntity.created(uri).body(airplane_type_service.save(airplane_type));
	}

	@PutMapping(path = "/update/airplane_type")
	public ResponseEntity<AirplaneType> updateAirplaneType(@RequestBody AirplaneType airplane_type) {
		Optional<AirplaneType> new_airplane_type = airplane_type_service.update(airplane_type);
		if (new_airplane_type.isPresent()) {
			return ResponseEntity.ok().body(new_airplane_type.get());
		}
		return ResponseEntity.noContent().build();
	}

	@Modifying
	@Transactional
	@DeleteMapping(path = "/delete/airplane_type/id={airplane_type_id}")
	public ResponseEntity<?> deleteAirplaneType(@PathVariable Integer airplane_type_id) {
		airplane_type_service.deleteAirplaneType(airplane_type_id);
		return ResponseEntity.noContent().build();
	}
}
