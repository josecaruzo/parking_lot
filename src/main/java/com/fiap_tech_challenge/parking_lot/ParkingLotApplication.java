package com.fiap_tech_challenge.parking_lot;

import com.fiap_tech_challenge.parking_lot.entity.ParkingSpot;
import com.fiap_tech_challenge.parking_lot.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ParkingLotApplication {

	public static void main(String[] args) {SpringApplication.run(ParkingLotApplication.class, args);}

}
