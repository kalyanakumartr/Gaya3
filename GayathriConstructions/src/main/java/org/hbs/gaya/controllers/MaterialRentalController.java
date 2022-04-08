package org.hbs.gaya.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MaterialRentalController {
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
	
	
	@GetMapping(value = "/rentalInvoice")
    public String viewInvoicePage() {
		
		System.out.println(">>>>>>>>>>>>>>>viewDashBoardPage>>>>>>>>>>>>>>>> " );
    	return "invoice";
    }
}
