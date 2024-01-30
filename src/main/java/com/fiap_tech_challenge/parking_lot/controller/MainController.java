package com.fiap_tech_challenge.parking_lot.controller;

import com.fiap_tech_challenge.parking_lot.dto.User.UserDTO;
import com.fiap_tech_challenge.parking_lot.entity.User;
import com.fiap_tech_challenge.parking_lot.service.PurchaseService;
import com.fiap_tech_challenge.parking_lot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.webjars.WebJarAssetLocator;


import java.util.Objects;
import java.util.Optional;

@Controller
public class MainController {
	private final UserService userService;
	private final PurchaseService purchaseService;

	@Autowired
	private MainController(UserService userService, PurchaseService purchaseService){
		this.userService = userService;
		this.purchaseService = purchaseService;
	}

	@GetMapping("/login")
	public String showLoginPage(Model model){
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("user") UserDTO user, Model model, RedirectAttributes ra){
			Optional<User> userFind = userService.findByEmail(user.email());
			if(userFind.isPresent() && Objects.equals(userFind.get().getPassword(), user.password())){
				ra.addFlashAttribute("user_id", userFind.get().getId());
				var purchase = purchaseService.findActiveByUser(userFind.get().getId());
				if(purchase != null) {
					ra.addFlashAttribute("purchase_id", purchase.id());
				}
				return "redirect:/";
			}
			model.addAttribute("param.error", "Email ou Senha inválidos!");
			return"login";
	}

	@GetMapping("")
	public String showHomePage(Model model, RedirectAttributes ra){
		var user_id = model.getAttribute("user_id");
		if(user_id == "" || user_id == null){
			return "redirect:/login";
		}
		ra.addFlashAttribute("user_id", user_id);

		var purchase = purchaseService.findActiveByUser(Long.parseLong(user_id.toString()));
		if(purchase != null) {
			ra.addFlashAttribute("purchase_id", purchase.id());
		}
		return "index";
	}

	@GetMapping("/{id}")
	public String showHomePage(@PathVariable Long id, Model model, RedirectAttributes ra){
		var user = userService.findById(id);

		if(user != null){
			ra.addFlashAttribute("user_id", user.id());
			var purchase = purchaseService.findActiveByUser(user.id());
			if(purchase != null) {
				ra.addFlashAttribute("purchase_id", purchase.id());
			}
			return "redirect:/";
		}

		return "redirect:/login";
	}

}
