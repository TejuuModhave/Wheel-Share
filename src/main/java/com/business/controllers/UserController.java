package com.business.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.business.entities.User;
import com.business.services.UserServices;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController
{
	@Autowired
	private UserServices services;

	@PostMapping("/addingUser")
	public String  addUser(@ModelAttribute User user)
	{
		System.out.println(user);
		this.services.addUser(user);
		return "redirect:/admin/services";
	}

	@GetMapping("/updatingUser/{id}")
	public String updateUser(@ModelAttribute User user, @PathVariable("id") int id)
	{
		this.services.updateUser(user, id);
		return "redirect:/admin/services";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id" )int id)
	{
		this.services.deleteUser(id);
		return "redirect:/admin/services";
	}
	@PostMapping("/register")
	public String registerUser(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes) {
		User newUser = new User();
		newUser.setUemail(email);
		newUser.setUpassword(password);

		services.addUser(newUser);

		redirectAttributes.addFlashAttribute("successMessage", "User registered successfully!"); // ✅ Success message
		return "redirect:/home";
	}


	@GetMapping("/register.html")
	public String showRegisterPage() {
		return "register";
	}



}
