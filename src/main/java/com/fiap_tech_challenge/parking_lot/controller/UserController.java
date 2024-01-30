package com.fiap_tech_challenge.parking_lot.controller;

import com.fiap_tech_challenge.parking_lot.dto.User.UserDTO;
import com.fiap_tech_challenge.parking_lot.entity.User;
import com.fiap_tech_challenge.parking_lot.service.PurchaseService;
import com.fiap_tech_challenge.parking_lot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
	private final UserService uService;
	private final PurchaseService pService;

	@Autowired
	public UserController(UserService userService, PurchaseService purchaseService){
		this.uService = userService;
		this.pService = purchaseService;
	}

	@GetMapping("")
	public ResponseEntity<Collection<UserDTO>> findAll(){
		Collection<UserDTO> usersDTO = uService.findAll();
		return ResponseEntity.ok(usersDTO);
	}


	@GetMapping("/new")
	public String showRegisterPage(Model model){
		model.addAttribute("user", new User());
		return "newUser";
	}

	@GetMapping("/{id}")
	public String showUserEditPage(@PathVariable Long id, Model model){
		UserDTO userDTO = uService.findById(id);
		model.addAttribute("user", userDTO);
		return "editUser";
	}

	@PostMapping("/save")
	public String saveUser(@Valid @ModelAttribute("user") UserDTO user, BindingResult result, Model model,  RedirectAttributes ra ){
		Optional<User> userFind = uService.findByEmail(user.email());
		if(userFind.isEmpty()){
			UserDTO newUser = uService.save(user);
			ra.addFlashAttribute("user_id", newUser.id());
			return "redirect:/";
		}

		result.rejectValue("email", null, "Email já cadastrado!");
		model.addAttribute("user", user);
		return "newUser";
	}

	@PostMapping("/save/{id}")
	public String editUser(@PathVariable Long id, @ModelAttribute("user") UserDTO user, Model model,  RedirectAttributes ra ){
		var userFind = uService.findById(id);
		if(userFind.id() != null){
			user = uService.save(user);
			ra.addFlashAttribute("user_id", user.id());
			var purchase = pService.findActiveByUser(user.id());
			if(purchase != null) {
				ra.addFlashAttribute("purchase_id", purchase.id());
			}
			return "redirect:/";
		}

		model.addAttribute("user", user);
		return "editUser";
	}


	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@Valid @PathVariable Long id, @RequestBody UserDTO userDTO){
		UserDTO updateUser = uService.update(id, userDTO);
		return ResponseEntity.ok(updateUser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		uService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
