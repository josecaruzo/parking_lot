package com.fiap_tech_challenge.parking_lot.entity;

import com.fiap_tech_challenge.parking_lot.dto.Car.CarDTO;
import com.fiap_tech_challenge.parking_lot.dto.Car.CarUserDTO;
import jakarta.persistence.*;

import java.time.Year;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 7, nullable = false)
	private String plate;

	@Column(length = 20, nullable = false)
	private String brand;

	@Column(length = 20, nullable = false)
	private String model;

	@Column(length = 20, nullable = false)
	private String color;

	@Column(length = 4, nullable = false)
	private Year year;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "car")
	private Set<Purchase> receipts = new HashSet<>();

	public Car(){}

	public Car(Long id, String plate, String brand, String model, String color, Year year){
		this.id = id;
		this.plate = plate;
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.year = year;
	}

	public Car(CarDTO carDTO){
		this.id = carDTO.id();
		this.plate = carDTO.plate();
		this.brand = carDTO.brand();
		this.model = carDTO.model();
		this.color = carDTO.color();
		this.year = carDTO.year();
	}
	public Car(CarUserDTO carDTO, User user){
		this.id = carDTO.id();
		this.plate = carDTO.plate();
		this.brand = carDTO.brand();
		this.model = carDTO.model();
		this.color = carDTO.color();
		this.year = carDTO.year();
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	//@JsonBackReference
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Car{" +
				"id=" + id +
				", plate='" + plate + '\'' +
				", brand='" + brand + '\'' +
				", model='" + model + '\'' +
				", color='" + color + '\'' +
				", year=" + year +
				", user=" + user +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Car car = (Car) o;
		return Objects.equals(id, car.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
