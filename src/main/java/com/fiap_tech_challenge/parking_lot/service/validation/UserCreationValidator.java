package com.fiap_tech_challenge.parking_lot.service.validation;

import com.fiap_tech_challenge.parking_lot.dto.User.UserDTO;
import com.fiap_tech_challenge.parking_lot.entity.User;
import com.fiap_tech_challenge.parking_lot.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserCreationValidator implements ConstraintValidator<ValidUserCreation, UserDTO> {
	@Autowired
	private UserRepository userRepo;

	@Override
	public void initialize(ValidUserCreation annotation){}

	@Override
	public boolean isValid(UserDTO userDto, ConstraintValidatorContext context){
		Optional<User> user = userRepo.findByEmail(userDto.email());
		return user.isEmpty();
	}
}