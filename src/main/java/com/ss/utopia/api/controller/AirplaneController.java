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

import com.ss.utopia.api.pojo.Airplane;
import com.ss.utopia.api.service.AirplaneService;

@RestController
public class AirplaneController {

	@Autowired 
	AirplaneService airplane_service;
	
	
	
	@RequestMapping(path = "read/airplanes/id={airplane_id}", method = RequestMethod.GET)
	public ResponseEntity<Airplane> getAirplaneById(@PathVariable Integer airplane_id) {
		return ResponseEntity.ok().body(airplane_service.getAirplaneById(airplane_id));
	}
	@RequestMapping(path = "read/airplanes", method = RequestMethod.GET)
	public ResponseEntity<List<Airplane>> findAllAirplanes() {
		return ResponseEntity.ok().body(airplane_service.findAllAirplanes());
	}
	
	@RequestMapping(path = "/find/airplanes/id={airplane_type_id}", method = RequestMethod.GET)
	public ResponseEntity<List<Airplane>> findAirplanesByType(@PathVariable Integer airplane_type_id) {
		Optional<List<Airplane>> airplanes = airplane_service.findAirplaneByType(airplane_type_id);
		if(airplanes.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.ok().body(airplanes.get());
	}
	
	@PostMapping(path = "/add/airplane")
	public ResponseEntity<Airplane> saveAirplane(@RequestBody Airplane airplane) {
		
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/airplanes").toUriString());
		return ResponseEntity.created(uri).body(airplane_service.save(airplane));
	}
	
	@PutMapping(path = "/update/airplane")
	public ResponseEntity<Airplane> updateAirplane(@RequestBody Airplane airplane) {
		Optional<Airplane> new_airplane = airplane_service.update(airplane);
		if(new_airplane.isPresent()) {
			return ResponseEntity.ok().body(new_airplane.get());
		}
		return ResponseEntity.noContent().build();
	}
	
	@Modifying
	@Transactional	
	@DeleteMapping(path = "/delete/airplane/id={airplane_id}")
	public ResponseEntity<?> deleteAirplane(@PathVariable Integer airplane_id) {
		airplane_service.deleteAirplane(airplane_id);
		return ResponseEntity.noContent().build();

	}
	
	
}
