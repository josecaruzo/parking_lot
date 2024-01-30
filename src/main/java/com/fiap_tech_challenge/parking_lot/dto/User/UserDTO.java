package com.fiap_tech_challenge.parking_lot.dto.User;

import com.fiap_tech_challenge.parking_lot.entity.User;
import com.fiap_tech_challenge.parking_lot.service.validation.ValidUserCreation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@ValidUserCreation(message = "Existe usuário com este e-mail")
public record UserDTO(
		Long id,
		@NotBlank(message = "O nome não pode estar vazio") //Name can't be blank
		String name,
		@Email(message = "E-mail inválido") // Invalid email
		String email,
		String password
) {
	public static User toEntity(UserDTO userDTO) {
		return new User(userDTO);
	}

	public static UserDTO fromEntity(User user) {
		return  new UserDTO(
				user.getId(),
				user.getName(),
				user.getEmail(),
				user.getPassword()
		);
	}

	public static User mapperDtoToEntity(UserDTO dto, User entity) {
		entity.setName(dto.name());
		entity.setEmail(dto.email());
		entity.setPassword(dto.password());
		return entity;
	}
}
