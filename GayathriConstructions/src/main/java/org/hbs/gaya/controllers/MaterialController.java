package org.hbs.gaya.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class MaterialController {

	@GetMapping(value = "/material")
	public String viewMaterialPage()
	{
		return "material";
	}

	
}
