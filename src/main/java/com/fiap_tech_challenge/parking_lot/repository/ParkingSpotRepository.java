package com.fiap_tech_challenge.parking_lot.repository;

import com.fiap_tech_challenge.parking_lot.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, String> {
}
