package com.ss.utopia.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.utopia.api.dao.FlightRepository;
import com.ss.utopia.api.pojo.Flight;

@Service
public class FlightService {

	@Autowired
	FlightRepository flight_repository;
	
	public List<Flight> findAllFlights() {
		return flight_repository.findAll();
	}


	public Optional<Flight> findFlightById(Integer flight_id) {
		return flight_repository.findById(flight_id);
	}
	
	
	public Optional<Flight> save(Flight flight) {
		try {

			return Optional.of(flight_repository.save(flight));

		} catch (IllegalArgumentException e) {

			// e.printStackTrace();
			return Optional.empty();
		}
	}
	
	
	
	@Transactional
	public Optional<Flight> update(Flight flight) {
		if (flight_repository.existsById(flight.getId())) {
			Flight flight_to_save = flight_repository.findById(flight.getId()).get();
			if (flight.getAirplane_id() != null) {
				flight_to_save.setAirplane_id(flight.getAirplane_id());
			}
			if (flight.getRoute_id() != null) {
				flight_to_save.setRoute_id(flight.getRoute_id());
			}
			if (flight.getDeparture_time() != null) {
				flight_to_save.setDeparture_time(flight.getDeparture_time());
			}
			if (flight.getReserved_seats() != null) {
				flight_to_save.setReserved_seats(flight.getReserved_seats());
			}
			if (flight.getSeat_price() != null) {
				flight_to_save.setSeat_price(flight.getSeat_price());
			}
			return Optional.of(flight_to_save);
		}
		return Optional.empty();
	}
	
	
	
	/* Special Queries */

	public List<Flight> findFlightByRoute(Integer route_id) {
		return flight_repository.findAll().stream().filter(x -> x.getRoute_id().equals(route_id))
				.collect(Collectors.toList());
	}

	public List<Flight> findFlightByAirplane(Integer airplane_id) {
		return flight_repository.findAll().stream().filter(x -> x.getAirplane_id().equals(airplane_id))
				.collect(Collectors.toList());
	}
	
	
	public void deleteFlight(Integer flight_id) {
		flight_repository.deleteById(flight_id);
	}
}
