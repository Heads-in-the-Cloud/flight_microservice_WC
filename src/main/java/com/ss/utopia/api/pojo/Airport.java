package com.ss.utopia.api.pojo;

import java.util.List;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonFilter;


@Entity
@Table(name = "airport")
public class Airport {

	@Id
	private String iataId;

	private String city;

	@Override
	public String toString() {
		return "Airport [iataId=" + iataId + ", city=" + city + "]";
	}

	public String getIataId() {
		return iataId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(iataId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airport other = (Airport) obj;
		return Objects.equals(iataId, other.iataId);
	}

	public void setIataId(String iataId) {
		this.iataId = iataId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public void addOrigin(Route route) {
		
		if(!this.as_origin.contains(route)) {
			this.as_origin.add(route);
		}
		else if(route.getFlights() != null){
			Integer index = as_origin.indexOf(route);
			
			route.getFlights().forEach(x -> x.setRoute_id(as_origin.get(index).getId()));
		
			as_origin.set( index, route);
		}
		
	}
	
	public void addDestination(Route route) {
		if(!this.as_destination.contains(route)) {
			this.as_destination.add(route);
		}
		else if(route.getFlights() != null){
			Integer index = as_destination.indexOf(route);
			
			route.getFlights().forEach(x -> x.setRoute_id(as_destination.get(index).getId()));
			as_destination.set(index, route);
			
		}
	}
	
	
	
	@OneToMany(targetEntity=Route.class, cascade = CascadeType.MERGE)
	@JoinColumn(name="origin_id", nullable = true)
	private List<Route> as_origin;

	@OneToMany(targetEntity=Route.class, cascade = CascadeType.MERGE)
	@JoinColumn(name = "destination_id", nullable=true)
	private List<Route> as_destination;

	public List<Route> getAs_origin() {
		return as_origin;
	}

	public void setAs_origin(List<Route> as_origin) {
		this.as_origin = as_origin;
	}

	public List<Route> getAs_destination() {
		return as_destination;
	}

	public void setAs_destination(List<Route> as_destination) {
		this.as_destination = as_destination;
	}

}

