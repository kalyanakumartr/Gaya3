package org.hbs.gaya.controllers;

import java.util.List;

import org.hbs.gaya.dao.UsersDao;
import org.hbs.gaya.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	@Autowired
	private UsersDao usersDao;
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new Users());
		
		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(Users user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		usersDao.save(user);
		
		return "register_success";
	}
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		List<Users> listUsers = usersDao.findAll();
		model.addAttribute("listUsers", listUsers);
		
		return "users";
	}
}
