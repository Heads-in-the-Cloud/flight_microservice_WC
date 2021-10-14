package com.ss.utopia.api;

import static org.junit.jupiter.api.Assertions.assertEquals;



import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.test.context.junit4.SpringRunner;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

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
import com.ss.utopia.api.service.AirlineService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AirlineServiceTests {
		
		@Autowired
		AirlineService airline_service;
		
		
		@Autowired
		AirplaneRepository airplane_repository;
		
		
		@Autowired
		AirplaneTypeRepository airplane_type_repository;
		
		
		@Autowired
		AirportRepository airport_repository;
		
		@Autowired
		FlightRepository flight_repository;
		
		@Autowired
		RouteRepository route_repository;
		
		
		
		
		
		@Nested
		public 	class testAirplane{
						
			Airplane airplane = new Airplane();
			

			Integer bad_id = 9999999;
			Integer good_id;
			Integer type_id = 1;
		
			
			public void init() {
				airplane.setType_id(type_id);
				
				this.airplane = airline_service.save(airplane);
				this.good_id = airplane.getId();

				this.type_id = airplane.getType_id();
			}
			
			public void tearDown() {
				airline_service.deleteAirplane(good_id);
			}
			
			@Test
			public void testFindAirplane() {

				assertEquals(airline_service.getAirplaneById(1).getId(), 1);
				
			}
			
			@Transactional
			@Test
			public void testTypeId() {
				// TODO Auto-generated method stub
				init();
				assertEquals(airline_service.getAirplaneById(good_id).getType_id(), type_id);
				tearDown();
			}
			
			@Transactional
			@Test
			public void testId() {
				init();
				assertEquals(airplane_repository.getById(airplane.getId()).getId(), airplane.getId());
				tearDown();
			}
			
			
			
			
			@Transactional
			@Test
			public void isDeleted() {
				init();
				tearDown();
				assertEquals(airplane_repository.existsById(airplane.getId()), false);
			}
			
			@Test
			public void isUpdate() {
				init();
				
				airplane.setType_id(2);
				airline_service.update(airplane);
				assertEquals(airline_service.getAirplaneById(good_id).getType_id(), 2);
				
				airplane.setType_id(type_id);
				airline_service.update(airplane);
				
				tearDown();
				
			}
			
			
			@Test
			public void testNull() {
				 assertThrows(InvalidDataAccessApiUsageException.class,
				            ()->{
				            airline_service.getAirplaneById(null);
				            });
			}
			@Test
			public void testFalse() {
				 assertThrows(NoSuchElementException.class,
				            ()->{
				            airline_service.getAirplaneById(bad_id);
				            });
			}
			
			
			
		}
		
		
		
		@Nested
		public 	class testAirplaneType{
						
			AirplaneType airplane_type = new AirplaneType();
			

			Integer bad_id = 9999999;
			Integer good_id;
			Integer maximum_capacity = 100;
		
			
			public void init() {
				airplane_type.setMax_capacity(maximum_capacity);
				this.airplane_type = airline_service.save(airplane_type);
				this.good_id = airplane_type.getId();
				this.maximum_capacity = airplane_type.getMax_capacity();
			}
			
			public void tearDown() {
				airline_service.deleteAirplaneType(good_id);
			}
			
			@Test
			public void testFindAirplaneType() {

				assertEquals(airline_service.getAirplaneTypeById(1).getId(), 1);
				
			}
			
			@Transactional
			@Test
			public void testMaxCapacity() {
				// TODO Auto-generated method stub
				init();
				assertEquals(airline_service.getAirplaneTypeById(good_id).getMax_capacity(), maximum_capacity);
				tearDown();
			}
			
			@Test
			public void testId() {
				init();
				assertEquals(airplane_type_repository.getById(airplane_type.getId()).getId(), airplane_type.getId());
				tearDown();
			}
			
			
			@Test
			public void testUpdate() {
				init();
				airplane_type.setMax_capacity(909);
				airline_service.update(airplane_type);
				assertEquals(airline_service.getAirplaneTypeById(good_id).getMax_capacity(), 909);
				airplane_type.setMax_capacity(maximum_capacity);
				airline_service.update(airplane_type);
				tearDown();
				
			}
			
			@Test
			public void isDeleted() {
				init();
				tearDown();
				assertEquals(airplane_type_repository.existsById(good_id), false);
			}
			@Test
			public void testNull() {
				 assertThrows(InvalidDataAccessApiUsageException.class,
				            ()->{
								 airline_service.getAirplaneTypeById(null);
				            });
			}
			
			@Test
			public void testFalse() {
				 assertThrows(NoSuchElementException.class,
				            ()->{
				            airline_service.getAirplaneTypeById(bad_id);
				            });
			}
			
			
			
		}
		
		
		@Transactional
		@Test
		public void testNoRollback() {
			AirplaneType at = new AirplaneType();
			at.setId(null);
			at.setMax_capacity(2929);
			
			AirplaneType at_saved = airline_service.save(at);

			assertEquals(airline_service.getAirplaneTypeById(at_saved.getId()).getMax_capacity(), 2929);
			
			
		}
		
		
		@Nested
		public 	class testAirport{
						
			Airport airport = new Airport();
			
			String existing_airport_id = "LAX";
			String bad_id = "ABCD";
			String good_id = "ABC";
			String city = "Chino Hills";
		
			
			public void init() {
				airport.setCity(city);
				airport.setIataId(good_id);
				this.airport = airline_service.save(airport);
				this.good_id = airport.getIataId();
				this.city = airport.getCity();
			
			}
			
			public void tearDown() {
				airline_service.deleteAirportById(good_id);
			}
			
			@Test
			public void testFindAirport() {

				assertEquals(airline_service.getAirportById("LAX").getCity(), "Los Angeles");
				
			}
			
			@Transactional
			@Test
			public void testCity() {
				// TODO Auto-generated method stub
				init();
				assertEquals(airline_service.getAirportById(good_id).getCity(), city);
				tearDown();
			}
			
			@Test
			public void testId() {
				init();
				assertEquals(airline_service.getAirportById(airport.getIataId()).getIataId(), good_id);
				tearDown();
			}
			
			@Test
			public void testUpdate() {
				init();
				String update_city = "cityname";
				airport.setCity(update_city);
				airline_service.update(airport);
				assertEquals(airline_service.getAirportById(good_id).getCity(), update_city);
				airport.setCity(city);
				airline_service.update(airport);
				tearDown();
				
			}
			
			
			@Test
			public void isDeleted() {
				//init();
				//tearDown();
				assertEquals(airport_repository.existsById(good_id), false);
			}
			@Test
			public void testNull() {
				 assertEquals(airline_service.getAirportById(null), null);
			}
			
			@Test
			public void testFalse() {
				assertThrows(NoSuchElementException.class,
			            ()->{
							 airline_service.getAirportById(bad_id);
			            });
			
			}
			
			@Transactional
			@Test
			public void testAirportAsOrigin() {	
				for(Route r : airline_service.getAirportById(existing_airport_id).getAs_origin()) {
					assertEquals(r.getOrigin_id(),existing_airport_id);
				}
						
			}
			@Transactional
			@Test
			public void testAirportAsDestination() {
				for(Route r : airline_service.getAirportById(existing_airport_id).getAs_destination()) {
					assertEquals(r.getDestination_id(),existing_airport_id);
				}

						
			}
			
		}
		
		
		@Nested
		public 	class testFlight{
						
			Flight flight = new Flight();
			

			Integer good_id;
			Integer bad_id = 999;
			Integer route_id = 1;
			LocalDateTime dt = LocalDateTime.now();
			Integer airplane_id = 1;
			Integer reserved_seats = 0;
			Float seat_price = (float) 100.0;
		
			
			public void init() {
				flight.setRoute_id(route_id);
				flight.setAirplane_id(airplane_id);
				flight.setDeparture_time(dt);
				flight.setReserved_seats(reserved_seats);
				flight.setSeat_price(seat_price);
				
				this.flight = airline_service.save(flight);
				this.good_id = flight.getId();
				this.route_id = flight.getRoute_id();
				this.airplane_id = flight.getAirplane_id();
				this.reserved_seats = flight.getReserved_seats();
				this.seat_price = flight.getSeat_price();
				this.dt = flight.getDeparture_time();
			}
			
			public void tearDown() {
				airline_service.deleteFlight(good_id);
			}
			
			@Transactional
			@Test
			public void testRoute() {
				// TODO Auto-generated method stub
				init();
				assertEquals(airline_service.getFlightById(good_id).getRoute_id(), route_id);
				tearDown();
			}
			
			@Transactional
			@Test
			public void testAirplane() {
				// TODO Auto-generated method stub
				init();
				assertEquals(airline_service.getFlightById(good_id).getAirplane_id(), airplane_id);
				tearDown();
			}
			@Transactional
			@Test
			public void testDate() {
				// TODO Auto-generated method stub
				init();
				assertEquals(airline_service.getFlightById(good_id).getDeparture_time(), dt);
				tearDown();
			}
			
			@Transactional
			@Test
			public void testReservedSeats() {
				// TODO Auto-generated method stub
				init();
				assertEquals(airline_service.getFlightById(good_id).getReserved_seats(), reserved_seats);
				tearDown();
			}
			@Transactional
			@Test
			public void testSeatPrice() {
				// TODO Auto-generated method stub
				init();
				assertEquals(airline_service.getFlightById(good_id).getSeat_price(), seat_price);
				tearDown();
			}
			
			
			
			@Test
			public void testId() {
				init();
				assertEquals(airline_service.getFlightById(good_id).getId(), good_id);
				tearDown();
			}
			
			@Transactional
			@Test
			public void testUpdate() {
				init();
				
				flight.setReserved_seats(1000);
				flight.setSeat_price((float)99);
				flight.setDeparture_time(LocalDateTime.now());
				flight.setAirplane_id(1);
				flight.setRoute_id(1);
				Integer flight_id = flight.getId();
				
				Flight updated_flight = airline_service.getFlightById(flight_id);
				
				assertEquals(updated_flight.getAirplane_id(), 1);
				assertEquals(updated_flight.getDeparture_time(), flight.getDeparture_time());
				assertEquals(updated_flight.getRoute_id(), 1);
				assertEquals(updated_flight.getReserved_seats(), 1000);
				assertEquals(updated_flight.getSeat_price(), (float)99);
				
				tearDown();
				
				
			}
			
			@Test
			public void testFindFlightByRoute() {
				Integer route_id = 1;
				List<Flight> flights = airline_service.findFlightByRoute(route_id);
				for(Flight f : flights) {
					assertEquals(f.getRoute_id(),  route_id);
				}
				
				
			}
			
			@Test
			public void testFindFlightByAirplane() {
				Integer airplane_id = 1;
				List<Flight> flights = airline_service.findFlightByAirplane(airplane_id);
				for(Flight f : flights) {
					assertEquals(f.getAirplane_id(),  route_id);
				}
			}
			
			
			@Test
			public void isDeleted() {
				init();
				tearDown();
				assertEquals(flight_repository.existsById(good_id), false);
			}
			@Test
			public void testNull() {
				 assertThrows(InvalidDataAccessApiUsageException.class,
				            ()->{
								 airline_service.getFlightById(null);
				            });
			}
			
			@Test
			public void testFalse() {
				assertEquals(null, airline_service.getFlightById(bad_id));

				
			}
			
			
			
			
		}
		
		
		@Nested
		public 	class testRoute{
						
			Route route = new Route();
			

			Integer good_id;
			Integer bad_id = 9999;
			String origin_id = "DEN";
			String destination_id = "SFO";
			
		
			
			public void init() {
				route.setDestination_id(destination_id);
				route.setOrigin_id(origin_id);
				route.setId(null);
				this.route = airline_service.save(route);
				this.good_id = route.getId();
				this.origin_id = route.getOrigin_id();
				this.destination_id = route.getDestination_id();
				
			}
			
			public void tearDown() {
				airline_service.deleteRoute(good_id);
			}
			
			
			@Transactional
			@Test
			public void testOrigin() {
				// TODO Auto-generated method stub
				init();
				
				assertEquals(airline_service.getRouteById(good_id).getOrigin_id(), origin_id);
				tearDown();
			}
			@Transactional
			@Test
			public void testDestination() {
				// TODO Auto-generated method stub
				init();
				assertEquals(airline_service.getRouteById(good_id).getDestination_id(), destination_id);
				tearDown();
			}
			
			
			@Test
			public void testId() {
				init();
				assertEquals(airline_service.getRouteById(good_id).getId(), good_id);
				tearDown();
			}
			
					
			@Test
			public void isDeleted() {
				init();
				tearDown();
				assertEquals(route_repository.existsById(good_id), false);
			}
			@Test
			public void testNull() {
				 assertThrows(InvalidDataAccessApiUsageException.class,
				            ()->{
								 airline_service.getRouteById(null);
				            });
			}
			
			@Test
			public void testFalse() {
				assertEquals(null, airline_service.getRouteById(bad_id));

				
			}	
		}

	
}
