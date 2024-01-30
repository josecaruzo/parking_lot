package com.fiap_tech_challenge.parking_lot.dto.Purchase;

import com.fiap_tech_challenge.parking_lot.entity.Purchase;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

public record PurchaseDTO (
		Long id,

		@NotBlank(message = "O tipo não pode ser vazio") //The type can't be blank
		char type,
		LocalDateTime start_time,
		LocalDateTime end_time,
		LocalDateTime warning_time,
		int total_time,
		float total_amount,
		@NotBlank(message = "O meio de pagamento não pode ser vazio") //The payment method can't be blank
		String payment_method,
		Boolean status
){
	public static Purchase toEntity(PurchaseDTO pDTO) {
		return new Purchase(pDTO);
	}

	public static PurchaseDTO fromEntity(Purchase purchase) {
		return  new PurchaseDTO(
				purchase.getId(),
				purchase.getType(),
				purchase.getStart_time(),
				purchase.getEnd_time(),
				purchase.getWarning_time(),
				purchase.getTotal_time(),
				purchase.getTotal_amount(),
				purchase.getPayment_method(),
				purchase.getStatus()
		);
	}

	public static Purchase mapperDtoToEntity(PurchaseDTO dto, Purchase entity) {
		entity.setType(dto.type());
		entity.setStart_time(dto.start_time());
		entity.setEnd_time(dto.end_time());
		entity.setWarning_time(dto.warning_time());
		entity.setTotal_time(dto.total_time());
		entity.setTotal_amount(dto.total_amount());
		entity.setPayment_method(dto.payment_method());
		entity.setStatus(dto.status());
		return entity;
	}
}
