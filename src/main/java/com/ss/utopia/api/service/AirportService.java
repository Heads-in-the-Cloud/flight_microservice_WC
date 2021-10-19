package com.ss.utopia.api.service;

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

import com.ss.utopia.api.dao.AirportRepository;
import com.ss.utopia.api.pojo.Airport;
import com.ss.utopia.api.pojo.Flight;
import com.ss.utopia.api.pojo.Route;

@Service
public class AirportService {

	@Autowired
	AirportRepository airport_repository;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	FlightService flight_service;
	
	@Autowired
	RouteService route_service;
	
	
	public List<Airport> findAllAirports() {
		return airport_repository.findAll();
	}

	public Airport getAirportById(String airport_code) {
	
		return airport_repository.getAirportById(airport_code.toUpperCase()).get();
	}
	
	
	
	
	public Airport save(Airport airport) {

			List<Route> origin_routes = new ArrayList<>();
			List<Route> destination_routes = new ArrayList<>();

			//Store airport lists in temporary variables and save airport parent before children
			
			if (airport.getAs_origin() != null) {
				origin_routes = airport.getAs_origin().stream().peek(x -> x.setOrigin_id(airport.getIataId()))
						.collect(Collectors.toList());
				airport.setAs_origin(null);

			}
			if (airport.getAs_destination() != null) {
				destination_routes = airport.getAs_destination().stream()
						.peek(x -> x.setDestination_id(airport.getIataId())).collect(Collectors.toList());
				airport.setAs_destination(null);

			}

			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();

			airport_repository.save(airport);

			for (int i =0; i<origin_routes.size(); i++) {

				Integer route_id = route_service.save(origin_routes.get(i)).getId();
				origin_routes.get(i).setId(route_id);
			}
			for (int i =0; i<destination_routes.size(); i++) {
				
				Integer route_id = route_service.save(destination_routes.get(i)).getId();
				destination_routes.get(i).setId(route_id);
			}

			tx.commit();
			session.close();

			airport.setAs_destination(destination_routes);
			airport.setAs_origin(origin_routes);

			return airport;


	}
	
	
	
	
	
	@Transactional
	public Airport update(Airport airport) {
					
	
			Airport airport_to_update = airport_repository.getAirportById(airport.getIataId()).get();
			
			List<Route> origins = airport_to_update.getAs_origin();
			List<Route> destinations = airport_to_update.getAs_destination();
			
		
			
			if (airport.getAs_destination() != null) {
				
				for (Route r : airport.getAs_destination()) {
					r.setDestination_id(airport.getIataId());

					if (destinations.contains(r)) { 
						Route route_to_update = destinations.get(destinations.indexOf(r));
												
						//set flight_id to existing route_id
						if (r.getFlights() != null) { 
							r.getFlights().forEach(x -> {
								
								x.setRoute_id(route_to_update.getId());
								
								route_to_update.getFlights().add(flight_service.save(x));
								

							});
							
						}

					} else {
						

						List<Flight> flights = r.getFlights();
						r.setFlights(null);
						Route saved_route = route_service.save(r);
						if (flights != null) {
							
							flights.forEach(x -> {
								x.setRoute_id(saved_route.getId());
								flight_service.save(x);

							});

						}
						saved_route.setFlights(flights);
						System.out.println(flights);
						System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFf");
						System.out.println(saved_route.getFlights());
						System.out.println(saved_route);
						airport_to_update.getAs_destination().add(saved_route);
						System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");

					}

				}
			}

			if (airport.getAs_origin() != null) {

				for (Route r : airport.getAs_origin()) {
					r.setOrigin_id(airport.getIataId());

					if (origins.contains(r)) {

						Route route_to_update = origins.get(origins.indexOf(r));
						
						//set flight_id to existing route_id
						if (r.getFlights() != null) {
							r.getFlights().forEach(x -> {

								x.setRoute_id(route_to_update.getId());
								
								route_to_update.getFlights().add(flight_service.save(x));

							});
						}

					} else {
						
						
						List<Flight> flights = r.getFlights();
						r.setFlights(null);

						Route saved_route = route_service.save(r);
						if (flights != null) {
							
							flights.forEach(x -> {
								x.setRoute_id(saved_route.getId());
								flight_service.save(x);

							});
						}
						
						saved_route.setFlights(flights);
						airport_to_update.getAs_destination().add(saved_route);

					}
				}

			}

			if (airport.getCity() != null) {
				airport_to_update.setCity(airport.getCity());
			}
			

		return airport_to_update;
		

	}
	
	
	
	
	
	/* JpaRepository custom query to handle String airport_code */
	public void deleteAirportById(String airport_code) {

		airport_repository.deleteAirportById(airport_code.toUpperCase());
	}
	
}
