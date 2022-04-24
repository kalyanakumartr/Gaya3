package org.hbs.gaya.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hbs.gaya.bo.RentalBo;
import org.hbs.gaya.model.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Controller
public class MaterialRentalController
{

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

	@GetMapping(value = "/dashboard")
	public String viewDashBoardPage()
	{

		return "dashboard";
	}

	@GetMapping(value = "/rentalInvoice")
	public String viewInvoicePage()
	{

		return "invoice";
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
