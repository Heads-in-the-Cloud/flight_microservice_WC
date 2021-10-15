package com.ss.utopia.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ss.utopia.api.dao.RouteRepository;
import com.ss.utopia.api.pojo.Flight;
import com.ss.utopia.api.pojo.Route;

@Service
public class RouteService {

	
	
	@Autowired
	RouteRepository route_repository;
	
	
	@Autowired
	FlightService flight_service;
	
	@Autowired
	SessionFactory sessionFactory;
	
	

	public List<Route> findAllRoutes() {
		return route_repository.findAll();
	}

	public Optional<Route> getRouteById(Integer route_id) {
		if(route_repository.existsById(route_id)) {
		
		return Optional.of(route_repository.getById(route_id));
		}
		
		return Optional.empty();
		
	}
	
	
	@Transactional
	public Optional<Route> save(Route route) {

		try {

			Route persist_route = new Route();

			persist_route.setOrigin_id(route.getOrigin_id());
			persist_route.setDestination_id(route.getDestination_id());

			persist_route = route_repository.save(persist_route);
			
			List<Flight> saved_flights = new ArrayList<>();
			Integer route_id = persist_route.getId();
			if (route.getFlights() != null) {

				route.getFlights().forEach(x -> {
					x.setRoute_id(route_id);
					//flight_service.save(x);
					saved_flights.add(x);
				});
			}
			
			persist_route.setFlights(saved_flights);
			return Optional.of(persist_route);


		} catch (DataIntegrityViolationException e) {

			// e.printStackTrace();

			return Optional.empty();
		}

	}
	
	
	@Transactional
	public Optional<Route> update(Route route) {
		
		try {
			Route route_to_save = route_repository.findById(route.getId()).get();
			if (route.getOrigin_id() != null) {
				route_to_save.setOrigin_id(route.getOrigin_id());
			}
			if (route.getDestination_id() != null) {
				route_to_save.setDestination_id(route.getDestination_id());
			}
			if (route.getFlights() != null) {
				
				route.getFlights().forEach(x -> x.setRoute_id(route_to_save.getId()));
				
				
				route_to_save.setFlights(route.getFlights());
			}

			return Optional.of(route_to_save);
		} catch(Exception e) {
			return Optional.empty();
		}
		
	}
	
	
	public void deleteRoute(Integer route_id) {
		route_repository.deleteById(route_id);
	}
}
