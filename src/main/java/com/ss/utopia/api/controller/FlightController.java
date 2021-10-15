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

import com.ss.utopia.api.pojo.Flight;
import com.ss.utopia.api.service.FlightService;

@RestController
public class FlightController {

	
	@Autowired
	FlightService flight_service;
	
	
	@RequestMapping(path = "read/flights", method = RequestMethod.GET)
	public ResponseEntity<List<Flight>> findAllFlights() {
		return ResponseEntity.ok().body(flight_service.findAllFlights());
	}
	
	@RequestMapping(path = "read/flights/id={flight_id}", method = RequestMethod.GET)
	public ResponseEntity<Flight> findFlightById(@PathVariable Integer flight_id) {
		Optional<Flight> flight = flight_service.findFlightById(flight_id);
		if(flight.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().body(flight.get());
	}
	
	@RequestMapping(path = "/find/flights/airplane/id={airplane_id}", method = RequestMethod.GET)
	public ResponseEntity<List<Flight>> findFlightByAirplane(@PathVariable Integer airplane_id) {
			return ResponseEntity.ok().body(flight_service.findFlightByAirplane(airplane_id));
		}
	@RequestMapping(path = "/find/flights/route/id={route_id}", method = RequestMethod.GET)
	public ResponseEntity<List<Flight>> findFlightByRoute(@PathVariable Integer route_id) {
			return ResponseEntity.ok().body(flight_service.findFlightByRoute(route_id));
		}	
	
	
	@PostMapping(path = "/add/flight")
	public ResponseEntity<Flight> saveRoute(@RequestBody Flight flight) {
		Optional<Flight> new_flight = flight_service.save(flight);
		
		if(new_flight.isEmpty()) {
			return ResponseEntity.badRequest().body(flight);
		}
		
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/flights").toUriString());
		return ResponseEntity.created(uri).body(new_flight.get());
	}
	
	@PutMapping(path = "/update/flight")
	public ResponseEntity<Flight> updateFlight(@RequestBody Flight flight) {
		Optional<Flight> new_flight = flight_service.update(flight);
		if(new_flight.isPresent()) {
			return ResponseEntity.ok().body(new_flight.get());
		}
		return ResponseEntity.noContent().build();
	}
	
	@Modifying
	@Transactional	
	@DeleteMapping(path = "/delete/flight/id={flight_id}")
	public ResponseEntity<?> deleteFlight(@PathVariable Integer flight_id) {
		flight_service.deleteFlight(flight_id);
		return ResponseEntity.noContent().build();

	}
}
