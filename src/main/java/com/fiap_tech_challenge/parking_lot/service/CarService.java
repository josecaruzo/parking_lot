package com.fiap_tech_challenge.parking_lot.service;

import com.fiap_tech_challenge.parking_lot.controller.exception.ControllerNotFoundException;
import com.fiap_tech_challenge.parking_lot.controller.exception.DatabaseException;
import com.fiap_tech_challenge.parking_lot.dto.Car.CarDTO;
import com.fiap_tech_challenge.parking_lot.dto.Car.CarUserDTO;
import com.fiap_tech_challenge.parking_lot.entity.Car;
import com.fiap_tech_challenge.parking_lot.entity.User;
import com.fiap_tech_challenge.parking_lot.repository.CarRepository;
import com.fiap_tech_challenge.parking_lot.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CarService {
	private final CarRepository carRepo;
	private final UserRepository userRepo;

	@Autowired
	public CarService(CarRepository carRepository, UserRepository userRepository){
		this.carRepo = carRepository;
		this.userRepo = userRepository;
	}

	public Collection<CarUserDTO> findAll(){
		var cars = carRepo.findAll();
		return cars.stream().map(CarUserDTO::fromEntity).collect(Collectors.toList());
	}

	public CarUserDTO findById(Long id){
		Car findCar = carRepo.findById(id).orElseThrow(() -> new ControllerNotFoundException("Carro não encontrado")); //User not found
		return CarUserDTO.fromEntity(findCar);
	}

	public CarUserDTO save(CarUserDTO dto){
		try{
			var user = userRepo.getReferenceById(dto.user_id());
			var entity = CarUserDTO.toEntity(dto, user);
			var newCar = carRepo.save(entity);

			return CarUserDTO.fromEntity(newCar);
		}catch ( DataIntegrityViolationException e) {
			throw new DatabaseException("Usuário não encontrado"); // User not found
		}
	}

	public CarDTO update(Long id, CarDTO dto){
		try{
			Car updateCar = carRepo.getReferenceById(id);

			updateCar.setPlate(dto.plate());
			updateCar.setBrand(dto.brand());
			updateCar.setModel(dto.model());
			updateCar.setColor(dto.model());
			updateCar.setYear(dto.year());

			updateCar = carRepo.save(updateCar);

			return CarDTO.fromEntity(updateCar);
		}
		catch (EntityNotFoundException e){
			throw new ControllerNotFoundException("Carro não foi encontrado"); //Car not found
		}
	}
	public void delete(Long id){
		carRepo.deleteById(id);
	}

	public List<Car> findAllByUser(Long user_id) {
		Optional<User> user = userRepo.findById(user_id);
		if(user.isPresent()) {
			return carRepo.findByUser(user.get());
		}

		return null;
	}
}
