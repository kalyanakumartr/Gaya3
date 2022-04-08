package org.hbs.gaya.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MaterialInvoiceReportController {
	@GetMapping(value = "/reports")
	public String viewReportsPage()
	{
		return "reports";
	}

}
