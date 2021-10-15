package com.ss.utopia.api.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.ss.utopia.api.dao.AirplaneRepository;
import com.ss.utopia.api.dao.AirplaneTypeRepository;
import com.ss.utopia.api.dao.AirportRepository;
import com.ss.utopia.api.dao.FlightRepository;
import com.ss.utopia.api.dao.RouteRepository;
import com.ss.utopia.api.pojo.Airplane;
import com.ss.utopia.api.pojo.AirplaneType;
import com.ss.utopia.api.pojo.Airport;
import com.ss.utopia.api.pojo.Flight;
import com.ss.utopia.api.pojo.Route;

@Service
public class AirlineService {

	@Autowired
	AirportRepository airport_repository;

	@Autowired
	AirplaneRepository airplane_repository;

	@Autowired
	AirplaneTypeRepository airplane_type_repository;

	@Autowired
	FlightRepository flight_repository;

	@Autowired
	RouteRepository route_repository;

	@Autowired
	SessionFactory sessionFactory;

	public List<Airport> findAllAirports() {
		return airport_repository.findAll();
	}

	public Airport getAirportById(String airport_code) {
		if (airport_code == null) {
			return null;
		}
		return airport_repository.getAirportById(airport_code.toUpperCase()).get();

	}

	public List<Route> findAllRoutes() {
		return route_repository.findAll();
	}

	public Route getRouteById(Integer route_id) {
		return route_repository.existsById(route_id) ? route_repository.getById(route_id) : null;
	}

	public List<AirplaneType> findAllAirplaneTypes() {
		return airplane_type_repository.findAll();
	}

	public AirplaneType getAirplaneTypeById(Integer airplane_type_id) {
		return airplane_type_repository.findById(airplane_type_id).get();
	}

	public List<Airplane> findAllAirplanes() {
		return airplane_repository.findAll();
	}

	public Airplane getAirplaneById(Integer airplane_id) {
		return airplane_repository.findById(airplane_id).get();
	}

	public List<Flight> findAllFlights() {
		return flight_repository.findAll();
	}

	public Flight getFlightById(Integer flight_id) {
		return flight_repository.existsById(flight_id) ? flight_repository.getById(flight_id) : null;
	}

	
	public Optional<Airport> save(Airport airport) {

		try {
		if (airport_repository.existsById(airport.getIataId())) {
			
			return Optional.empty();
		}
		
			List<Route> origin_routes = new ArrayList<>();
			
			
			if (airport.getAs_origin() != null) {
				origin_routes = airport.getAs_origin().stream().peek(x -> x.setOrigin_id(airport.getIataId()))
						.collect(Collectors.toList());
				airport.setAs_origin(null);

			}
			List<Route> destination_routes = new ArrayList<>();
			if (airport.getAs_destination() != null) {
				destination_routes = airport.getAs_destination().stream()
						.peek(x -> x.setDestination_id(airport.getIataId())).collect(Collectors.toList());
				airport.setAs_destination(null);

			}

			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();

			airport_repository.save(airport);

			for (Route route : origin_routes) {

				save(route);
			}
			for (Route route : destination_routes) {
				save(route);
			}

			tx.commit();
			session.close();
			
			airport.setAs_destination(destination_routes);
			airport.setAs_origin(origin_routes);
			
			return Optional.of(airport);

		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
			return Optional.empty();
		}

	}

	@Transactional
	public Optional<Route> save(Route route) {
		try {

			Route persist_route = new Route();
			persist_route.setOrigin_id(route.getOrigin_id());
			persist_route.setDestination_id(route.getDestination_id());
			
			persist_route = route_repository.save(persist_route);
			Integer route_id = persist_route.getId();
			if (route.getFlights() != null) {
				
				route.getFlights().forEach(x -> {
					x.setRoute_id(route_id);
					save(x);
				});
			}
			
			
			
			return Optional.of(persist_route);

		} catch (IllegalArgumentException e) {

			// e.printStackTrace();
			
			return Optional.empty();
		}
	}

	public Airplane save(Airplane airplane) {
		try {

			return airplane_repository.save(airplane);

		} catch (IllegalArgumentException e) {

			return null;
		}
	}

	public AirplaneType save(AirplaneType airplane_type) {
		try {
			return airplane_type_repository.save(airplane_type);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			return null;
		}
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
	public Optional<Airport> update(Airport airport) {
		try {

		if (!airport_repository.existsById(airport.getIataId().toUpperCase())) {

			return Optional.empty();

		}

		System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
		System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
		System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
		System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
		System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
			Airport airport_to_update = airport_repository.getAirportById(airport.getIataId()).get();
			System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGg");
			System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGg");
			System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGg");
			System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGg");
			System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGg");
			if (airport.getAs_destination() != null) {
				System.out.println(airport.getAs_destination());
				airport.getAs_destination().forEach(x -> x.setDestination_id(airport.getIataId()));
				System.out.println(airport.getAs_destination());
				System.out.println(airport.getAs_destination().getClass());

			}
			System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
			System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
			System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
			System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
			System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
			if (airport.getAs_origin() != null) {
				System.out.println(airport.getAs_origin());
				airport.getAs_origin().forEach(x -> x.setOrigin_id(airport.getIataId()));
				System.out.println(airport.getAs_origin());


			}
			System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGg");
			System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGg");
			System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGg");
			System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGg");
			System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGg");
			airport_to_update.setAs_destination(airport.getAs_destination());
			airport_to_update.setAs_origin(airport.getAs_origin());

			airport_to_update.setCity(airport.getCity());
			System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
			System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
			System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
			System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
			System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
			return Optional.of(airport_to_update);

		} catch (Exception e) {
			return Optional.empty();
		}

	}

	@Transactional
	public Optional<Airport> updateOverwrite(Airport airport) {

		if (airport_repository.existsById(airport.getIataId().toUpperCase())) {
			Airport airport_to_save = airport_repository.getAirportById(airport.getIataId()).get();
			if (airport.getCity() != null) {
				airport_to_save.setCity(airport.getCity());
			}
			return Optional.of(airport_to_save);
		}

		return Optional.empty();
	}

	@Transactional
	public Optional<Route> update(Route route) {
		if (route_repository.existsById(route.getId())) {
			Route route_to_save = route_repository.findById(route.getId()).get();
			if (route.getOrigin_id() != null) {
				route_to_save.setOrigin_id(route.getOrigin_id());
			}
			if (route.getDestination_id() != null) {
				route_to_save.setDestination_id(route.getDestination_id());
			}
			if (route.getFlights() != null) {
				route_to_save.setFlights(route.getFlights());
			}

			return Optional.of(route_to_save);
		}
		return Optional.empty();
	}

	public Optional<AirplaneType> update(AirplaneType airplane_type) {
		if (airplane_type_repository.existsById(airplane_type.getId())) {
			airplane_type_repository.save(airplane_type);
			return Optional.of(airplane_type);
		}
		return Optional.empty();
	}

	public Optional<Airplane> update(Airplane airplane) {
		if (airplane_repository.existsById(airplane.getId())) {
			airplane_repository.save(airplane);
			return Optional.of(airplane);
		}
		return Optional.empty();
	}

	public Optional<Flight> findFlightById(Integer flight_id) {
		return flight_repository.findById(flight_id);
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

	/* JpaRepository custom query to handle String airport_code */
	public void deleteAirportById(String airport_code) {

		airport_repository.deleteAirportById(airport_code.toUpperCase());
	}

	public void deleteRoute(Integer route_id) {
		route_repository.deleteById(route_id);
	}

	public void deleteAirplaneType(Integer airplane_type_id) {
		airplane_type_repository.deleteById(airplane_type_id);
	}

	public void deleteAirplane(Integer airplane_id) {
		airplane_repository.deleteById(airplane_id);
	}

	public void deleteFlight(Integer flight_id) {
		flight_repository.deleteById(flight_id);
	}

	/* Special Queries */

	public List<Flight> findFlightByRoute(Integer route_id) {
		return flight_repository.findAll().stream().filter(x -> x.getRoute_id() == route_id)
				.collect(Collectors.toList());
	}

	public List<Flight> findFlightByAirplane(Integer airplane_id) {
		return flight_repository.findAll().stream().filter(x -> x.getAirplane_id() == airplane_id)
				.collect(Collectors.toList());
	}

//	public List<Flight> filterFlightByDate(List<Flight> flights, LocalDate after, LocalDate before) {
//		if (after == null)
//			after = LocalDate.now();
//		if (before == null)
//			before = LocalDate.now().plusYears(10);
//		flights.stream().filter(x -> x.getDeparture_time().isBefore(before.atStartOfDay())
//				&& x.getDeparture_time().isAfter(after.atStartOfDay())).collect(Collectors.toList());
//	}

//	public List<Airplane> filterAirplaneByMaxCapacity(Integer min, Integer max) {
//		if (min == null)
//			min = 0;
//		if (max == null)
//			max = Integer.MAX_VALUE;
//		Integer min_final = min;
//		Integer max_final = max;
//		return airplane_type_repository.findAll().stream()
//				.filter(x -> x.getMax_capacity() >= min_final && x.getMax_capacity() <= max_final)
//				.map(x -> airplane_repository.findByType(x.getId())).collect(Collectors.toList()).stream().flatMap(List::stream)
//		        .collect(Collectors.toList());
//	}

}