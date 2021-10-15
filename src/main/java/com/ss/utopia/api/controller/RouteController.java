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

import com.ss.utopia.api.pojo.Route;
import com.ss.utopia.api.service.AirportService;
import com.ss.utopia.api.service.RouteService;

@RestController
public class RouteController {

	@Autowired 
	RouteService route_service;
	
	@Autowired
	AirportService airport_service;
	
	
	
	
	@RequestMapping(path = "read/routes/id={route_id}", method = RequestMethod.GET)
	public ResponseEntity<Route> getRouteById(@PathVariable Integer route_id) {
		Optional<Route> route = route_service.getRouteById(route_id);
		if(route.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.ok().body(route.get());
	}
	
	@RequestMapping(path = "read/routes", method = RequestMethod.GET)
	public ResponseEntity<List<Route>> findAllRoutes() {
		return ResponseEntity.ok().body(route_service.findAllRoutes());
	}
	
	@RequestMapping(path = "/find/routes/outgoing/id={airport_code}", method = RequestMethod.GET)
	public ResponseEntity<List<Route>> findValidDestinationAirport(@PathVariable String airport_code) {
			return ResponseEntity.ok().body(airport_service.getAirportById(airport_code).getAs_origin());
		}
	
	@RequestMapping(path = "/find/routes/incoming/id={airport_code}", method = RequestMethod.GET)
	public ResponseEntity<List<Route>> findValidOriginAirport(@PathVariable String airport_code) {
			return ResponseEntity.ok().body(airport_service.getAirportById(airport_code).getAs_destination());
		}
	
	@PostMapping(path = "/add/route")
	public ResponseEntity<Route> saveRoute(@RequestBody Route route) {
		Optional<Route> new_route = route_service.save(route);
		if(new_route.isEmpty()) {
			return ResponseEntity.badRequest().body(route);
		}
		
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/routes").toUriString());
		return ResponseEntity.created(uri).body(new_route.get());
	}
	
	@Transactional
	@PutMapping(path = "/update/route")
	public ResponseEntity<Route> updateRoute(@RequestBody Route route) {
		Optional<Route> new_route = route_service.update(route);
		if(new_route.isPresent()) {
			return ResponseEntity.ok().body(new_route.get());
		}
		return ResponseEntity.noContent().build();
	}
	
	@Modifying
	@Transactional	
	@DeleteMapping(path = "/delete/route/id={route_id}")
	public ResponseEntity<?> deleteRoute(@PathVariable Integer route_id) {
		route_service.deleteRoute(route_id);
		return ResponseEntity.noContent().build();

	}
}
