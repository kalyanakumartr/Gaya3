package org.hbs.gaya.controllers;

import org.hbs.gaya.dao.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller

public class MaterialController {

	@Autowired
	private UsersDao usersDao;
	
}
