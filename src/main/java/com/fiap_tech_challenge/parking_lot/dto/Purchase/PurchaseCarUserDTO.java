package com.fiap_tech_challenge.parking_lot.dto.Purchase;

import com.fiap_tech_challenge.parking_lot.entity.Car;
import com.fiap_tech_challenge.parking_lot.entity.Purchase;
import com.fiap_tech_challenge.parking_lot.entity.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

public record PurchaseCarUserDTO(
		Long id,
		char type,

		LocalDateTime start_time,

		LocalDateTime end_time,

		LocalDateTime warning_time,

		int total_time,

		float total_amount,

		String payment_method,

		Boolean status,
		Long car_id,
		Long user_id
) {
	public static Purchase toEntity(PurchaseCarUserDTO pDTO, Car car, User user) {
		return new Purchase(pDTO, car, user);
	}
	public static PurchaseCarUserDTO fromEntity(Purchase purchase) {
		return  new PurchaseCarUserDTO(
				purchase.getId(),
				purchase.getType(),
				purchase.getStart_time(),
				purchase.getEnd_time(),
				purchase.getWarning_time(),
				purchase.getTotal_time(),
				purchase.getTotal_amount(),
				purchase.getPayment_method(),
				purchase.getStatus(),
				purchase.getCar() != null ? purchase.getCar().getId() : null,
				purchase.getUser() != null ? purchase.getUser().getId() : null
		);
	}

	public static Purchase mapperDtoToEntity(PurchaseCarUserDTO dto, Purchase entity, Car car , User user) {
		entity.setType(dto.type());
		entity.setStart_time(dto.start_time());
		entity.setEnd_time(dto.end_time());
		entity.setWarning_time(dto.warning_time());
		entity.setTotal_time(dto.total_time());
		entity.setTotal_amount(dto.total_amount());
		entity.setPayment_method(dto.payment_method());
		entity.setStatus(dto.status());
		entity.setCar(car);
		entity.setUser(user);
		return entity;
	}

}
