package com.fiap_tech_challenge.parking_lot.dto.Car;

import com.fiap_tech_challenge.parking_lot.entity.Car;
import com.fiap_tech_challenge.parking_lot.entity.User;
import jakarta.validation.constraints.NotBlank;

import java.time.Year;

public record CarUserDTO(
		Long id,
		@NotBlank(message = "A placa não pode ser vazia") //The plate can't be blank
		String plate,
		@NotBlank(message = "A marca não pode estar vazio") //Brand can't be blank
		String brand,
		@NotBlank(message = "O modelo não pode estar vazio") //Model can't be blank
		String model,
		@NotBlank(message = "A cor não pode estar vazia") //Color can't be blank
		String color,
		Year year,
		Long user_id) {
	public static Car toEntity(CarUserDTO carDTO, User user) {
		return new Car(carDTO, user);
	}

	public static CarUserDTO fromEntity(Car car) {
		return  new CarUserDTO(
				car.getId(),
				car.getPlate(),
				car.getBrand(),
				car.getModel(),
				car.getColor(),
				car.getYear(),
				car.getUser() != null ? car.getUser().getId() : null
		);
	}

	public static Car mapperDtoToEntity(CarUserDTO dto, Car entity, User user) {
		entity.setPlate(dto.plate());
		entity.setBrand(dto.brand());
		entity.setModel(dto.model());
		entity.setColor(dto.model());
		entity.setYear(dto.year());
		entity.setUser(user);
		return entity;
	}
}
