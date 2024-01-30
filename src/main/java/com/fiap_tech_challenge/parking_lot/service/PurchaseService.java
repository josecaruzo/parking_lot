package com.fiap_tech_challenge.parking_lot.service;

import com.fiap_tech_challenge.parking_lot.controller.exception.ControllerNotFoundException;
import com.fiap_tech_challenge.parking_lot.controller.exception.DatabaseException;
import com.fiap_tech_challenge.parking_lot.dto.Purchase.PurchaseCarUserDTO;
import com.fiap_tech_challenge.parking_lot.dto.Purchase.PurchaseDTO;
import com.fiap_tech_challenge.parking_lot.entity.Car;
import com.fiap_tech_challenge.parking_lot.entity.Purchase;
import com.fiap_tech_challenge.parking_lot.entity.User;
import com.fiap_tech_challenge.parking_lot.repository.CarRepository;
import com.fiap_tech_challenge.parking_lot.repository.PurchaseRepository;
import com.fiap_tech_challenge.parking_lot.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseService {
	private final PurchaseRepository purchaseRepo;
	private final CarRepository carRepo;
	private final UserRepository userRepo;

	@Autowired
	public PurchaseService(PurchaseRepository purchaseRepository, CarRepository carRepository, UserRepository userRepository){
		this.purchaseRepo = purchaseRepository;
		this.carRepo = carRepository;
		this.userRepo = userRepository;
	}

	public Collection<PurchaseCarUserDTO> findAll(){
		var purchases = purchaseRepo.findAll();
		return purchases.stream().map(PurchaseCarUserDTO::fromEntity).collect(Collectors.toList());
	}

	public PurchaseCarUserDTO findById(Long id){
		Purchase findPurchase = purchaseRepo.findById(id).orElseThrow(() -> new ControllerNotFoundException("Registro de compra não encontrado")); //Purchase receipt not found
		return PurchaseCarUserDTO.fromEntity(findPurchase);
	}

	public PurchaseCarUserDTO save(PurchaseCarUserDTO dto){
		try{
			var car = carRepo.getReferenceById(dto.car_id());
			var user = userRepo.getReferenceById(dto.user_id());

			var entity = PurchaseCarUserDTO.toEntity(dto, car, user);
			var purchase = purchaseRepo.save(entity);

			return PurchaseCarUserDTO.fromEntity(purchase);
		}catch ( DataIntegrityViolationException e) {
			throw new DatabaseException("Carro ou Usuário não encontrados"); //Car or User not found
		}
	}

	public PurchaseDTO update(Long id, PurchaseDTO dto){
		try{
			Purchase updatePurchase = purchaseRepo.getReferenceById(id);

			updatePurchase.setType(dto.type());
			updatePurchase.setStart_time(dto.start_time());
			updatePurchase.setEnd_time(dto.end_time());
			updatePurchase.setWarning_time(dto.warning_time());
			updatePurchase.setTotal_time(dto.total_time());
			updatePurchase.setTotal_amount(dto.total_amount());
			updatePurchase.setPayment_method(dto.payment_method());

			updatePurchase = purchaseRepo.save(updatePurchase);

			return PurchaseDTO.fromEntity(updatePurchase);
		}
		catch (EntityNotFoundException e){
			throw new ControllerNotFoundException("O registro de compra não foi encontrado"); //Car not found
		}
	}

	public void delete(Long id){
		purchaseRepo.deleteById(id);
	}

	public List<Purchase> findAllByUser(Long user_id) {
		Optional<User> user = userRepo.findById(user_id);
		if(user.isPresent()) {
			return purchaseRepo.findByUser(user.get());
		}

		return null;
	}

	public PurchaseCarUserDTO findActiveByUser(Long user_id) {
		Optional<User> user = userRepo.findById(user_id);
		if(user.isPresent()) {
			System.out.println("User: " + user.get());
			Purchase purchase = purchaseRepo.findByUserAndStatus(user.get(), Boolean.TRUE);
			if(purchase != null){
				return PurchaseCarUserDTO.fromEntity(purchase);
			}
			return null;
		}
		return null;
	}
}
