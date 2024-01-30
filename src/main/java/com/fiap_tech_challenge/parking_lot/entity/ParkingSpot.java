package com.fiap_tech_challenge.parking_lot.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "parking_spots")
public class ParkingSpot {
	@Id
	@Column(length = 12, nullable = false, unique = true)
	private String id;
	@Column(length = 100, nullable = false)
	private String street;

	@Column(length = 50, nullable = false)
	private String district;

	@Column(length = 50, nullable = false)
	private String city;

	@Column(length = 20, nullable = false)
	private String country_state;

	@Column(length = 1, nullable = false)
	private Character status; // B - Busy (Ocupado) | F - Free (Livre)

	public ParkingSpot(){}
	public ParkingSpot(String id, String street, String district, String city, String country_state, Character status) {
		this.id = id;
		this.street = street;
		this.district = district;
		this.city = city;
		this.country_state = country_state;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry_state() {
		return country_state;
	}

	public void setCountry_state(String country_state) {
		this.country_state = country_state;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ParkingSpot{" +
				"id='" + id + '\'' +
				", street='" + street + '\'' +
				", district='" + district + '\'' +
				", city='" + city + '\'' +
				", country_state='" + country_state + '\'' +
				", status=" + status +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ParkingSpot that = (ParkingSpot) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
