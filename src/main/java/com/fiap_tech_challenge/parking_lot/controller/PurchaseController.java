package com.fiap_tech_challenge.parking_lot.controller;

import com.fiap_tech_challenge.parking_lot.dto.Car.CarUserDTO;
import com.fiap_tech_challenge.parking_lot.dto.ParkingSpot.ParkingSpotDTO;
import com.fiap_tech_challenge.parking_lot.dto.Purchase.PurchaseCarUserDTO;
import com.fiap_tech_challenge.parking_lot.dto.User.UserDTO;
import com.fiap_tech_challenge.parking_lot.entity.Car;
import com.fiap_tech_challenge.parking_lot.entity.ParkingSpot;
import com.fiap_tech_challenge.parking_lot.entity.Purchase;
import com.fiap_tech_challenge.parking_lot.service.CarService;
import com.fiap_tech_challenge.parking_lot.service.ParkingSpotService;
import com.fiap_tech_challenge.parking_lot.service.PurchaseService;
import com.fiap_tech_challenge.parking_lot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

	private final PurchaseService pService;
	private final CarService carService;
	private final UserService userService;

	private final ParkingSpotService spotService;

	@Autowired
	private PurchaseController(PurchaseService purchaseService, CarService carService, UserService userService, ParkingSpotService spotService){
		this.pService = purchaseService;
		this.carService = carService;
		this.userService = userService;
		this.spotService = spotService;
	}

	@GetMapping("/new/{user_id}")
	public String showNewPurchasePage(@PathVariable Long user_id, Model model,  RedirectAttributes ra){
		var purchase = new Purchase();
		List<Car> cars = carService.findAllByUser(user_id);
		//Page<ParkingSpotDTO> spots = spotService.findAll(Pageable.ofSize(999));

		if(cars.isEmpty()){
			ra.addFlashAttribute("user_id", user_id);
			ra.addFlashAttribute("message", "O usuário não possui carros!");
			return "redirect:/";
		}
		model.addAttribute("user_id", user_id);
		model.addAttribute("carList", cars);
		model.addAttribute("purchase", PurchaseCarUserDTO.fromEntity(purchase));
		return "newPurchase";
	}

	@GetMapping("/{user_id}")
	public String showPurchasePage(@PathVariable Long user_id, Model model){
		List<Purchase> purchases = pService.findAllByUser(user_id);
		model.addAttribute("user_id", user_id);
		model.addAttribute("purchaseList", purchases);
		return "purchases";
	}

	@PostMapping("/save/{user_id}")
	public String savePurchase(@PathVariable Long user_id, @Valid PurchaseCarUserDTO newPurchase, BindingResult result, Model model, RedirectAttributes ra ){
		var user = UserDTO.toEntity(userService.findById(user_id));
		if(user.getId() != null) {
			var car = CarUserDTO.toEntity(carService.findById(newPurchase.car_id()), user);
			Purchase purchase = new Purchase();
			PurchaseCarUserDTO.mapperDtoToEntity(newPurchase, purchase, car, user);

			LocalDateTime time = LocalDateTime.now();
			purchase.setStart_time(time);

			if(purchase.getTotal_time() > 0) {
				time = time.plusHours(purchase.getTotal_time());
				time = time.minusMinutes(10); //10 minutes to warning the client
				purchase.setWarning_time(time);
			}

			purchase.setStatus(Boolean.TRUE); // Se verdadeiro significa que é um ticket em aberto

			newPurchase = pService.save(PurchaseCarUserDTO.fromEntity(purchase));
			ra.addFlashAttribute("user_id", newPurchase.user_id());
			ra.addFlashAttribute("purchase_id", newPurchase.id());
			return "redirect:/";
		}

		result.rejectValue("user", null, "Usuário inexistente!");
		model.addAttribute("purchase", newPurchase);
		return "newPurchase";
	}

	@PostMapping("/save")
	public String editPurchase(@Valid PurchaseCarUserDTO editPurchase, Model model, RedirectAttributes ra ){
		var purchase = pService.save(editPurchase);
		if(purchase.id() != null){
			ra.addFlashAttribute("user_id", purchase.user_id());
			return "redirect:/";
		}

		model.addAttribute("param_error", "Erro ao tentar salvar");
		model.addAttribute("purchase", editPurchase);
		return "editPurchase";
	}
	@GetMapping("/edit/{id}")
	public String showEditPurchasePage(@PathVariable Long id, Model model){

		var purchase = pService.findById(id);
		var user = UserDTO.toEntity(userService.findById(purchase.user_id()));
		var car = CarUserDTO.toEntity(carService.findById(purchase.car_id()), user);

		Purchase editPurchase = PurchaseCarUserDTO.toEntity(purchase, car, user);
		editPurchase.setEnd_time(LocalDateTime.now());

		var duration = Duration.between(editPurchase.getStart_time(), editPurchase.getEnd_time());
		editPurchase.setTotal_time((int) Math.ceil((double) duration.toMinutes() / 60));
		editPurchase.setTotal_amount(editPurchase.getTotal_time() * 10); // 10 reais por hora
		editPurchase.setStatus(Boolean.FALSE);

		model.addAttribute("user_id", purchase.user_id());
		model.addAttribute("car_plate", car.getPlate());
		model.addAttribute("purchase", PurchaseCarUserDTO.fromEntity(editPurchase));
		return "editPurchase";
	}

}
