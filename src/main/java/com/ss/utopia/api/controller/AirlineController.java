package com.ss.utopia.api.controller;

import java.net.URI;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ss.utopia.api.dao.AirplaneRepository;
import com.ss.utopia.api.dao.AirplaneTypeRepository;
import com.ss.utopia.api.dao.AirportRepository;
import com.ss.utopia.api.dao.RouteRepository;
import com.ss.utopia.api.pojo.Airplane;
import com.ss.utopia.api.pojo.AirplaneType;
import com.ss.utopia.api.pojo.Airport;
import com.ss.utopia.api.pojo.Flight;
import com.ss.utopia.api.pojo.Route;
import com.ss.utopia.api.service.AirlineService;


@RestController
@RequestMapping("/airline")
public class AirlineController {

	@Autowired
	AirlineService airline_service;
	
	@Autowired
	AirplaneRepository airplane_repository;
	
	@Autowired
	AirplaneTypeRepository airplane_type_repository;
	
	@Autowired
	AirportRepository airport_repository;
	
	@Autowired
	RouteRepository route_repository;
	

	
	
	
	
	@PostMapping(path = "/add/airport")
	public ResponseEntity<Airport> saveAirport(@RequestBody Airport airport) {
		
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/airports").toUriString());
		return ResponseEntity.created(uri).body(airline_service.save(airport));
	}
	
	@PostMapping(path = "/add/route")
	public ResponseEntity<Route> saveRoute(@RequestBody Route route) {
		
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/routes").toUriString());
		return ResponseEntity.created(uri).body(airline_service.save(route));
	}
	
	@PostMapping(path = "/add/airplane")
	public ResponseEntity<Airplane> saveAirplane(@RequestBody Airplane airplane) {
		
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/airplanes").toUriString());
		return ResponseEntity.created(uri).body(airline_service.save(airplane));
	}
	@PostMapping(path = "/add/airplane_type")
	public ResponseEntity<AirplaneType> saveAirplaneType(@RequestBody AirplaneType airplane_type) {
		
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/airplane_types").toUriString());
		return ResponseEntity.created(uri).body(airline_service.save(airplane_type));
	}
	
	
	@PostMapping(path = "/add/flight")
	public ResponseEntity<Flight> saveRoute(@RequestBody Flight flight) {

		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/flights").toUriString());
		return ResponseEntity.created(uri).body(airline_service.save(flight));
	}
	

	@PutMapping(path = "/update/airport")
	public ResponseEntity<Airport> updateAirport(@RequestBody Airport airport) {
		Optional<Airport> new_airport = airline_service.update(airport);
		if(new_airport.isPresent()) {
			return ResponseEntity.ok().body(new_airport.get());
		}
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(path = "/update/route")
	public ResponseEntity<Route> updateRoute(@RequestBody Route route) {
		Optional<Route> new_route = airline_service.update(route);
		if(new_route.isPresent()) {
			return ResponseEntity.ok().body(new_route.get());
		}
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(path = "/update/airplane_type")
	public ResponseEntity<AirplaneType> updateAirplaneType(@RequestBody AirplaneType airplane_type) {
		Optional<AirplaneType> new_airplane_type = airline_service.update(airplane_type);
		if(new_airplane_type.isPresent()) {
			return ResponseEntity.ok().body(new_airplane_type.get());
		}
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(path = "/update/airplane")
	public ResponseEntity<Airplane> updateAirplane(@RequestBody Airplane airplane) {
		Optional<Airplane> new_airplane = airline_service.update(airplane);
		if(new_airplane.isPresent()) {
			return ResponseEntity.ok().body(new_airplane.get());
		}
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(path = "/update/flight")
	public ResponseEntity<Flight> updateFlight(@RequestBody Flight flight) {
		Optional<Flight> new_flight = airline_service.update(flight);
		if(new_flight.isPresent()) {
			return ResponseEntity.ok().body(new_flight.get());
		}
		return ResponseEntity.noContent().build();
	}
	
	
	
	

	@RequestMapping(path = "read/airports/id={airport_code}", method = RequestMethod.GET)
	public ResponseEntity<Airport> getAirportById(@PathVariable String airport_code) {
		return ResponseEntity.ok().body(airline_service.getAirportById(airport_code));
	}
	@RequestMapping(path = "read/airports", method = RequestMethod.GET)
	public ResponseEntity<List<Airport>> findAllAirports() {
		return ResponseEntity.ok().body(airline_service.findAllAirports());
	}
	
	@RequestMapping(path = "read/routes/{route_id}", method = RequestMethod.GET)
	public ResponseEntity<Route> getRouteById(@PathVariable Integer route_id) {
		return ResponseEntity.ok().body(airline_service.getRouteById(route_id));
	}
	
	@RequestMapping(path = "read/routes", method = RequestMethod.GET)
	public ResponseEntity<List<Route>> findAllRoutes() {
		return ResponseEntity.ok().body(airline_service.findAllRoutes());
	}
	
	@RequestMapping(path = "read/airplanes/{airplane_id}", method = RequestMethod.GET)
	public ResponseEntity<Airplane> getAirplaneById(@PathVariable Integer airplane_id) {
		return ResponseEntity.ok().body(airline_service.getAirplaneById(airplane_id));
	}
	@RequestMapping(path = "/airplanes", method = RequestMethod.GET)
	public ResponseEntity<List<Airplane>> findAllAirplanes() {
		return ResponseEntity.ok().body(airline_service.findAllAirplanes());
	}
	@RequestMapping(path = "read/airplane_types/{airplane_type_id}", method = RequestMethod.GET)
	public ResponseEntity<AirplaneType> getAirplaneTypeById(@PathVariable Integer airplane_type_id) {
		return ResponseEntity.ok().body(airline_service.getAirplaneTypeById(airplane_type_id));
	}
	@RequestMapping(path = "read/airplane_types", method = RequestMethod.GET)
	public ResponseEntity<List<AirplaneType>> findAllAirplaneTypes() {
		return ResponseEntity.ok().body(airline_service.findAllAirplaneTypes());
	}
	
	@RequestMapping(path = "read/flights", method = RequestMethod.GET)
	public ResponseEntity<List<Flight>> findAllFlights() {
		return ResponseEntity.ok().body(airline_service.findAllFlights());
	}
	
	@RequestMapping(path = "read/flights/id={flight_id}", method = RequestMethod.GET)
	public ResponseEntity<Flight> findFlightById(@PathVariable Integer flight_id) {
		Optional<Flight> flight = airline_service.findFlightById(flight_id);
		if(flight.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().body(flight.get());
	}
	
	
	@RequestMapping(path = "/find/airplanes/{airplane_type_id}", method = RequestMethod.GET)
	public ResponseEntity<List<Airplane>> findAirplanesByType(@PathVariable Integer airplane_type_id) {
		return ResponseEntity.ok().body(airplane_repository.findByType(airplane_type_id));
	}
	
	/* Special Queries */
	
	@RequestMapping(path = "/find/flights/airplane/{airplane_id}", method = RequestMethod.GET)
	public ResponseEntity<List<Flight>> findFlightByAirplane(@PathVariable Integer airplane_id) {
			return ResponseEntity.ok().body(airline_service.findFlightByAirplane(airplane_id));
		}
	@RequestMapping(path = "/find/flights/route/{route_id}", method = RequestMethod.GET)
	public ResponseEntity<List<Flight>> findFlightByRoute(@PathVariable Integer route_id) {
			return ResponseEntity.ok().body(airline_service.findFlightByRoute(route_id));
		}	
	
	@RequestMapping(path = "/find/routes/outgoing/{airport_code}", method = RequestMethod.GET)
	public ResponseEntity<List<Route>> findValidDestinationAirport(@PathVariable String airport_code) {
			return ResponseEntity.ok().body(airline_service.getAirportById(airport_code).getAs_origin());
		}
	
	@RequestMapping(path = "/find/routes/incoming/{airport_code}", method = RequestMethod.GET)
	public ResponseEntity<List<Route>> findValidOriginAirport(@PathVariable String airport_code) {
			return ResponseEntity.ok().body(airline_service.getAirportById(airport_code).getAs_destination());
		}
	
	
	
	
	@Modifying
	@Transactional	
	@DeleteMapping(path = "/delete/airport/{airport_code}")
	public ResponseEntity<?> deleteAirport(@PathVariable String airport_code) {
		airline_service.deleteAirportById(airport_code);
		return ResponseEntity.noContent().build();

	}
	
	@Modifying
	@Transactional	
	@DeleteMapping(path = "/delete/airplane_type/{airplane_type_id}")
	public ResponseEntity<?> deleteAirplaneType(@PathVariable Integer airplane_type_id) {
		airline_service.deleteAirplaneType(airplane_type_id);
		return ResponseEntity.noContent().build();

	}
	@Modifying
	@Transactional	
	@DeleteMapping(path = "/delete/airplane/{airplane_id}")
	public ResponseEntity<?> deleteAirplane(@PathVariable Integer airplane_id) {
		airline_service.deleteAirplane(airplane_id);
		return ResponseEntity.noContent().build();

	}
	@Modifying
	@Transactional	
	@DeleteMapping(path = "/delete/route/{route_id}")
	public ResponseEntity<?> deleteRoute(@PathVariable Integer route_id) {
		airline_service.deleteRoute(route_id);
		return ResponseEntity.noContent().build();

	}
	
	@Modifying
	@Transactional	
	@DeleteMapping(path = "/delete/flight/{flight_id}")
	public ResponseEntity<?> deleteFlight(@PathVariable Integer flight_id) {
		airline_service.deleteFlight(flight_id);
		return ResponseEntity.noContent().build();

	}
	
	
	
	
	
	
	
}
