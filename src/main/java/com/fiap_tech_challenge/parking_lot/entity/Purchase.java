package com.fiap_tech_challenge.parking_lot.entity;

import com.fiap_tech_challenge.parking_lot.dto.Purchase.PurchaseCarUserDTO;
import com.fiap_tech_challenge.parking_lot.dto.Purchase.PurchaseDTO;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "purchase_receipts")
public class Purchase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 1, nullable = false)
	private char type; // S - Static | F - Flexible

	@Column(nullable = false)
	@DateTimeFormat(pattern="dd/MM/YYYY HH:mm")
	private LocalDateTime start_time;

	@Column
	@DateTimeFormat(pattern="dd/MM/YYYY HH:mm")
	private LocalDateTime end_time;

	@Column
	@DateTimeFormat(pattern="dd/MM/YYYY HH:mm")
	private LocalDateTime warning_time;

	@Column
	private int total_time; //In hours

	@Column
	private float total_amount;

	@Column(length = 30, nullable = false)
	private String payment_method;

	@Column(nullable = false)
	private Boolean status;

	@ManyToOne
	@JoinColumn(name = "car_id", nullable = false)
	private Car car;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	/*@ManyToOne
	@JoinColumn(name = "spot_id", nullable = false)
	private ParkingSpot spot;*/


	public Purchase(){}

	public Purchase(
			Long id,
			char type,
			LocalDateTime start_time,
			LocalDateTime end_time,
			LocalDateTime warning_time,
			int total_time,
			float total_amount,
			String payment_method,
			Boolean status){
		this.id = id;
		this.type = type;
		this.start_time = start_time;
		this.end_time = end_time;
		this.warning_time = warning_time;
		this.total_time = total_time;
		this.total_amount = total_amount;
		this.payment_method = payment_method;
		this.status = status;
	}

	public Purchase(PurchaseDTO pDTO){
		this.id = pDTO.id();
		this.type = pDTO.type();
		this.start_time = pDTO.start_time();
		this.end_time = pDTO.end_time();
		this.warning_time = pDTO.warning_time();
		this.total_time = pDTO.total_time();
		this.total_amount = pDTO.total_amount();
		this.payment_method = pDTO.payment_method();
		this.status = pDTO.status();
	}

	public Purchase(PurchaseCarUserDTO pDTO, Car car, User user){
		this.id = pDTO.id();
		this.type = pDTO.type();
		this.start_time = pDTO.start_time();
		this.end_time = pDTO.end_time();
		this.warning_time = pDTO.warning_time();
		this.total_time = pDTO.total_time();
		this.total_amount = pDTO.total_amount();
		this.payment_method = pDTO.payment_method();
		this.status = pDTO.status();
		this.car = car;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public LocalDateTime getStart_time() {
		return start_time;
	}

	public void setStart_time(LocalDateTime start_time) {
		this.start_time = start_time;
	}

	public LocalDateTime getEnd_time() {
		return end_time;
	}

	public void setEnd_time(LocalDateTime end_time) {
		this.end_time = end_time;
	}

	public LocalDateTime getWarning_time() {
		return warning_time;
	}

	public void setWarning_time(LocalDateTime warning_time) {
		this.warning_time = warning_time;
	}

	public int getTotal_time() {
		return total_time;
	}

	public void setTotal_time(int total_time) {
		this.total_time = total_time;
	}

	public float getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(float total_amount) {
		this.total_amount = total_amount;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public String toString() {
		return "PurchaseReceipt{" +
				"id=" + id +
				", type=" + type +
				", start_time=" + start_time +
				", end_time=" + end_time +
				", warning_time=" + warning_time +
				", total_time=" + total_time +
				", total_amount=" + total_amount +
				", payment_method='" + payment_method + '\'' +
				", user=" + user +
				", car=" + car +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Purchase that = (Purchase) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
