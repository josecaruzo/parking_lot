package com.fiap_tech_challenge.parking_lot.repository;

import com.fiap_tech_challenge.parking_lot.entity.Purchase;
import com.fiap_tech_challenge.parking_lot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	List<Purchase> findByUser(User user);

	Purchase findByUserAndStatus(User user, Boolean active);
}
