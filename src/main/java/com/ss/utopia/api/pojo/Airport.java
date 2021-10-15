package com.ss.utopia.api.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

	public void setIataId(String iataId) {
		this.iataId = iataId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

//	@OneToMany(targetEntity=Route.class, cascade = CascadeType.ALL)
//	@JoinColumn(name="origin_id")
	@OneToMany(mappedBy = "airport", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Route> as_origin;

	@OneToMany(mappedBy = "airport", cascade = CascadeType.ALL, orphanRemoval = true)
	//@JoinColumn(name = "destination_id")
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