package com.fiap_tech_challenge.parking_lot.service;

import com.fiap_tech_challenge.parking_lot.controller.exception.ControllerNotFoundException;
import com.fiap_tech_challenge.parking_lot.dto.ParkingSpot.ParkingSpotDTO;
import com.fiap_tech_challenge.parking_lot.entity.ParkingSpot;
import com.fiap_tech_challenge.parking_lot.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpotService {
	/* EN: Parking spots should be migrated or added to the application as static entities, so has no need to create / alter / delete them */
	/* PT: Os espaços de estacionamento deveriam ter sido migrados ou adicionados a aplicação como dados estáticos (Via database),
		não havendo necessidade de criar / alterar / deletar os mesmos
	*/
	private final ParkingSpotRepository pSpotRepo;

	@Autowired
	public ParkingSpotService(ParkingSpotRepository pSpotRepository){
		this.pSpotRepo = pSpotRepository;
	}

	public Page<ParkingSpotDTO> findAll(Pageable numPage){
		Page<ParkingSpot>spots = pSpotRepo.findAll(numPage);
		return spots.map(this::toDTO);
	}

	public ParkingSpotDTO findById(String id){
		ParkingSpot findSpot = pSpotRepo.findById(id).orElseThrow(() -> new ControllerNotFoundException("Vaga não encontrada")); //Spot not found

		if(findSpot.getStatus().compareTo('B') == 0){ //Busy
			throw new ControllerNotFoundException("Vaga ocupada"); //Spot not valid
		}
		return toDTO(findSpot);
	}
	private ParkingSpotDTO toDTO(ParkingSpot pSpot){
		return new ParkingSpotDTO(
				pSpot.getId(),
				pSpot.getStreet(),
				pSpot.getDistrict(),
				pSpot.getCity(),
				pSpot.getCountry_state(),
				pSpot.getStatus()
		);
	}
}
