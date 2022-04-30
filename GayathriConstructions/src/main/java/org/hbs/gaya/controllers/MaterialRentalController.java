package org.hbs.gaya.controllers;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hbs.gaya.bo.RentalBo;
import org.hbs.gaya.model.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Controller
public class MaterialRentalController
{
	private DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
	
	@Autowired
	RentalBo rentalBo;

	@Autowired
	Jackson2ObjectMapperBuilder mapperBuilder;
	
	@GetMapping(value = "/success-dashboard")
	@ResponseBody
	public String successPage()
	{

		return "success";
	}

	@GetMapping(value = "/failure-dashboard")
	@ResponseBody
	public String failurePage()
	{

		return "failure";
	}

	@PostMapping(value = "/calculateInvoice")
	@ResponseBody
	public String calculateInvoice(HttpServletRequest request)
	{

		return "failure";
	}
	
	@PostMapping(value = "/viewRentalReceipt/{rentalId}")
	public String viewRentalReceipt(ModelMap modal, @PathVariable("rentalId") String rentalId)
	{
		
		Rental rental = rentalBo.getRentalById(rentalId);
		
		modal.addAttribute("items", rental.getRentalItemSet());
		modal.addAttribute("advanceAmount$", rental.getAdvanceAmount$());
		modal.addAttribute("totalAmount$", rental.getTotalAmount$());
		modal.addAttribute("customer", rental.getCustomer());
		modal.addAttribute("rentedDate", rental.getRentedDate().format(dtFormat));

		return "fragments/receipt";
	}
	
	@GetMapping(value = "/printRentalReceipt/{rentalId}")
	public String printRentalReceipt(ModelMap modal, @PathVariable("rentalId") String rentalId)
	{
		
		Rental rental = rentalBo.getRentalById(rentalId);
		
		modal.addAttribute("items", rental.getRentalItemSet());
		modal.addAttribute("advanceAmount$", rental.getAdvanceAmount$());
		modal.addAttribute("totalAmount$", rental.getTotalAmount$());
		modal.addAttribute("customer", rental.getCustomer());
		modal.addAttribute("rentedDate", rental.getRentedDate().format(dtFormat));

		return "fragments/print_receipt";
	}

	@GetMapping(value = "/dashboard")
	public String viewDashBoardPage()
	{

		return "dashboard";
	}

	@PostMapping(value = "/viewRentalInvoice/{rentalId}")
	public String viewRentalInvoice(@PathVariable("rentalId") String rentalId)
	{
		return "fragments/invoice";
	}

	@PostMapping("/searchRental")
	@ResponseBody
	public String search(HttpServletRequest request)
	{
		String data = "";
		try
		{
			String search = request.getParameter("");
			List<Rental> dataList = rentalBo.searchRental("%");
			ObjectMapper o = mapperBuilder.build();
			o.registerModule(new JavaTimeModule());
			data = o.writeValueAsString(dataList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

}
