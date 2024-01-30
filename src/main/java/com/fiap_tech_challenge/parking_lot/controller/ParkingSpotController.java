package com.fiap_tech_challenge.parking_lot.controller;

import com.fiap_tech_challenge.parking_lot.dto.ParkingSpot.ParkingSpotDTO;
import com.fiap_tech_challenge.parking_lot.service.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spot")
public class ParkingSpotController {
	private final ParkingSpotService spotService;

	@Autowired
	public ParkingSpotController(ParkingSpotService pSpotService){
		this.spotService = pSpotService;
	}

	@GetMapping
	public ResponseEntity<Page<ParkingSpotDTO>> findAll(@PageableDefault(size = 999, page = 0, sort = "id") Pageable pageable){
		Page<ParkingSpotDTO> spotDTO = spotService.findAll(pageable);
		return ResponseEntity.ok(spotDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ParkingSpotDTO> findByID(@PathVariable String id){
		ParkingSpotDTO spotDTO = spotService.findById(id);
		return ResponseEntity.ok(spotDTO);
	}
}
