package com.fiap_tech_challenge.parking_lot.dto.Car;

import com.fiap_tech_challenge.parking_lot.entity.Car;
import jakarta.validation.constraints.NotBlank;

import java.time.Year;

public record CarDTO(
		Long id,
		@NotBlank(message = "A placa não pode ser vazia") //The plate can't be blank
		String plate,
		@NotBlank(message = "A marca não pode estar vazio") //Brand can't be blank
		String brand,
		@NotBlank(message = "O modelo não pode estar vazio") //Model can't be blank
		String model,
		@NotBlank(message = "A cor não pode estar vazia") //Color can't be blank
		String color,
		Year year
) {
	public static Car toEntity(CarDTO carDTO) {
		return new Car(carDTO);
	}

	public static CarDTO fromEntity(Car car) {
		return  new CarDTO(
				car.getId(),
				car.getPlate(),
				car.getBrand(),
				car.getModel(),
				car.getColor(),
				car.getYear()
		);
	}

	public static Car mapperDtoToEntity(CarDTO dto, Car entity) {
		entity.setPlate(dto.plate());
		entity.setBrand(dto.brand());
		entity.setModel(dto.model());
		entity.setColor(dto.color());
		entity.setYear(dto.year());
		return entity;
	}
}
