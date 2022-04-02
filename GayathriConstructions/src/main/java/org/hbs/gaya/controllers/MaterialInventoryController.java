package org.hbs.gaya.controllers;

import org.hbs.gaya.dao.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MaterialInventoryController {

	@Autowired
	private UsersDao usersDao;
	
	@GetMapping("/mat")
	public String viewHomePage() {
		return "index";
	}
	
	
}
