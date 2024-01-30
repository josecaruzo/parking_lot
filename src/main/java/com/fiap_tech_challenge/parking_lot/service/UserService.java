package com.fiap_tech_challenge.parking_lot.service;

import com.fiap_tech_challenge.parking_lot.controller.exception.ControllerNotFoundException;
import com.fiap_tech_challenge.parking_lot.dto.User.UserDTO;
import com.fiap_tech_challenge.parking_lot.entity.User;
import com.fiap_tech_challenge.parking_lot.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
	private final UserRepository userRepo;

	@Autowired
	public UserService(UserRepository userRepository){
		this.userRepo = userRepository;
	}

	public Collection<UserDTO> findAll(){
		var users = userRepo.findAll();
		return users.stream().map(UserDTO::fromEntity).collect(Collectors.toList());
	}

	public Optional<User> findByEmail(String email){
		return userRepo.findByEmail(email);
	}

	public UserDTO findById(Long id){
		if(id != 0) {
			User findUser = userRepo.findById(id).orElseThrow(() -> new ControllerNotFoundException("Usuário não encontrado")); //User not found
			return UserDTO.fromEntity(findUser);
		}
		return null;
	}

	public UserDTO save(UserDTO userDTO){
		User newUser = UserDTO.toEntity(userDTO);
		newUser = userRepo.save(newUser);
		return UserDTO.fromEntity(newUser);
	}

	public UserDTO update(Long id, UserDTO userDTO){
		try{
			User updateUser = userRepo.getReferenceById(id);

			updateUser.setName(userDTO.name());
			updateUser.setEmail(userDTO.email());
			updateUser.setPassword(userDTO.password());

			updateUser = userRepo.save(updateUser);
			return UserDTO.fromEntity(updateUser);
		}
		catch (EntityNotFoundException e){
			throw new ControllerNotFoundException("Usuário não encontrado"); //User not found
		}
	}

	public void delete(Long id){
		userRepo.deleteById(id);
	}

}
