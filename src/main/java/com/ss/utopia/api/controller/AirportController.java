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

import com.ss.utopia.api.pojo.Airport;
import com.ss.utopia.api.service.AirportService;

@RestController
public class AirportController {

	@Autowired
	AirportService airport_service;

	@RequestMapping(path = "read/airports/id={airport_code}", method = RequestMethod.GET)
	public ResponseEntity<Airport> getAirportById(@PathVariable String airport_code) {
		return ResponseEntity.ok().body(airport_service.getAirportById(airport_code));
	}

	@RequestMapping(path = "read/airports", method = RequestMethod.GET)
	public ResponseEntity<List<Airport>> findAllAirports() {
		return ResponseEntity.ok().body(airport_service.findAllAirports());
	}

	@PostMapping(path = "/add/airport")
	public ResponseEntity<Airport> saveAirport(@RequestBody Airport airport) {

		Optional<Airport> new_airport = airport_service.save(airport);

		if (new_airport.isEmpty()) {
			return ResponseEntity.badRequest().body(airport);
		}

		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/read/airports/id=" + airport.getIataId()).toUriString());
		return ResponseEntity.created(uri).body(new_airport.get());
	}

	@PutMapping(path = "/update/airport")
	public ResponseEntity<Airport> updateAirport(@RequestBody Airport airport) {
		Optional<Airport> new_airport = airport_service.update(airport);
		if (new_airport.isPresent()) {
			return ResponseEntity.ok().body(new_airport.get());
		}
		return ResponseEntity.noContent().build();
	}

	@Modifying
	@Transactional
	@DeleteMapping(path = "/delete/airport/id={airport_code}")
	public ResponseEntity<?> deleteAirport(@PathVariable String airport_code) {
		airport_service.deleteAirportById(airport_code);
		return ResponseEntity.noContent().build();

	}
}
