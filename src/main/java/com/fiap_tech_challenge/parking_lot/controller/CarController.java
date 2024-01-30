package com.fiap_tech_challenge.parking_lot.controller;

import com.fiap_tech_challenge.parking_lot.controller.exception.ControllerNotFoundException;
import com.fiap_tech_challenge.parking_lot.dto.Car.CarDTO;
import com.fiap_tech_challenge.parking_lot.dto.Car.CarUserDTO;
import com.fiap_tech_challenge.parking_lot.dto.User.UserDTO;
import com.fiap_tech_challenge.parking_lot.entity.Car;
import com.fiap_tech_challenge.parking_lot.service.CarService;
import com.fiap_tech_challenge.parking_lot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {

	private final CarService cService;
	private final UserService uService;

	@Autowired
	private CarController(CarService carService, UserService userService){
		this.cService = carService;
		this.uService = userService;
	}

	@GetMapping("/{user_id}")
	public String showCarPage(@PathVariable Long user_id, Model model){
		List<Car> cars = cService.findAllByUser(user_id);
		model.addAttribute("user_id", user_id);
		model.addAttribute("carList", cars);
		return "cars";
	}
	@GetMapping("/new/{user_id}")
	public String showNewCarPage(@PathVariable Long user_id, Model model){
		model.addAttribute("user_id", user_id);
		model.addAttribute("car", new Car());
		return "newCar";
	}

	@GetMapping("/edit/{id}")
	public String showEditCarPage(@PathVariable Long id, Model model){
		var car = cService.findById(id);

		model.addAttribute("user_id", car.user_id());
		model.addAttribute("car", car);
		return "editCar";
	}

	@PostMapping("/save/{user_id}")
	public String saveCar(@PathVariable Long user_id, @Valid Car newCar, Model model, RedirectAttributes ra ){
		var user = uService.findById(user_id);
		if(user.id() != null){
			newCar.setUser(UserDTO.toEntity(user));
			var car = cService.save(CarUserDTO.fromEntity(newCar));
			ra.addFlashAttribute("user_id", car.user_id());
			return "redirect:/car/" + car.user_id();
		}

		return "cars";
	}

	@PostMapping("/save")
	public String saveCar(@Valid @ModelAttribute("car") CarUserDTO editCar, BindingResult result, Model model, RedirectAttributes ra ){
		var car = cService.findById(editCar.id());

		if(car.id() != null){
			editCar = cService.save(editCar);
			ra.addFlashAttribute("user_id", editCar.user_id());
			return "redirect:/car/" + editCar.user_id();
		}

		result.rejectValue("user", null, "Usuário inexistente!");
		model.addAttribute("car", car);
		return "editCar";
	}

	@GetMapping("/delete/{id}")
	public String deleteCar(@PathVariable Long id, RedirectAttributes ra) {
		var car = cService.findById(id);
		try {
			cService.delete(car.id());
			ra.addFlashAttribute("message", "O carro foi deletado com sucesso!");
		} catch (ControllerNotFoundException e) {
			ra.addFlashAttribute("message", "Erro ao tentar econtrar o carro!");
		}

		ra.addFlashAttribute("user_id", car.user_id());
		return "redirect:/car/" + car.user_id();
	}
	@PutMapping("/{id}")
	public ResponseEntity<CarDTO> update(@Valid @PathVariable Long id, @RequestBody CarDTO dto){
		var updateCar = cService.update(id, dto);
		return ResponseEntity.ok(updateCar);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		cService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
