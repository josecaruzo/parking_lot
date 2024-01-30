package com.fiap_tech_challenge.parking_lot.dto.ParkingSpot;

import jakarta.validation.constraints.NotBlank;

public record ParkingSpotDTO(
	@NotBlank(message = "O código não pode ser vazio") // the id can't be blank
	String id,
	@NotBlank(message = "A rua não pode estar vazia") // the street can't be blank
	String street,
	@NotBlank(message = "O bairro não pode estar vazio") // the district can't be blank
	String district,
	@NotBlank(message = "A cidade não pode ser vazia") // The city can't be blank
	String city,
	@NotBlank(message = "O estado não pode estar vazio") // The state can't be blank
	String country_state,
	@NotBlank(message = "O status não pode ser vazio") // The status can't be blank
	Character status /* B - Busy (Ocupado) | F - Free (Livre))*/) {
}
