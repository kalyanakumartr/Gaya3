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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class UserController {

	@Autowired
	private UsersDao usersDao;
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping(value = "/success-dashboard")
	@ResponseBody
    public String successPage() {
		
    	return "success";
    }
	

	@GetMapping(value = "/failure-dashboard")
	@ResponseBody
    public String failurePage() {
		
    	return "failure";
    }


	@GetMapping(value = "/dashboard")
    public String viewDashBoardPage() {
		
		System.out.println(">>>>>>>>>>>>>>>viewDashBoardPage>>>>>>>>>>>>>>>> " );
    	return "dashboard";
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
	
	@PostMapping("/users")
	@ResponseBody
	public String listUsers(Model model) {
		List<Users> listUsers = usersDao.findAll();
		model.addAttribute("listUsers", listUsers);
		System.out.println(">>>>listUsers>>>>> " + listUsers);
		return new Gson().toJson(listUsers);
	}
	
}
